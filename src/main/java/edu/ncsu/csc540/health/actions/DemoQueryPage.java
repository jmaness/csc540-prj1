package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.service.DemoQueryService;
import net.efabrika.util.DBTablePrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.jdbi.v3.core.result.ResultSetScanner;
import org.jdbi.v3.core.statement.StatementContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.function.Supplier;

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
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Find all patients that were discharged but had negative experiences at any facility",
                                safelyRun(this::findPatientsWithNegativeExperiences)),
                        Pair.of("Find facilities that did not have a negative experience for a specific period",
                                safelyRun(this::findFacilitiesWithNoNegativeExperiences)),
                        Pair.of("For each facility, find the facility that it sends the most referrals to",
                                safelyRun(this::findFacilitiesMostReferred)),
                        Pair.of("Find facilities that had no negative experience for patients with cardiac symptoms",
                                safelyRun(this::findFacilitiesWithNoNegativeCardiacExperiences)),
                        Pair.of("Find the facility with the most number of negative experiences overall",
                                safelyRun(this::findFacilitiesWithMostNegativeExperiences)),
                        Pair.of("For each facility, list the patient encounters with the top five longest check-in phases",
                                safelyRun(this::findLongestCheckinPhases)),
                        Pair.of("Other", safelyRun(this::runOtherQuery)),
                        Pair.of("Go back", homePage)
                ))
                .withValueFormatter(Pair::getKey)
                .read("\nDemo Queries\n---------------------------------------")
                .getValue();
    }

    private Action safelyRun(Action action) {
        return (TextIO textIO) -> {
            TextTerminal<?> terminal = textIO.getTextTerminal();
            try {
                terminal.println();
                action.apply(textIO);
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message = e.getCause().getMessage();
                }
                terminal.println("Error: " + message);
            }

            return this;
        };
    }

    private Action findPatientsWithNegativeExperiences(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        demoQueryService.findPatientsWithNegativeExperiences(getResultSetScanner(terminal));
        return this;
    }

    private Action findFacilitiesWithNoNegativeExperiences(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();

        LocalDate startTime = LocalDate.parse(
                textIO.newStringInputReader()
                        .withPattern("\\d{1,2}/\\d{1,2}/\\d{4}")
                        .read("Date range start"),
                DateTimeFormatter.ofPattern("M/d/yyyy"));

        LocalDate endTime = LocalDate.parse(
                textIO.newStringInputReader()
                        .withPattern("\\d{1,2}/\\d{1,2}/\\d{4}")
                        .read("Date range end"),
                DateTimeFormatter.ofPattern("M/d/yyyy"));

        demoQueryService.findFacilitiesWithNoNegativeExperiences(getResultSetScanner(terminal), startTime, endTime);
        return this;
    }

    private Action findFacilitiesMostReferred(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        demoQueryService.findFacilitiesMostReferred(getResultSetScanner(terminal));
        return this;
    }

    private Action findFacilitiesWithNoNegativeCardiacExperiences(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        demoQueryService.findFacilitiesWithNoNegativeCardiacExperiences(getResultSetScanner(terminal));
        return this;
    }

    private Action findFacilitiesWithMostNegativeExperiences(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        demoQueryService.findFacilitiesWithMostNegativeExperiences(getResultSetScanner(terminal));
        return this;
    }

    private Action findLongestCheckinPhases(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();
        demoQueryService.findLongestCheckinPhases(getResultSetScanner(terminal));
        return this;
    }

    private Action runOtherQuery(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println();

        String query = StringUtils.stripEnd(textIO.newStringInputReader()
                .withMaxLength(1000)
                .read("Query"), ";");

        demoQueryService.runQuery(getResultSetScanner(terminal), query);
        return this;
    }

    private ResultSetScanner<Void> getResultSetScanner(TextTerminal<?> terminal) {
        return (Supplier<ResultSet> supplier, StatementContext ctx) -> {
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
        };
    }
}
