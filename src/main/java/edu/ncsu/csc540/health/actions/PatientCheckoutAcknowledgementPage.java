package edu.ncsu.csc540.health.actions;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.DischargeStatus;
import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.Service;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.service.FacilityService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.StaffService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;
import java.util.Optional;

public class PatientCheckoutAcknowledgementPage implements Action {
    private final PatientService patientService;
    private final FacilityService facilityService;
    private final StaffService staffService;
    private final Action patientRoutingPage;
    private final PatientCheckIn patientCheckIn;

    @Inject
    public PatientCheckoutAcknowledgementPage(PatientService patientService,
                                              FacilityService facilityService,
                                              StaffService staffService,
                                              @Assisted Patient patient,
                                              @Assisted Action patientRoutingPage) {
        this.patientService = patientService;
        this.facilityService = facilityService;
        this.staffService = staffService;
        this.patientRoutingPage = patientRoutingPage;

        this.patientCheckIn = patientService.findActivePatientCheckIn(patient);
    }

    @Override
    public Action apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        if (patientCheckIn == null) {
            terminal.println("\nNo active check-in found for patient");
            return patientRoutingPage;
        }

        OutcomeReport outcomeReport = patientService.findOutcomeReport(patientCheckIn.getId());
        printOutcomeReportSummary(terminal, outcomeReport);

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Yes", this::acknowledge),
                        Pair.of("No", this::reject),
                        Pair.of("Go Back", patientRoutingPage)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nDo you acknowledge this summary is correct?")
                .getValue();
    }

    private void printOutcomeReportSummary(TextTerminal<?> terminal, OutcomeReport outcomeReport) {

        terminal.println("\nOutcome Report\n-----------------------------------");
        terminal.println(String.format("Discharge status: %s", outcomeReport.getDischargeStatus().getLabel()));

        if (outcomeReport.getDischargeStatus() == DischargeStatus.REFERRED) {
            Optional<Facility> facility = Optional.ofNullable(outcomeReport.getReferralStatus().getFacilityId())
                    .map(facilityService::findById);
            Staff referrer = staffService.findById(outcomeReport.getReferralStatus().getStaffId());

            terminal.println("Referral Status:");
            terminal.println(String.format("    Facility: %s", facility.map(Facility::getName).orElse("None")));
            terminal.println(String.format("    Referrer: %s", referrer.getDisplayString()));
            terminal.println("    Reasons:");

            outcomeReport.getReferralStatus().getReasons().forEach(reason -> {
                Service service = facilityService.findServiceByCode(reason.getServiceCode());

                terminal.println(String.format("      - Reason code: %s", reason.getCode().getLabel()));
                terminal.println(String.format("        Description: %s", reason.getDescription()));
                terminal.println(String.format("        Service: %s", service.getDisplayString()));
            });
        }

        terminal.println(String.format("Treatment: %s", outcomeReport.getTreatment()));
        terminal.println("Negative Experiences:" + (CollectionUtils.isEmpty(outcomeReport.getNegativeExperiences()) ? " None" : ""));

        if (CollectionUtils.isNotEmpty(outcomeReport.getNegativeExperiences())) {
            outcomeReport.getNegativeExperiences().forEach(negativeExperience ->
                    terminal.println(String.format("  - Code: %s\n    Description: %s",
                            negativeExperience.getCode().getLabel(),
                            negativeExperience.getDescription())));
        }
    }

    private Action acknowledge(TextIO textIO) {
        patientService.acknowledgeOutcomeReport(patientCheckIn.getId());
        textIO.getTextTerminal().println("\nYou have successfully acknowledged the Patient Outcome Report.");
        return patientRoutingPage;
    }

    private Action reject(TextIO textIO) {
        String reason = textIO.newStringInputReader()
                .withMaxLength(1000)
                .read("\nReason");
        patientService.rejectOutcomeReport(patientCheckIn.getId(), reason);

        textIO.getTextTerminal().println("\nYou have successfully acknowledged concerns regarding the Patient Outcome Report.");
        return patientRoutingPage;
    }
}
