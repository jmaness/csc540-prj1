package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PatientCheckInRowReducer implements LinkedHashMapRowReducer<Integer, PatientCheckIn> {

    @Override
    public void accumulate(Map<Integer, PatientCheckIn> container, RowView rowView) {
        final PatientCheckIn patientCheckIn = container.computeIfAbsent(rowView.getColumn("c_id", Integer.class),
                id -> rowView.getRow(PatientCheckIn.class));

        if (rowView.getColumn("cs_checkin_id", Integer.class) != null) {
            List<CheckInSymptom> allSymptoms = Optional.ofNullable(patientCheckIn.getSymptoms())
                    .orElse(new ArrayList<>());

            allSymptoms.add(rowView.getRow(CheckInSymptom.class));

            container.put(patientCheckIn.getId(), new PatientCheckIn(
                    patientCheckIn.getId(),
                    patientCheckIn.getPatientId(),
                    patientCheckIn.getStartTime(),
                    patientCheckIn.getEndTime(),
                    allSymptoms
            ));
        }
    }
}
