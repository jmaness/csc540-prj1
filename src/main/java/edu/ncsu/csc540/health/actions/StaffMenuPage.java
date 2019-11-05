package edu.ncsu.csc540.health.actions;

import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 * The Home page is one of the start pages that all users initially see. This
 * page shows a menu which will allow the user to sign in, sign up as a patient
 * or run some demo queries.
 *
 */
@Singleton
public class StaffMenuPage implements Action {
    private final Action homePage;

    @Inject
    public StaffMenuPage(@Named("home") Action homePage) {
        this.homePage = homePage;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checked-in patient list", Actions.notYetImplemented.apply(this)),
                        Pair.of("Treated patient list", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add symptoms", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add severity scale", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add assessment rule", Actions.notYetImplemented.apply(this)),
                        Pair.of("Go back", homePage)))
                .withValueFormatter(Pair::getKey)
                .read("Staff Menu")
                .getValue();
    }
}