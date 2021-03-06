package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.Operation;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.PatientVitals;
import edu.ncsu.csc540.health.model.Priority;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.model.Symptom;
import edu.ncsu.csc540.health.service.AssessmentRuleService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.SeverityScaleService;
import edu.ncsu.csc540.health.service.SymptomService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.jdbi.v3.core.JdbiException;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Home page is one of the start pages that all users initially see. This
 * page shows a menu which will allow the user to sign in, sign up as a patient
 * or run some demo queries.
 */
public class StaffMenuPage implements Action {
    private final ActionFactory actionFactory;
    private final Action homePage;
    private final Staff staff;
    private final SymptomService symptomService;
    private final PatientService patientService;
    private final AssessmentRuleService assessmentRuleService;
    private final SeverityScaleService severityScaleService;

    private SeverityScale scale;
    private List<SeverityScaleValue> scaleValues = new ArrayList<>();

    @Inject
    public StaffMenuPage(ActionFactory actionFactory,
                         @Named("home") Action homePage,
                         @Assisted Staff staff,
                         SymptomService symptomService,
                         PatientService patientService,
                         AssessmentRuleService assessmentRuleService,
                         SeverityScaleService severityScaleService) {
        this.actionFactory = actionFactory;
        this.homePage = homePage;
        this.staff = staff;
        this.symptomService = symptomService;
        this.patientService = patientService;
        this.assessmentRuleService = assessmentRuleService;
        this.severityScaleService = severityScaleService;
    }

