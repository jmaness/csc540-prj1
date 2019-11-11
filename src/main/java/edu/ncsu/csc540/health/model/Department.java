package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.util.List;

public class Department {
    private final String code;
    private final String name;
    private final String type;
    private final Integer facilityId;
    private final List<BodyPart> bodyParts;

    public Department(@ColumnName("code") String code,
                      @ColumnName("name") String name,
                      @ColumnName("type") String type,
                      @ColumnName("facility_id") Integer facilityId,
                      @Nullable List<BodyPart> bodyParts) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.facilityId = facilityId;
        this.bodyParts = bodyParts;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public List<BodyPart> getBodyParts() {
        return bodyParts;
    }
}
