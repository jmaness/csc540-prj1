package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.DischargeStatus;
import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.NegativeExperience;
import edu.ncsu.csc540.health.model.NegativeExperienceCode;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.ReferralReason;
import edu.ncsu.csc540.health.model.ReferralReasonCode;
import edu.ncsu.csc540.health.model.ReferralStatus;
import edu.ncsu.csc540.health.model.Service;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.service.FacilityService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.StaffService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StaffPatientReportPage implements Action {
    private final Staff staff;
    private final PatientCheckIn patientCheckIn;

    private final StaffMenuPage staffMenuPage;
    private final PatientService patientService;
    private final FacilityService facilityService;
    private final StaffService staffService;

    private DischargeStatus selectedDischargeStatus;
    private ReferralStatus referralStatus;
    private String treatment;
    private List<NegativeExperience> negativeExperiences = new ArrayList<>();

    @Inject
    public StaffPatientReportPage(@Assisted Staff staff,
                                  @Assisted Patient patient,
                                  @Assisted StaffMenuPage staffMenuPage,
                                  PatientService patientService,
                                  FacilityService facilityService,
                                  StaffService staffService) {
        this.staff = staff;
        this.staffMenuPage = staffMenuPage;
        this.patientService = patientService;
        this.facilityService = facilityService;
        this.staffService = staffService;

        this.patientCheckIn = patientService.findActivePatientCheckIn(patient);
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Discharge Status", this::selectDischargeStatus),
                        Pair.of("Referral Status", this::addReferralStatus),
                        Pair.of("Treatment", this::addTreatment),
                        Pair.of("Negative Experience", this::addNegativeExperience),
                        Pair.of("Go back", staffMenuPage),
                        Pair.of("Submit", this::confirm)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nStaff-Patient Report")
                .getValue();
    }

    /**
     * Returns an Action that allows the user to select a discharge status
     *
     * @param textIO TextIO
     * @return an Action that allows the user to select a discharge status
     */
    private Action selectDischargeStatus(TextIO textIO) {
        selectedDischargeStatus = textIO.newEnumInputReader(DischargeStatus.class)
                .withAllValuesNumbered()
                .withValueFormatter(DischargeStatus::getLabel)
                .read("\nDischarge Status");

        return this;
    }

    /**
     * Returns an Action that allows the user to add a referral status if the discharge status is "Referred"
     *
     * @param textIO TextIO
     * @return an Action to add a referral status
     */
    private Action addReferralStatus(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        // Referral Status should allow be set if the discharge status is "Referred"
        if (selectedDischargeStatus != DischargeStatus.REFERRED) {
            terminal.println(String.format("\nDischarge status is not \"%s\"", DischargeStatus.REFERRED.getLabel()));
            return this;
        }

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Facility", this::selectFacility),
                        Pair.of("Referrer", this::selectReferrer),
                        Pair.of("Add reason", this::addReferralStatusReason),
                        Pair.of("Go back", this)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nReferral Status")
                .getValue();
    }

    private Action selectFacility(TextIO textIO) {
        List<Facility> facilities = facilityService.findAllFacilities();
        List<Optional<Facility>> facilityOptions = facilities.stream()
                .map(Optional::of)
                .collect(Collectors.toList());
        facilityOptions.add(Optional.empty());

        Optional<Facility> selectedFacility = textIO.<Optional<Facility>>newGenericInputReader(null)
                .withNumberedPossibleValues(facilityOptions)
                .withValueFormatter(f -> f.map(Facility::getName).orElse("None"))
                .read("\nFacility:");

        if (referralStatus == null) {
            referralStatus = new ReferralStatus(patientCheckIn.getId(), null, null, null);
        }

        referralStatus = new ReferralStatus(
                referralStatus.getCheckInId(),
                selectedFacility.map(Facility::getId).orElse(null),
                referralStatus.getStaffId(),
                referralStatus.getReasons());

        return this::addReferralStatus;
    }

    private Action selectReferrer(TextIO textIO) {
        List<Staff> medicalStaff = staffService.findAllMedicalStaffByFacility(staff.getFacilityId());

        Staff referrer = textIO.<Staff>newGenericInputReader(null)
                .withNumberedPossibleValues(medicalStaff)
                .withValueFormatter(s -> String.format("%s, %s", s.getLastName(), s.getFirstName()))
                .read("\nReferrer");

        if (referralStatus == null) {
            referralStatus = new ReferralStatus(patientCheckIn.getId(), null, null, null);
        }

        referralStatus = new ReferralStatus(
                referralStatus.getCheckInId(),
                referralStatus.getFacilityId(),
                referrer.getId(),
                referralStatus.getReasons());

        return this::addReferralStatus;
    }

    private Action addReferralStatusReason(TextIO textIO) {
        ReferralReasonCode reasonCode = textIO.newEnumInputReader(ReferralReasonCode.class)
                .withValueFormatter(ReferralReasonCode::getLabel)
                .read("Reason code");

        String description = textIO.newStringInputReader()
                .withMaxLength(2000)
                .read("Description");

        List<Service> services;

        if (referralStatus.getFacilityId() != null) {
            services = facilityService.findAllServicesByFacility(referralStatus.getFacilityId());
        } else {
            services = facilityService.findAllServices();
        }

        Service service = textIO.<Service>newGenericInputReader(null)
                .withNumberedPossibleValues(services)
                .withValueFormatter(Service::getDisplayString)
                .read("Service");

        ReferralReason referralReason = new ReferralReason(
                referralStatus.getCheckInId(),
                reasonCode,
                service.getCode(),
                description);

        if (referralStatus == null) {
            referralStatus = new ReferralStatus(patientCheckIn.getId(), null, null, null);
        }

        referralStatus.getReasons().add(referralReason);

        return this::addReferralStatus;
    }

    /**
     * Returns an Action to allow the staff user to specify a textual description of the treatment
     *
     * @param textIO TextIO
     * @return an Action to specify the treatment description
     */
    private Action addTreatment(TextIO textIO) {
        treatment = textIO.newStringInputReader()
                .withMaxLength(2000)
                .read("Treatment");

        return this;
    }

    /**
     * Returns an Action that allows the user to enter a negative experience code and description
     * to add to the current patient checkin.
     *
     * @param textIO TextIO
     * @return an Action to accept a negative experience code and description for the checkin
     */
    private Action addNegativeExperience(TextIO textIO) {
        NegativeExperienceCode code = textIO.newEnumInputReader(NegativeExperienceCode.class)
                .withValueFormatter(NegativeExperienceCode::getLabel)
                .read("Code");

        String description = textIO.newStringInputReader()
                .withMaxLength(2000)
                .read("Description");

        negativeExperiences.add(new NegativeExperience(patientCheckIn.getId(), code, description));
        return this;
    }

    /**
     * Returns an Action that allows the user to see a summary of the report and options to confirm
     * or go back
     *
     * @param textIO TextIO
     * @return an Action that allows the user to confirm the patient report summary
     */
    private Action confirm(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        if (!validateFields(textIO)) {
            return this;
        }

        terminal.println(String.format("\nDischarge status: %s", selectedDischargeStatus.getLabel()));

        if (selectedDischargeStatus == DischargeStatus.REFERRED) {
            Facility facility = facilityService.findById(referralStatus.getFacilityId());
            Staff referrer = staffService.findById(referralStatus.getStaffId());

            terminal.println("Referral Status:");
            terminal.println(String.format("    Facility: %s", Optional.ofNullable(facility)
                    .map(Facility::getName)
                    .orElse("None")));
            terminal.println(String.format("    Referrer: %s", referrer.getDisplayString()));
            terminal.println("    Reasons:");

            referralStatus.getReasons().forEach(reason -> {
                Service service = facilityService.findServiceByCode(reason.getServiceCode());

                terminal.println(String.format("      - Reason code: %s", reason.getCode().getLabel()));
                terminal.println(String.format("        Description: %s", reason.getDescription()));
                terminal.println(String.format("        Service: %s", service.getDisplayString()));
            });
        }

        terminal.println(String.format("Treatment: %s", treatment));
        terminal.println("Negative Experiences:" + (negativeExperiences.isEmpty() ? " None" : ""));

        if (!negativeExperiences.isEmpty()) {
            negativeExperiences.forEach(negativeExperience ->
                terminal.println(String.format("  - Code: %s\n    Description: %s",
                        negativeExperience.getCode().getLabel(),
                        negativeExperience.getDescription())));
        }

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", this::submit),
                        Pair.of("Go Back", this)
                ))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }

    /**
     * Returns true if all required fields have been specified in the form. Prints
     * validation error message to the console.
     *
     * @param textIO TextIO
     * @return true if all required fields have been specified, false otherwise
     */
    private boolean validateFields(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        if (selectedDischargeStatus == null) {
            terminal.println("\nDischarge status is required.\n");
            return false;
        }

        if (selectedDischargeStatus == DischargeStatus.REFERRED && referralStatus == null) {
            terminal.println("\nReferral status is required since the discharge status is \"Referred\"\n");
            return false;
        }

        if (StringUtils.isBlank(treatment)) {
            terminal.println("\nTreatment description is required.\n");
            return false;
        }

        return true;
    }

    /**
     * Returns an Action that will persist the report summary and discharge the patient.
     *
     * @param textIO TextIO
     * @return an Action that will persist the report summary and discharge the patient.
     */
    private Action submit(TextIO textIO) {
        OutcomeReport outcomeReport = new OutcomeReport(
                patientCheckIn.getId(),
                selectedDischargeStatus,
                referralStatus,
                treatment,
                LocalDateTime.now(),
                negativeExperiences,
                null,
                null);

        patientService.submitOutcomeReport(outcomeReport);

        return staffMenuPage;
    }
}
