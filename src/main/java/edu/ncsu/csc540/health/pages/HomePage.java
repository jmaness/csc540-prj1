package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.function.Consumer;

/**
 * The Home page is one of the start pages that all users initially see. This
 * page shows a menu which will allow the user to sign in, sign up as a patient
 * or run some demo queries.
 *
 */
public class HomePage implements Consumer<TextIO> {
    private final Consumer<TextIO> signUpPage;

    @Inject
    public HomePage(@Named("signUp") Consumer<TextIO> signUpPage) {
        this.signUpPage = signUpPage;
    }

    @Override
    public void accept(TextIO textIO) {
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
                break;
            case 2:
                signUpPage.accept(textIO);
                break;
            case 3:
                terminal.println("Demo Queries not yet implemented");
                break;
            case 4:
                textIO.dispose();
                break;
        }
    }
}
