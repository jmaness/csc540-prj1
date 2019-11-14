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

    @Override
    public String toString() {
        return name;
    }

    public static Priority fromString(String priority) {
        switch (priority.toUpperCase()) {
            case "NORMAL":
                return NORMAL;
            case "HIGH":
                return HIGH;
            case "QUARANTINE":
                return QUARANTINE;
            default:
                return null;
        }
    }
}
