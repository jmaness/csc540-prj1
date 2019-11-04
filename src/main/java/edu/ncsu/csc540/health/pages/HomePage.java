package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * The Home page is one of the start pages that all users initially see. This
 * page shows a menu which will allow the user to sign in, sign up as a patient
 * or run some demo queries.
 *
 */
@Singleton
public class HomePage implements Page {
    private final Page signUpPage;
    private final Page demoQueryPage;

    @Inject
    public HomePage(@Named("signUp") Page signUpPage,
                    @Named("demo") Page demoQueryPage) {
        this.signUpPage = signUpPage;
        this.demoQueryPage = demoQueryPage;
    }

    @Override
    public Page apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("Home Page");
        terminal.println("=====================");
        terminal.println("1. Sign In");
        terminal.println("2. Sign Up (Patient)");
        terminal.println("3. Demo Queries");
        terminal.println("4. Exit");
        terminal.println();

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(4)
                .read("> ");

        switch (option) {
            case 1:
                terminal.println("Sign In not yet implemented");
                return this;
            case 2:
                return signUpPage;
            case 3:
                return demoQueryPage;
            case 4:
                textIO.dispose();
                break;
        }

        return null;
    }
}
