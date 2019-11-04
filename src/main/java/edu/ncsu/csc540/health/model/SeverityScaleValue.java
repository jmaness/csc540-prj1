package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class SeverityScaleValue {
    private Integer id;
    private Integer severityScaleId;
    private String name;
    private Integer ordinal;

    public SeverityScaleValue(@ColumnName("id") Integer id,
                              @ColumnName("severity_scale_id") Integer severityScaleId,
                              @ColumnName("name") String name,
                              @ColumnName("ordinal") Integer ordinal) {
        this.id = id;
        this.severityScaleId = severityScaleId;
        this.name = name;
        this.ordinal = ordinal;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeverityScaleId() {
        return severityScaleId;
    }

    public String getName() {
        return name;
    }

    public Integer getOrdinal() {
        return ordinal;
    }
}
