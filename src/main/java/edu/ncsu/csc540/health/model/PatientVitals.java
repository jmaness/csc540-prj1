package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class PatientVitals {
    private final Integer checkInId;
    private final Integer temperature;
    private final Integer systolicBloodPressure;
    private final Integer diastolicBloodPressure;

    public PatientVitals(@ColumnName("checkin_id") Integer checkInId,
                         @ColumnName("temperature") Integer temperature,
                         @ColumnName("systolic_blood_pressure") Integer systolicBloodPressure,
                         @ColumnName("diastolic_blood_pressure") Integer diastolicBloodPressure) {
        this.checkInId = checkInId;
        this.temperature = temperature;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public Integer getSystolicBloodPressure() {
        return systolicBloodPressure;
    }

    public Integer getDiastolicBloodPressure() {
        return diastolicBloodPressure;
    }
}
