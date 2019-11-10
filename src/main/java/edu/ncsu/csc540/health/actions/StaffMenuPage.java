package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.Staff;
import edu.ncsu.csc540.health.service.PatientService;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
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
    private final PatientService patientService;

    @Inject
    public StaffMenuPage(@Named("home") Action homePage,
                         @Assisted Staff staff,
                         PatientService patientService) {
        this.homePage = homePage;
        this.staff = staff;
        this.patientService = patientService;
    }

    @Override
    public Action apply(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checked-in patient list", Actions.notYetImplemented.apply(this)),
                        Pair.of("Treated patient list", this::treatedPatientListMenu),
                        Pair.of("Add symptoms", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add severity scale", Actions.notYetImplemented.apply(this)),
                        Pair.of("Add assessment rule", Actions.notYetImplemented.apply(this)),
                        Pair.of("Go back", homePage)))
                .withValueFormatter(Pair::getKey)
                .read("Staff Menu")
                .getValue();
    }

    private Action treatedPatientListMenu(TextIO textIO) {
        return textIO.<Pair<String, Action>>newGenericInputReader(null)
                .withNumberedPossibleValues(Arrays.asList(
                        Pair.of("Checkout patient", this::treatedPatientList),
                        Pair.of("Go back", this::apply)))
                .withValueFormatter(Pair::getKey)
                .read("Treated Patient Menu")
                .getValue();
    }

    private Action treatedPatientList(TextIO textIO) {
        List<Patient> patients = patientService.getTreatedPatientList();

        Patient selectedPatient = textIO.<Patient>newGenericInputReader(null)
                .withNumberedPossibleValues(patients)
                .withValueFormatter(Patient::getDisplayString)
                .read("Patient");

        //Jeremy, patient info is above. Ready to hook up next step
        return Actions.notYetImplemented.apply(this);
    }
}