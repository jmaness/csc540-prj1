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
public class HomePage implements Action {
    private final Action signUpPage;
    private final Action demoQueryPage;
    private final Action signInPage;

    @Inject
    public HomePage(@Named("signUp") Action signUpPage,
                    @Named("demo") Action demoQueryPage,
                    @Named("signIn") Action signInPage) {
        this.signUpPage = signUpPage;
        this.demoQueryPage = demoQueryPage;
        this.signInPage = signInPage;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Sign In", signInPage),
                        Pair.of("Sign Up (Patient)", signUpPage),
                        Pair.of("Demo Queries", demoQueryPage),
                        Pair.of("Exit", Actions.exit)))
                .withValueFormatter(Pair::getKey)
                .read("\nHome Page")
                .getValue();
    }
}
