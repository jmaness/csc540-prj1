package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class BodyPart {
    private final String code;
    private final String name;

    public BodyPart(@ColumnName("code") String code,
                    @ColumnName("name") String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
