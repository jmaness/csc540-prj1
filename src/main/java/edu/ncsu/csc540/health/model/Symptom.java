package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;

public class Symptom {
    private final String code;
    private final String name;
    private final SeverityScale severityScale;
    private final BodyPart bodyPart;

    public Symptom(@ColumnName("code") String code,
                   @ColumnName("name") String name,
                   @Nullable @Nested("c") SeverityScale severityScale,
                   @Nullable @Nested("b") BodyPart bodyPart) {
        this.code = code;
        this.name = name;
        this.severityScale = severityScale;
        this.bodyPart = bodyPart;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public SeverityScale getSeverityScale() {
        return severityScale;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }
}