    /**
     * Displays the landing menu of the Staff Menu Page
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checked-in patient list", this::processPatient),
                        Pair.of("Treated patient list", this::treatedPatientListMenu),
                        Pair.of("Add symptoms", this::addSymptoms),
                        Pair.of("Add severity scale", this::addScale),
                        Pair.of("Add assessment rule", this::addAssessmentRule),
                        Pair.of("Go back", homePage)))
                .withValueFormatter(Pair::getKey)
                .read("\nStaff Menu")
                .getValue();
    }

    /**
     * Begins the creation of a new severity scale
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action addScale(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        scaleValues.clear();

        terminal.println("\nAdd Severity Scale");
        terminal.println("=====================");

        String scaleName = textIO.newStringInputReader()
                .read("Enter the name of the new scale: ");

        this.scale = new SeverityScale(null, scaleName);

        return this::scaleMenu;
    }

    /**
     * Displays a menu for modifying/confirming a severity scale that is being created
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action scaleMenu(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Add level for this scale", this::addScaleValue),
                        Pair.of("Confirm scale: no more levels", this::writeScale),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read("\nScale Menu")
                .getValue();
    }

    /**
     * Adds a severity scale value to a given severity scale
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action addScaleValue(TextIO textIO) {
        String name = textIO.newStringInputReader()
                .read("\nEnter the severity scale value: ");

        this.scaleValues.add(new SeverityScaleValue(null, null, name, this.scaleValues.size() + 1));

        return this::scaleMenu;
    }

    /**
     * Adds the newly created severity scale to the database
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action writeScale(TextIO textIO) {
        int id = severityScaleService.addSeverityScale(this.scale);

        for (SeverityScaleValue s : this.scaleValues)
            severityScaleService.addSeverityScaleValue(
                    new SeverityScaleValue(
                            null,
                            id,
                            s.getName(),
                            s.getOrdinal()
                    )
            );

        return this;
    }

    /**
     * Provides a menu for the user to process a patient by either entering their vitals or attempting to treat them
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action processPatient(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        if (!staff.getDesignation().equalsIgnoreCase("medical")) {
            terminal.println("Error: Only medical staff can process patients.");
            terminal.println("If you wish to process a patient, please return to the home page and sign in with a medical staff's credentials.");
            return this;
        }

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Enter Vitals", this::enterVitals),
                        Pair.of("Treat Patient", this::treatPatient),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read("\nPatient Processing Menu:")
                .getValue();
    }

    /**
     * User interface for entering a given patient's vitals. The patient's vitals are written to the database and their
     * check-in is updated with an end-time, then their symptoms are assessed and the patient is moved onto a priority list.
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action enterVitals(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        List<Patient> patients = patientService.findAllVitalsPatients(staff.getFacilityId());

        if (patients.isEmpty()) {
            terminal.println("Error: No patients currently waiting for vitals entry.");
            return this;
        }

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getDisplayString)
                .read("\nPlease select the patient whose vitals you wish to record: ");

        Integer temperature = textIO.newIntInputReader().read("\nPlease enter the patient's temperature in degrees Celsius: ");
        Integer systolicBP = textIO.newIntInputReader().read("Please enter the patient's systolic blood pressure: ");
        Integer diastolicBP = textIO.newIntInputReader().read("Please enter the patient's diastolic blood pressure: ");

        terminal.println("Please confirm the following information:\n");
        terminal.println(String.format("Patient: %s", selectedPatient.getDisplayString()));
        terminal.println(String.format("Temperature: %d", temperature));
        terminal.println(String.format("Systolic Blood Pressure: %d", systolicBP));
        terminal.print(String.format("Diastolic Blood Pressure: %d", diastolicBP));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm & Record", (TextIO tio) -> {
                            PatientCheckIn checkIn = patientService.findActivePatientCheckIn(selectedPatient);
                            Priority priority = patientService.confirmPatientVitals(new PatientVitals(checkIn.getId(),
                                    temperature,
                                    systolicBP,
                                    diastolicBP));

                            terminal.println("...Success!");
                            terminal.println("The patient's check-in has been completed, and has been giving the following priority: " + priority.toString());
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }

    /**
     * Interface for treating patients. If a staff member belongs to a department that can treat the patient, the patient
     * is moved off of the priority list and prepares them for the check-out process.
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action treatPatient(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        List<Patient> patients = patientService.findAllPriorityPatients(staff.getFacilityId());

        if (patients.isEmpty()) {
            terminal.println("Error: No patients currently waiting for treatment.");
            return this;
        }

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getDisplayString)
                .read("Please select a patient to treat: ");

        boolean treatable = false;
        List<Symptom> symptoms = patientService.findAllPatientSymptoms(selectedPatient);
        List<BodyPart> bodyParts = staff.getPrimaryDepartment().getBodyParts();

        for (Symptom symptom : symptoms) {
            for (BodyPart bodyPart : bodyParts) {
                if (bodyPart.getCode().equalsIgnoreCase(symptom.getBodyPart().getCode())) {
                    treatable = true;
                    break;
                }
            }

            if (treatable)
                break;
        }

        if (!treatable) {
            terminal.println("\nError: Inadequate privileges.");
            terminal.println("This patient's symptoms are not covered by your department's specialties.");
            return this;
        }

        patientService.treatPatient(staff, selectedPatient);

        terminal.println("Treating patient...");
        terminal.println("...");
        terminal.println("...Patient treated! You're the best doctor ever!");

        return this;
    }

    /**
     * Displays a menu for dealing with treated patients
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action treatedPatientListMenu(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checkout patient", this::treatedPatientList),
                        Pair.of("Go back", this)))
                .withValueFormatter(Pair::getKey)
                .read("\nTreated Patient Menu")
                .getValue();
    }

    /**
     * Begins the check-out process for a selected patient
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action treatedPatientList(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        List<Patient> patients = patientService.getTreatedPatientList(staff.getFacilityId());

        if (patients.isEmpty()) {
            terminal.println("\nThere are currently no treated patients awaiting checkout.");
            return this;
        }

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getDisplayString)
                .read("\nPatient");

        return actionFactory.getStaffPatientReportPage(staff, selectedPatient, this);
    }

    /**
     * Interface for adding a new symptom into the system
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action addSymptoms(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.println("\nAdd Symptom");
        terminal.println("=====================");

        String name = textIO.newStringInputReader()
                .read("Please enter the name of the symptom: ");

        List<BodyPart> bodyParts = symptomService.findAllBodyParts();

        BodyPart selectedBodyPart = textIO.<BodyPart>newGenericInputReader(null)
                .withNumberedPossibleValues(bodyParts)
                .withValueFormatter(BodyPart::getName)
                .read("Please select the associated body part: ");

        List<SeverityScale> severityScales = symptomService.findAllSeverityScales();

        SeverityScale selectedScale = textIO.<SeverityScale>newGenericInputReader(null)
                .withNumberedPossibleValues(severityScales)
                .withValueFormatter(SeverityScale::getName)
                .withDefaultValue(null)
                .read("Please select the associated severity scale (leave blank if none applicable): ");

        terminal.println("\nPlease confirm the following information:\n");
        terminal.println(String.format("Symptom Name: %s", name));
        terminal.println(String.format("Associated Body Part: %s", selectedBodyPart.getName()));
        terminal.print(String.format("Associated Severity Scale: %s", selectedScale.getName()));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", (TextIO tio) -> {
                            try {
                                symptomService.createSymptom(new Symptom(null,
                                        name,
                                        selectedScale,
                                        selectedBodyPart));
                            } catch (Exception e) {
                                if (e instanceof JdbiException
                                        && e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                                    terminal.println("Error: A symptom with the selected name, scale, and body part already exists.");
                                } else {
                                    throw e;
                                }
                            }
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }

    /**
     * Interface for adding a new assessment rule (and associated assessment symptoms) into the system
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    private Action addAssessmentRule(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        boolean repeat = false;
        List<AssessmentSymptom> assessmentSymptoms = new ArrayList<>();

        terminal.println("\nAdd Assessment Rule");
        terminal.println("=====================");

        do {

            List<Symptom> symptoms = symptomService.findAllSymptoms();

            Symptom selectedSymptom = textIO.<Symptom>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptoms)
                    .withValueFormatter(Symptom::getName)
                    .read("Please select a symptom: ");

            BodyPart selectedBodyPart = null;

            if (selectedSymptom.getBodyPart().getCode().equalsIgnoreCase("NON000")) {
                List<BodyPart> bodyParts = symptomService.findAllBodyParts();
                selectedBodyPart = textIO.<BodyPart>newGenericInputReader(null)
                        .withNumberedPossibleValues(bodyParts)
                        .withValueFormatter(BodyPart::getName)
                        .read("Please select a body part to associate to the symptom: ");
            }
            else
                selectedBodyPart = selectedSymptom.getBodyPart();

            SeverityScaleValue selectedValue = textIO.<SeverityScaleValue>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptomService.findSeverityScaleValues(selectedSymptom.getSeverityScale().getId()))
                    .withValueFormatter(SeverityScaleValue::getName)
                    .read("Please select a severity to associate to the symptom: ");

            Operation operation = textIO.newEnumInputReader(Operation.class)
                    .withValueFormatter(Operation::toString)
                    .read("Please select an operator to associate to the severity: ");

            assessmentSymptoms.add(new AssessmentSymptom(null, selectedSymptom, selectedBodyPart.getCode(), selectedValue, operation));

            terminal.print("Would you like to enter another symptom, or move on to choosing the assessment rule priority?");

            repeat = textIO.<Pair<String, Boolean>>newGenericInputReader(null)
                    .withNumberedPossibleValues(Arrays.asList(
                            Pair.of("Enter another symptom", true),
                            Pair.of("Choose priority", false)))
                    .withValueFormatter(Pair::getKey)
                    .read()
                    .getValue();
        } while (repeat);



        Priority priority = textIO.newEnumInputReader(Priority.class)
                .withValueFormatter(Priority::toString)
                .read("Please select an priority to associate to the assessment rule: ");

        String description = textIO.newStringInputReader()
                .withDefaultValue("No description provided.")
                .read("Please provide a brief description of this assessment rule: ");

        terminal.println("\nPlease confirm the following information:\n");
        for (AssessmentSymptom assessmentSymptom : assessmentSymptoms)
            terminal.println(String.format("Symptom: %s | Severity: %s", assessmentSymptom.getSymptom().getName(), assessmentSymptom.getSeverityScaleValue().getName()));

        terminal.print(String.format("Rule priority: %s", priority));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", (TextIO tio) -> {
                            assessmentRuleService.createAssessmentRule(new AssessmentRule(null, priority, description, assessmentSymptoms));
                            terminal.println("\nRule successfully added!");
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }
}
