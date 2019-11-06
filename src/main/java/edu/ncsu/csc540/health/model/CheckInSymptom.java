package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;

public class CheckInSymptom {
    private Integer checkInId;
    private String symptomCode;
    private String bodyPartCode;
    private Integer severityScaleValueId;
    private Integer duration;
    private boolean reoccurring;
    private String incident;

    public CheckInSymptom(@ColumnName("checkin_id") @Nullable Integer checkInId,
                          @ColumnName("symptom_code") String symptomCode,
                          @ColumnName("body_part_code") String bodyPartCode,
                          @ColumnName("severity_scale_value_id") Integer severityScaleValueId,
                          @ColumnName("duration") Integer duration,
                          @ColumnName("reoccurring") boolean reoccurring,
                          @ColumnName("incident") String incident) {
        this.checkInId = checkInId;
        this.symptomCode = symptomCode;
        this.bodyPartCode = bodyPartCode;
        this.severityScaleValueId = severityScaleValueId;
        this.duration = duration;
        this.reoccurring = reoccurring;
        this.incident = incident;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public String getSymptomCode() {
        return symptomCode;
    }

    public String getBodyPartCode() {
        return bodyPartCode;
    }

    public Integer getSeverityScaleValueId() {
        return severityScaleValueId;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean isReoccurring() {
        return reoccurring;
    }

    public String getIncident() {
        return incident;
    }
}
