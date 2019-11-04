package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Symptom {
    private final String code;
    private final String name;
    private final Integer severityScaleId;
    private final String bodyPartCode;

    public Symptom(@ColumnName("code") String code,
                   @ColumnName("name") String name,
                   @ColumnName("severity_scale_id") Integer severityScaleId,
                   @ColumnName("body_part_code") String bodyPartCode) {
        this.code = code;
        this.name = name;
        this.severityScaleId = severityScaleId;
        this.bodyPartCode = bodyPartCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getSeverityScaleId() {
        return severityScaleId;
    }

    public String getBodyPartCode() {
        return bodyPartCode;
    }
}
