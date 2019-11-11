package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.service.DemoQueryService;
import net.efabrika.util.DBTablePrinter;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.Arrays;

@Singleton
public class DemoQueryPage implements Action {
    private final Action homePage;
    private final DemoQueryService demoQueryService;

    @Inject
    public DemoQueryPage(@Named("home") Action homePage,
                         DemoQueryService demoQueryService) {
        this.homePage = homePage;
        this.demoQueryService = demoQueryService;
    }

    @Override
    public Action apply(TextIO textIO) {
        Action notYetImplemented = Actions.notYetImplemented.apply(this);

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Find all patients that were discharged but had negative experiences at any facility", notYetImplemented),
                        Pair.of("Find facilities that did not have a negative experience for a specific period", notYetImplemented),
                        Pair.of("For each facility, find the facility that it sends the most referrals to", notYetImplemented),
                        Pair.of("Find facilities that had no negative experience for patients with cardiac symptoms", notYetImplemented),
                        Pair.of("Find the facility with the most number of negative experiences overall", notYetImplemented),
                        Pair.of("Find each facility, list the patient encounters with the top five longest check-in phases", notYetImplemented),
                        Pair.of("Other", notYetImplemented),
                        Pair.of("DUMMY: Find all facilities", this::findAllFacilities),
                        Pair.of("Go back", homePage)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nDemo Queries\n================================")
                .getValue();
    }

    private Action findAllFacilities(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();

        demoQueryService.findAllFacilities((supplier, ctx) -> {
            try (ResultSet rs = supplier.get()) {
                DBTablePrinter.printResultSet(rs, new PrintWriter(new Writer() {
                    @Override
                    public void write(char[] cbuf, int off, int len) {
                        terminal.print(String.valueOf(cbuf, off, len));
                    }

                    @Override
                    public void flush() {}

                    @Override
                    public void close() {}
                }));
            }
            return null;
        });

        return this;
    }
}
