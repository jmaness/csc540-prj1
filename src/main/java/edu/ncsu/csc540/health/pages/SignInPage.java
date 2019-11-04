package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
<<<<<<< HEAD

import java.util.function.Consumer;

public class SignInPage implements Page {

    @Override
    public Page apply(TextIO textIO) {
        return null;
=======
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
>>>>>>> Added SignInPage.java and began filling out its accept() method. Note: Functions involving database queries have been omitted until the database interfaces have been implemented.
    }
}
