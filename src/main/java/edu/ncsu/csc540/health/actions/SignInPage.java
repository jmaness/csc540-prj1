package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.service.FacilityService;
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

import java.util.List;

/**
 * The Sign-In page is the area where a user enters their information in
 * order to sign in to their user account. This page will display a series
 * of prompts, after each of which the user will input the corresponding
 * information.
 */

@Singleton
public class SignInPage implements Action {
    private final Action previousPage;
    private final PatientService patientService;
    private final FacilityService facilityService;

    private static final Logger logger = LoggerFactory.getLogger(SignInPage.class);

    @Inject
    public SignInPage(@Named("home") Action previousPage, PatientService patientService, FacilityService facilityService) {
        this.previousPage = previousPage;
        this.patientService = patientService;
        this.facilityService = facilityService;
    }

    @Override
    public Action apply(TextIO textIO) {
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
                List<Facility> facilities = facilityService.findAllFacilities();

                if (facilities.isEmpty()) {
                    terminal.println("No facilities found.");
                    terminal.println();
                    return previousPage;
                }

                Facility selectedFacility = textIO.<Facility>newGenericInputReader(null)
                    .withNumberedPossibleValues(facilities)
                    .withValueFormatter(Facility::getName)
                    .read("A. Please select your Facility: ");

                String lastName = textIO.newStringInputReader().read("\nB. Please enter your last name: ");

                String dobString = textIO.newStringInputReader().withPattern("\\d{1,2}/\\d{1,2}/\\d{4}").read("\nC. Please enter your date of birth (mm/dd/yyyy): ");
                LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

                String city = textIO.newStringInputReader().read("\nD. Please enter the city listed on your home address: ");

                String isPatient = textIO.newStringInputReader().withPossibleValues("y", "n", "yes", "no").read("\nE. Are you a patient? (y/n)");

                switch (isPatient.toLowerCase()) {
                    case "y":
                    case "yes":
                        Patient patient = patientService.signIn(selectedFacility.getId(), lastName, dob, city);

                        if (patient == null) {
                            terminal.println("\nError: Patient not found. Please try again");
                            return this;
                        }
                        else {
                            //The following should be replaced with a link to the Patient Check-In Page
                            terminal.println("\nSuccess!");
                            return previousPage;
                        }
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
