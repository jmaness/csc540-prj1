package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Singleton;
import java.sql.Date;
import java.util.function.Consumer;

/**
 * The Sign-In page is the area where a user enters their information in
 * order to sign in to their user account. This page will display a series
 * of prompts, after each of which the user will input the corresponding
 * information.
 */
@Singleton
public class SignInPage implements Consumer<TextIO> {

    @Override
    public void accept(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("Sign In");
        terminal.println("=====================");
        terminal.println("1. Sign In");
        terminal.println("2. Go Back\n");

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(2)
                .read("> ");

        switch (option) {
            case 1:

                /*
                TODO: List all facilities here (will do after database interfaces have been implemented)
                 */

                terminal.println("A. Please enter your Facility ID:\n");
                int facilityID = textIO.newIntInputReader().withMinVal(1).read("> "); //TODO: Add withMaxVal() after database interface implementation

                terminal.println("\nB. Please enter your last name:\n");
                String lastName = textIO.newStringInputReader().read("> ");

                terminal.println("\nC. Please enter your date of birth (mm/dd/yyyy):\n");
                String[] dateArray = textIO.newStringInputReader().read("> ").split("/");
                Date date = new Date(Integer.parseInt(dateArray[0]),
                        Integer.parseInt(dateArray[1]),
                        Integer.parseInt(dateArray[2]));

                terminal.println("\nD. Please enter the city listed on your home address:\n");
                String city = textIO.newStringInputReader().read("> ");

                terminal.println("\nE. Are you a patient? (y/n)\n");
                String patient = textIO.newStringInputReader().withPossibleValues("y", "n", "yes", "no").read("> ");

                /*
                TODO: Query database for tuple matching given information to validate user
                 */

                break;
            case 2:
                //TODO: Add a link back to the home page
                terminal.println("Coward.");
                break;
        }
    }
}
