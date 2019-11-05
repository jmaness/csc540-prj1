package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Symptom {
    private final String code;
    private final String name;
    private final SeverityScale scale;
    private final BodyPart bodyPart;

    public Symptom(@ColumnName("code") String code,
                   @ColumnName("name") String name,
                   @Nested("s") SeverityScale scale,
                   @Nested("b") BodyPart bodyPart) {
        this.code = code;
        this.name = name;
        this.scale = scale;
        this.bodyPart = bodyPart;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public SeverityScale getScale() {
        return scale;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }
}
