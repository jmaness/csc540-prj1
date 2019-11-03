package edu.ncsu.csc540.health.pages;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class DemoQueryPage implements Page {
    private final Page homePage;

    @Inject
    public DemoQueryPage(@Named("home") Page homePage) {
        this.homePage = homePage;
    }

    @Override
    public Page apply(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("Demo Queries");
        terminal.println("================================");
        terminal.println("1. Find all patients that were discharged but had negative experiences at any facility");
        terminal.println("2. Find facilities that did not have a negative experience for a specific period");
        terminal.println("3. For each facility, find the facility that it sends the most referrals to");
        terminal.println("4. Find facilities that had no negative experience for patients with cardiac symptoms");
        terminal.println("5. Find the facility with the most number of negative experiences overall");
        terminal.println("6. Find each facility, list the patient encounters with the top five longest check-in phases");
        terminal.println("7. Other");
        terminal.println("8. Go back");
        terminal.println();

        int option = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(8)
                .read("> ");

        switch (option) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                terminal.println();
                terminal.println("Not yet implemented");
                terminal.println();
                return this;
            case 8:
                return homePage;
        }

        return homePage;
    }
}
