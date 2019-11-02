package edu.ncsu.csc540.health.pages;

import edu.ncsu.csc540.health.service.PatientService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.Date;
import java.util.function.Consumer;

/**
 * The Sign-In page is the area where a user enters their information in
 * order to sign in to their user account. This page will display a series
 * of prompts, after each of which the user will input the corresponding
 * information.
 */

@Singleton
public class SignInPage implements Page {
    private final Page previousPage;
    private final PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(SignInPage.class);

    @Inject
    public SignInPage(@Named("home") Page previousPage, PatientService patientService) {
        this.previousPage = previousPage;
        this.patientService = patientService;
    }

    @Override
    public Page apply(TextIO textIO) {
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
                String dateString = textIO.newStringInputReader().read("> ");
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("mm/dd/yyyy"));

                terminal.println("\nD. Please enter the city listed on your home address:\n");
                String city = textIO.newStringInputReader().read("> ");

                terminal.println("\nE. Are you a patient? (y/n)\n");
                String patient = textIO.newStringInputReader().withPossibleValues("y", "n", "yes", "no").read("> ");

                switch (patient.toLowerCase()) {
                    case "y":
                    case "yes":

                        break;
                    case "n":
                    case "no":
                        //TODO: Implement staff verification
                        break;
                    default:
                        //You done goofed.
                        break;
                }

                break;
            case 2:
                return previousPage;
        }

        return previousPage; //Temporary
    }
}