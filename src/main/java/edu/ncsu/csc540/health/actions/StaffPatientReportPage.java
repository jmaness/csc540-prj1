package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.DischargeStatus;
import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.NegativeExperience;
import edu.ncsu.csc540.health.model.NegativeExperienceCode;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.ReferralReason;
import edu.ncsu.csc540.health.model.ReferralReasonCode;
import edu.ncsu.csc540.health.model.ReferralStatus;
import edu.ncsu.csc540.health.model.Service;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.service.FacilityService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.StaffService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StaffPatientReportPage implements Action {
    private Staff staff;
    private PatientCheckIn patientCheckIn;
    private StaffMenuPage staffMenuPage;
    private PatientService patientService;
    private FacilityService facilityService;
    private StaffService staffService;

    private DischargeStatus selectedDischargeStatus;
    private ReferralStatus referralStatus;
    private String treatment;
    private List<NegativeExperience> negativeExperiences = new ArrayList<>();

    @Inject
    public StaffPatientReportPage(@Assisted Staff staff,
                                  @Assisted PatientCheckIn patientCheckIn,
                                  @Assisted StaffMenuPage staffMenuPage,
                                  PatientService patientService,
                                  FacilityService facilityService,
                                  StaffService staffService) {
        this.staff = staff;
        this.patientCheckIn = patientCheckIn;
        this.staffMenuPage = staffMenuPage;
        this.patientService = patientService;
        this.facilityService = facilityService;
        this.staffService = staffService;
    }

    @Override
    public Action apply(TextIO textIO) {
        Action notYetImplemented = Actions.notYetImplemented.apply(this);
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Discharge Status", this::selectDischargeStatus),
                        Pair.of("Referral Status", this::addReferralStatus),
                        Pair.of("Treatment", this::addTreatment),
                        Pair.of("Negative Experience", this::addNegativeExperience),
                        Pair.of("Go back", notYetImplemented),
                        Pair.of("Submit", this::confirm)
                ))
                .withValueFormatter(Pair::getKey)
                .read("Staff - Patient Report")
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
                .read("Discharge Status");

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
            terminal.println(String.format("Discharge status is not \"%s\"", DischargeStatus.REFERRED.getLabel()));
            return this;
        }

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Facility id", this::selectFacility),
                        Pair.of("Referrer id", this::selectReferrer),
                        Pair.of("Add reason", this::addReferralStatusReason),
                        Pair.of("Go back", this)
                ))
                .read()
                .getValue();
    }

    private Action selectFacility(TextIO textIO) {
        List<Facility> facilities = facilityService.findAllFacilities();
        Facility selectedFacility = textIO.<Facility>newGenericInputReader(null)
                .withNumberedPossibleValues(facilities)
                .withValueFormatter(Facility::getName)
                .read("Facility:");

        referralStatus = new ReferralStatus(
                referralStatus.getCheckInId(),
                selectedFacility.getId(),
                referralStatus.getStaffId(),
                referralStatus.getTreatment(),
                referralStatus.getReasons());

        return this::addReferralStatus;
    }

    private Action selectReferrer(TextIO textIO) {

        if (referralStatus.getFacilityId() == null) {
            textIO.getTextTerminal().println("Please select a facility.");
            return this::addReferralStatus;
        }

        List<Staff> staff = staffService.findAllMedicalStaffByFacility(referralStatus.getFacilityId());

        Staff referrer = textIO.<Staff>newGenericInputReader(null)
                .withNumberedPossibleValues(staff)
                .withValueFormatter(s -> String.format("%s, %s", s.getLastName(), s.getFirstName()))
                .read("Referrer:");

        referralStatus = new ReferralStatus(
                referralStatus.getCheckInId(),
                referralStatus.getFacilityId(),
                referrer.getId(),
                referralStatus.getTreatment(),
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

        List<Service> services = facilityService.findAllServicesByFacility(referralStatus.getFacilityId());
        List<String> serviceNames = services.stream()
                .map(s -> String.format("%s: %s", s.getCode(), s.getName()))
                .collect(Collectors.toList());

        serviceNames.add("Other");

        String serviceName = textIO.newStringInputReader()
                .withNumberedPossibleValues(serviceNames)
                .read("Service");

        ReferralReason referralReason = new ReferralReason(
                referralStatus.getCheckInId(),
                reasonCode,
                serviceName,
                description);

        referralStatus.getReasons().add(referralReason);

        referralStatus = new ReferralStatus(
                referralStatus.getCheckInId(),
                referralStatus.getFacilityId(),
                referralStatus.getStaffId(),
                referralStatus.getTreatment(),
                referralStatus.getReasons());

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
        terminal.println(String.format("Discharge status: %s", selectedDischargeStatus.getLabel()));

        if (selectedDischargeStatus == DischargeStatus.REFERRED) {
            terminal.println(String.format("Referral Status: %s", "TODO"));
        }

        terminal.println(String.format("Treatment: %s", treatment));
        terminal.println("Negative Experiences:" + (negativeExperiences.isEmpty() ? " NONE": ""));

        if (!negativeExperiences.isEmpty()) {
            negativeExperiences.forEach( negativeExperience -> {
                terminal.println(String.format("    Code: %s", negativeExperience.getCode().getLabel()));
                terminal.println(String.format("    Description: %s", negativeExperience.getDescription()));
            });
        }

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", this::submit),
                        Pair.of("Go Back", this)
                ))
                .read()
                .getValue();
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
