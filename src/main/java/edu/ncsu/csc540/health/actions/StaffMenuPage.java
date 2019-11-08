package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.model.Symptom;
import edu.ncsu.csc540.health.service.SymptomService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

/**
 * The Home page is one of the start pages that all users initially see. This
 * page shows a menu which will allow the user to sign in, sign up as a patient
 * or run some demo queries.
 *
 */
public class StaffMenuPage implements Action {
    private final Action homePage;
    private final Staff staff;
    private final SymptomService symptomService;

    @Inject
    public StaffMenuPage(@Named("home") Action homePage,
                         @Assisted Staff staff,
                         SymptomService symptomService) {
        this.homePage = homePage;
        this.staff = staff;
        this.symptomService = symptomService;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checked-in patient list", Actions.notYetImplemented.apply(this)),
                        Pair.of("Treated patient list", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add symptoms", this::addSymptoms),
                        Pair.of("Add severity scale", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add assessment rule", Actions.notYetImplemented.apply(this)),
                        Pair.of("Go back", homePage)))
                .withValueFormatter(Pair::getKey)
                .read("Staff Menu")
                .getValue();
    }

    private void processPatient() {

    }

    private Action addSymptoms(TextIO textIO) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        String name = textIO.newStringInputReader()
                .read("A. Please enter the name of the symptom: ");

        List<BodyPart> bodyParts = symptomService.findAllBodyParts();

        BodyPart selectedBodyPart = textIO.<BodyPart>newGenericInputReader(null)
                .withNumberedPossibleValues(bodyParts)
                .withValueFormatter(BodyPart::getName)
                .withDefaultValue(null)
                .read("B. Please select the associated body part (leave blank if none applicable): ");

        List<SeverityScale> severityScales = symptomService.findAllSeverityScales();

        SeverityScale selectedScale = textIO.<SeverityScale>newGenericInputReader(null)
                .withNumberedPossibleValues(severityScales)
                .withValueFormatter(SeverityScale::getName)
                .withDefaultValue(null)
                .read("C. Please select the associated severity scale (leave blank if none applicable): ");

        terminal.println("Please confirm the following information:\n");
        terminal.println(String.format("Symptom Name: %s", name));
        terminal.println(String.format("Associated Body Part: %s", selectedBodyPart.getName()));
        terminal.println(String.format("Associated Severity Scale: %s", selectedScale.getName()));

        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Confirm", (TextIO tio) -> {
                            symptomService.createSymptom(new Symptom(null,
                                    name,
                                    selectedScale,
                                    selectedBodyPart));
                            return this;
                        }),
                        Pair.of("Go Back", this)))
                .withValueFormatter(Pair::getKey)
                .read()
                .getValue();
    }
}