package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class NegativeExperience {
    private final Integer checkInId;
    private final NegativeExperienceCode code;
    private final String description;

    public NegativeExperience(@ColumnName("checkin_id") Integer checkInId,
                              @ColumnName("code") NegativeExperienceCode code,
                              @ColumnName("description") String description) {
        this.checkInId = checkInId;
        this.code = code;
        this.description = description;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public NegativeExperienceCode getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
