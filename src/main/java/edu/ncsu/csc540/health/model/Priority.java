package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public enum Priority {
    NORMAL("Normal"),
    HIGH("High"),
    QUARANTINE("Quarantine");

    private final String name;

    Priority(@ColumnName("priority") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
