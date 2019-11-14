package edu.ncsu.csc540.health.actions;

import com.google.inject.assistedinject.Assisted;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import edu.ncsu.csc540.health.model.Symptom;
import edu.ncsu.csc540.health.service.PatientService;
import edu.ncsu.csc540.health.service.SymptomService;
import org.apache.commons.lang3.tuple.Triple;
import org.beryx.textio.TextIO;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientCheckInPage implements Action {
    private final Action homePage;
    private final PatientService patientService;
    private final SymptomService symptomService;
    private final Patient patient;

    private List<CheckInSymptom> checkInSymptoms = new ArrayList<>();

    @Inject
    public PatientCheckInPage(@Named("home") Action homePage,
                              PatientService patientService,
                              SymptomService symptomService,
                              @Assisted Patient patient) {
        this.homePage = homePage;
        this.patientService = patientService;
        this.symptomService = symptomService;
        this.patient = patient;
    }

    @Override
    public Action apply(TextIO textIO) {
        List<Symptom> symptoms = symptomService.findAllSymptoms();
        System.out.println(symptoms.size());
        List<Triple<Symptom, String, Action>> symptomActions = symptoms.stream()
                .map(s -> Triple.of(s, s.getName(), getSymptomMeta(s, this)))
                .collect(Collectors.toList());

        symptomActions.add(Triple.of(null,"Done", this::completeCheckin));

        return textIO.<Triple<Symptom, String, Action>>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptomActions)
                    .withValueFormatter(Triple::getMiddle)
                    .read("Symptom")
                    .getRight()
                    .apply(textIO);
    }

    private Action getSymptomMeta(Symptom symptom, Action next) {
        return (TextIO textIO) -> {
            BodyPart bodyPart = textIO.<BodyPart>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptomService.findAllBodyParts())
                    .withValueFormatter(BodyPart::getName)
                    .read("Body part");

            int duration = textIO.newIntInputReader()
                    .withMinVal(0)
                    .read("Duration");

            boolean reoccurring = textIO.newBooleanInputReader()
                    .withDefaultValue(false)
                    .read("Reoccurring?");

            SeverityScaleValue severity = textIO.<SeverityScaleValue>newGenericInputReader(null)
                    .withNumberedPossibleValues(symptomService.findSeverityScaleValues(symptom.getSeverityScale().getId()))
                    .withValueFormatter(SeverityScaleValue::getName)
                    .read("Severity");

            String incident = textIO.newStringInputReader()
                    .withMaxLength(1000)
                    .read("Cause (incident)");

            checkInSymptoms.add(new CheckInSymptom(null, symptom.getCode(), bodyPart.getCode(), severity.getId(), duration, reoccurring, incident));
            return next;
        };
    }

    private Action completeCheckin(TextIO textIO) {
        patientService.checkIn(new PatientCheckIn(null, patient.getId(), LocalDateTime.now(), null, checkInSymptoms));
        return homePage;
    }
}
