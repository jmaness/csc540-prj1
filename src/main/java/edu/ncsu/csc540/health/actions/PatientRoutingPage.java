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
    private final Action homePage;
    private final Action checkInPage;
    private final PatientService patientService;
    private final Patient patient;

    @Inject
    public PatientRoutingPage(@Named("home") Action homePage,
                              @Named("checkIn") Action checkInPage,
                              PatientService patientService,
                              @Assisted Patient patient) {
        this.homePage = homePage;
        this.checkInPage = checkInPage;
        this.patientService = patientService;
        this.patient = patient;
    }

    @Override
    public Action apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        Action notYetImplemented = Actions.notYetImplemented.apply(this);

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Check-in", (TextIO tio) -> {
                            // Validate patient is not already checked in.
                            if (patientService.isCheckedIn(patient)) {
                                terminal.println("Patient is already checked in.");
                                return homePage;
                            } else {
                                return checkInPage;
                            }
                        }),
                        Pair.of("Check-out acknowledgement", notYetImplemented),
                        Pair.of("Go back", homePage)
                ))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }
}
