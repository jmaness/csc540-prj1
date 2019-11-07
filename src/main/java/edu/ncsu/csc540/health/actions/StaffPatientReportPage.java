package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.DischargeStatus;
import edu.ncsu.csc540.health.model.NegativeExperience;
import edu.ncsu.csc540.health.model.NegativeExperienceCode;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.Staff;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaffPatientReportPage implements Action {
    private Staff staff;
    private PatientCheckIn patientCheckIn;
    private DischargeStatus selectedDischargeStatus;
    private String treatment;
    private List<NegativeExperience> negativeExperiences = new ArrayList<>();

    @Inject
    public StaffPatientReportPage(@Assisted Staff staff,
                                  @Assisted PatientCheckIn patientCheckIn) {
        this.staff = staff;
        this.patientCheckIn = patientCheckIn;
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

        // TODO This will be implemented in https://trello.com/c/xxWce1jX/32-app-referral-status
        return this;
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
        return Actions.notYetImplemented.apply(this);
    }
}
