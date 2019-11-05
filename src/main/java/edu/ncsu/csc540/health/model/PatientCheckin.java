package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class PatientCheckin {
    private Integer patientId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public PatientCheckin(@ColumnName("patient_id") Integer patientId,
                          @ColumnName("start_time") LocalDateTime startTime,
                          @ColumnName("end_time") @Nullable LocalDateTime endTime) {
        this.patientId = patientId;
        this.startTime = startTime;
        this.endTime = endTime;
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
}
