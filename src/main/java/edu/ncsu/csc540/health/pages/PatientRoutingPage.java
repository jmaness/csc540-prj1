package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Named;

public class PatientRoutingPage implements Page {
    private final Page homePage;

    public PatientRoutingPage(@Named("home") Page homePage) {
        this.homePage = homePage;
    }

    @Override
    public Page apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.println("1. Check-in");
        terminal.println("2. Check-out acknowledgement");
        terminal.println("3. Go back");

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(3)
                .read("> ");

        switch (option) {
            case 1:
                terminal.println("Check-in not yet implemented");
                return this;
            case 2:
                terminal.println("Check-out acknowledgement not yet implemented");
                return this;
            case 3:
                return homePage;
        }

        return null;
    }
}
