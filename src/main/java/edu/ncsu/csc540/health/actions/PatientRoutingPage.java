package edu.ncsu.csc540.health.actions;

import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;

import javax.inject.Named;
import java.util.Arrays;

public class PatientRoutingPage implements Action {
    private final Action homePage;

    public PatientRoutingPage(@Named("home") Action homePage) {
        this.homePage = homePage;
    }

    @Override
    public Action apply(TextIO textIO) {
        Action notYetImplemented = Actions.notYetImplemented.apply(this);

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Check-in", notYetImplemented),
                        Pair.of("Check-out acknowledgement", notYetImplemented),
                        Pair.of("Go back", homePage)
                ))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }
}
