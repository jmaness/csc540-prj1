package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.model.Symptom;
import edu.ncsu.csc540.health.model.*;
import edu.ncsu.csc540.health.service.AssessmentRuleService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.SymptomService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
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
    private final Action homePage;
    private final Staff staff;
    private final SymptomService symptomService;
    private final PatientService patientService;
    private final AssessmentRuleService assessmentRuleService;

    @Inject
    public StaffMenuPage(@Named("home") Action homePage,
                         @Assisted Staff staff,
                         SymptomService symptomService,
                         PatientService patientService,
                         AssessmentRuleService assessmentRuleService) {
        this.homePage = homePage;
        this.staff = staff;
        this.symptomService = symptomService;
        this.patientService = patientService;
        this.assessmentRuleService = assessmentRuleService;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checked-in patient list", this::processPatient),
                        Pair.of("Treated patient list", this::treatedPatientListMenu),
                        Pair.of("Add symptoms", this::addSymptoms),
                        Pair.of("Add severity scale", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add assessment rule", this::addAssessmentRule),
                        Pair.of("Go back", homePage)))
                .withValueFormatter(Pair::getKey)
                .read("Staff Menu")
                .getValue();
    }

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
                .read()
                .getValue();
    }

    private Action enterVitals(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        List<Patient> patients = patientService.findAllVitalsPatients();

        if (patients.isEmpty()) {
            terminal.println("Error: No patients currently waiting for vitals entry.");
            return this;
        }

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getFirstName)
                .read("Please select the patient whose vitals you wish to record: ");

        Integer temperature = textIO.newIntInputReader().read("A. Please enter the patient's temperature in degrees Celsius: ");
        Integer systolicBP = textIO.newIntInputReader().read("B. Please enter the patient's systolic blood pressure: ");
        Integer diastolicBP = textIO.newIntInputReader().read("C. Please enter the patient's diastolic blood pressure: ");

        terminal.println("Please confirm the following information:\n");
        terminal.println(String.format("Patient: %s %s", selectedPatient.getFirstName(), selectedPatient.getLastName()));
        terminal.println(String.format("Temperature: %d", temperature));
        terminal.println(String.format("Systolic Blood Pressure: %d", systolicBP));
        terminal.println(String.format("Diastolic Blood Pressure: %d", diastolicBP));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm & Record", (TextIO tio) -> {
                            patientService.updateCheckInEndtime(selectedPatient, new Timestamp(System.currentTimeMillis()));
                            //TODO: Implement assessment rules, place patient on priority list, display priority
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }

    private Action treatPatient(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        List<Patient> patients = patientService.findAllPriorityPatients();

        if (patients.isEmpty()) {
            terminal.println("Error: No patients currently waiting for treatment.");
            return this;
        }

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getFirstName)
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

        terminal.println("Treating patient...");
        terminal.println("...");
        terminal.println("...Patient treated! You're the best doctor ever!");

        //TODO: Replace this with a reference to the Patient Outcome Report page
        return Actions.notYetImplemented.apply(this);
    }

    private Action treatedPatientListMenu(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checkout patient", this::treatedPatientList),
                        Pair.of("Go back", this::apply)))
                .withValueFormatter(Pair::getKey)
                .read("Treated Patient Menu")
                .getValue();
    }

    private Action treatedPatientList(TextIO textIO) {
        List<Patient> patients = patientService.getTreatedPatientList();

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getDisplayString)
                .read("Patient");

        //Jeremy, patient info is above. Ready to hook up next step
        return Actions.notYetImplemented.apply(this);
    }

    private Action addSymptoms(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        String name = textIO.newStringInputReader()
                .read("A. Please enter the name of the symptom: ");

        List<BodyPart> bodyParts = symptomService.findAllBodyParts();

        BodyPart selectedBodyPart = textIO.<BodyPart>newGenericInputReader(null)
                .withNumberedPossibleValues(bodyParts)
                .withValueFormatter(BodyPart::getName)
                .withDefaultValue(null)
                .read("B. Please select the associated body part (leave blank if none applicable): ");

        List<SeverityScale> severityScales = symptomService.findAllSeverityScales();

        SeverityScale selectedScale = textIO.<SeverityScale>newGenericInputReader(null)
                .withNumberedPossibleValues(severityScales)
                .withValueFormatter(SeverityScale::getName)
                .withDefaultValue(null)
                .read("C. Please select the associated severity scale (leave blank if none applicable): ");

        terminal.println("\nPlease confirm the following information:\n");
        terminal.println(String.format("Symptom Name: %s", name));
        terminal.println(String.format("Associated Body Part: %s", selectedBodyPart.getName()));
        terminal.println(String.format("Associated Severity Scale: %s", selectedScale.getName()));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", (TextIO tio) -> {
                            symptomService.createSymptom(new Symptom(null,
                                    name,
                                    selectedScale,
                                    selectedBodyPart));
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }

    private Action addAssessmentRule(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        boolean repeat = false;
        List<AssessmentSymptom> assessmentSymptoms = new ArrayList<>();

        do {

            List<Symptom> symptoms = symptomService.findAllSymptoms();

            Symptom selectedSymptom = textIO.<Symptom>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptoms)
                    .withValueFormatter(Symptom::getName)
                    .read("Please select a symptom: ");

            SeverityScaleValue selectedValue = textIO.<SeverityScaleValue>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptomService.findSeverityScaleValues(selectedSymptom.getSeverityScale().getId()))
                    .withValueFormatter(SeverityScaleValue::getName)
                    .read("Please select a severity: ");

            String operation = textIO.<String>newStringInputReader()
                    .withNumberedPossibleValues(Arrays.asList(
                            "<",
                            "<=",
                            "=",
                            ">=",
                            ">"
                    ))
                    .read("Please select an operator to associate to the severity: ");

            assessmentSymptoms.add(new AssessmentSymptom(null, selectedSymptom, selectedValue, operation));

            terminal.println("Would you like to enter another symptom, or move on to choosing the assessment rule priority?");

            repeat = textIO.<Pair<String, Boolean>>newGenericInputReader(null)
                    .withNumberedPossibleValues(Arrays.asList(
                            Pair.of("Enter another symptom", true),
                            Pair.of("Choose priority", false)))
                    .withValueFormatter(Pair::getKey)
                    .read()
                    .getValue();
        } while (repeat);

        String priority = textIO.<String>newStringInputReader()
                .withNumberedPossibleValues(Arrays.asList(
                        "High",
                        "Normal",
                        "Quarantine"
                ))
                .read("Please select the priority to associate with this assessment rule:");

        String description = textIO.newStringInputReader()
                .withDefaultValue("No description provided.")
                .read("Please provide a brief description of this assessment rule: ");

        terminal.println("\nPlease confirm the following information:\n");
        for (AssessmentSymptom assessmentSymptom : assessmentSymptoms)
            terminal.println(String.format("Symptom: %s | Severity: %s", assessmentSymptom.getSymptom().getName(), assessmentSymptom.getSeverityScaleValue().getName()));

        terminal.println(String.format("Rule priority: %s", priority));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", (TextIO tio) -> {
                            assessmentRuleService.createAssessmentRule(new AssessmentRule(null, priority, description, assessmentSymptoms));
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }
}
