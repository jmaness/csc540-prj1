package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

/**
 * The Sign-In page is the area where a user enters their information in
 * order to sign in to their user account. This page will display a series
 * of prompts, after each of which the user will input the corresponding
 * information.
 */
public class SignInPage {

    @Override
    public void accept(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("Sign In");
        terminal.println("=====================");
        /*
        TODO: List all facilities here (will do after database interfaces have been implemented)
         */
        terminal.println("A. Please enter the number of your facility: ");
    }
}
