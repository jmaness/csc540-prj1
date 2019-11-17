package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.service.FacilityService;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.StaffService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;

/**
 * The Sign-In page is the area where a user enters their information in
 * order to sign in to their user account. This page will display a series
 * of prompts, after each of which the user will input the corresponding
 * information.
 */

@Singleton
public class SignInPage implements Action {
    private final ActionFactory actionFactory;
    private final Action previousPage;
    private final PatientService patientService;
    private final StaffService staffService;
    private final FacilityService facilityService;

    private static final Logger logger = LoggerFactory.getLogger(SignInPage.class);

    @Inject
    public SignInPage(ActionFactory actionFactory,
                      @Named("home") Action previousPage,
                      PatientService patientService,
                      StaffService staffService,
                      FacilityService facilityService) {
        this.actionFactory = actionFactory;
        this.previousPage = previousPage;
        this.patientService = patientService;
        this.staffService = staffService;
        this.facilityService = facilityService;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Sign In", this::signIn),
                        Pair.of("Go Back", previousPage)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nSign In")
                .getValue();
    }

    private Action signIn(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        List<Facility> facilities = facilityService.findAllFacilities();

        if (facilities.isEmpty()) {
            terminal.println("No facilities found.");
            terminal.println();
            return previousPage;
        }

        Facility selectedFacility = textIO.<Facility>newGenericInputReader(null)
                .withNumberedPossibleValues(facilities)
                .withValueFormatter(Facility::getName)
                .read("\nPlease select your facility: ");

        String lastName = textIO.newStringInputReader()
                .read("Please enter your last name: ");

        String dobString = textIO.newStringInputReader()
                .withPattern("\\d{1,2}/\\d{1,2}/\\d{4}")
                .read("Please enter your date of birth (mm/dd/yyyy): ");
        LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("M/d/yyyy"));

        String city = textIO.newStringInputReader()
                .read("Please enter the city listed on your home address: ");

        boolean isPatient = textIO.newBooleanInputReader()
                .read("Are you a patient?");

        if (isPatient) {
            Patient patient = patientService.signIn(selectedFacility.getId(), lastName, dob, city);

            if (patient == null) {
                terminal.println("\nError: Patient not found. Please try again.\n");
                return this;
            } else {
                return actionFactory.getPatientRoutingPage(patient);
            }
        } else {
            Staff staff = staffService.signIn(selectedFacility.getId(), lastName, city);

            if (staff == null) {
                terminal.println("\nError: Staff not found. Please try again.\n");
                return this;
            }
            else {
                return actionFactory.getStaffMenuPage(staff);
            }
        }
    }
}
