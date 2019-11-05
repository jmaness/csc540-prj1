package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Named;

public class StaffMenu implements Page {
    private final Page homePage;

    public StaffMenu(@Named("home") Page homePage) {
        this.homePage = homePage;
    }

    @Override
    public Page apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        erminal.println("Staff Menu");
        terminal.println("=====================");
        terminal.println("1. Checked-in patient list");
        terminal.println("2. Treated patient list");
        terminal.println("3. Add symptom");
        terminal.println("4. Add severity scale");
        terminal.println("5. Add assessment rule");
        terminal.println("6. Go back");

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(6)
                .read("> ");

        switch (option) {
            case 1:
                terminal.println("Checked-in patient list not yet implemented");
                return this;
            case 2:
                terminal.println("Treated patient list not yet implemented");
                return this;
            case 3:
                terminal.println("Add symptom not yet implemented");
                return this;
            case 4:
                terminal.println("Add severity scale not yet implemented");
                return this;
            case 5:
                terminal.println("Add assessment rule not yet implemented");
                return this;
            case 6:
                return homePage;
        }

        return null;
    }
}