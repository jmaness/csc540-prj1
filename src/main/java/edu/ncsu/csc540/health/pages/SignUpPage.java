package edu.ncsu.csc540.health.pages;

import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.service.PatientService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class SignUpPage implements Consumer<TextIO> {
    private final Consumer<TextIO> signInPage;
    private final Consumer<TextIO> previousPage;
    private final PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(SignUpPage.class);

    @Inject
    public SignUpPage(@Named("signIn") Consumer<TextIO> signInPage,
                      @Named("home") Consumer<TextIO> previousPage,
                      PatientService patientService) {
        this.signInPage = signInPage;
        this.previousPage = previousPage;
        this.patientService = patientService;
    }

    @Override
    public void accept(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("Sign Up");
        terminal.println("=====================");
        terminal.println();

        String firstName = textIO.newStringInputReader()
                .withMaxLength(255)
                .read("First name: ");

        String lastName = textIO.newStringInputReader()
                .withMaxLength(255)
                .read("Last name: ");

        String dobString = textIO.newStringInputReader()
                .withPattern("\\d{1,2}/\\d{1,2}/\\d{4}")
                .read("Date of birth: ");

        LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        int streetNum = textIO.newIntInputReader()
                .withMinVal(0)
                .read("Street number: ");

        String street = textIO.newStringInputReader()
                .withMaxLength(255)
                .read("Street: ");

        String city = textIO.newStringInputReader()
                .withMaxLength(255)
                .read("City:");

        String state = textIO.newStringInputReader()
                .withMaxLength(255)
                .read("State:");

        String country = textIO.newStringInputReader()
                .withDefaultValue("USA")
                .read("Country:");

        String phone = textIO.newStringInputReader()
                .read("Phone:");


        terminal.println();
        terminal.println("Please confirm the following information:");
        terminal.println();
        terminal.println(String.format("First name: %s", firstName));
        terminal.println(String.format("Last name: %s", firstName));
        terminal.println(String.format("Date of birth: %s", firstName));
        terminal.println(String.format("Address: %d %s \n         %s, %s, %s", streetNum, street, city, state, country));
        terminal.println(String.format("Phone: %s", phone));

        terminal.println();
        terminal.println("1. Sign Up");
        terminal.println("2. Go Back");
        terminal.println();

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(2)
                .read("> ");

        switch (option) {
            case 1:
                patientService.signUp(new Patient(null,
                        firstName,
                        lastName,
                        dob,
                        new Address(null, streetNum, street, city, state, country),
                        phone));
                signInPage.accept(textIO);
                break;
            case 2:
                previousPage.accept(textIO);
                break;
        }
    }
}
