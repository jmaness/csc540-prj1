package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Equipment {
    private String name;

    public Equipment(@ColumnName("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
