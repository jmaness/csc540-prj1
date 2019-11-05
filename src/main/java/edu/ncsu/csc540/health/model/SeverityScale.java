package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class SeverityScale {
    private final Integer id;
    private final String name;

    public SeverityScale(@ColumnName("id") Integer id,
                         @ColumnName("name") String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
