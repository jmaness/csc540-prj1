package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientCheckIn {
    private final Integer id;
    private final Integer patientId;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<CheckInSymptom> symptoms;

    public PatientCheckIn(@ColumnName("id") Integer id,
                          @ColumnName("patient_id") Integer patientId,
                          @ColumnName("start_time") LocalDateTime startTime,
                          @ColumnName("end_time") @Nullable LocalDateTime endTime,
                          @Nullable List<CheckInSymptom> symptoms) {
        this.id = id;
        this.patientId = patientId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.symptoms = symptoms;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<CheckInSymptom> getSymptoms() {
        return symptoms;
    }
}
