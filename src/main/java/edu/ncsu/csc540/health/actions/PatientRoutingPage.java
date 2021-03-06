package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.service.PatientService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

public class PatientRoutingPage implements Action {
    private final ActionFactory actionFactory;
    private final Action homePage;
    private final PatientService patientService;
    private final Patient patient;

    @Inject
    public PatientRoutingPage(ActionFactory actionFactory,
                              @Named("home") Action homePage,
                              PatientService patientService,
                              @Assisted Patient patient) {
        this.actionFactory = actionFactory;
        this.homePage = homePage;
        this.patientService = patientService;
        this.patient = patient;
    }

    /**
     * Display the landing page for the Patient Routing Page
     * @param textIO A reference to the terminal controller
     * @return An Action object containing a reference to a page
     */
    @Override
    public Action apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();


        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Check-in", (TextIO tio) -> {
                            // Validate patient is not already checked in.
                            if (patientService.isCheckedIn(patient)) {
                                terminal.println("Patient is already checked in.");
                                return homePage;
                            } else {
                                return actionFactory.getPatientCheckinPage(patient);
                            }
                        }),
                        Pair.of("Check-out acknowledgement",
                                actionFactory.getPatientCheckoutAcknowledgementPage(patient, this)),
                        Pair.of("Go back", homePage)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nPatient Routing Menu:")
                .getValue();
    }
}
