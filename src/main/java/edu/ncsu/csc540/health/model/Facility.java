package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Facility {
    private final Integer id;
    private final String name;
    private final Integer capacity;
    private final String classificationCode;

    public Facility(@ColumnName("id") Integer id,
                    @ColumnName("name") String name,
                    @ColumnName("capacity") Integer capacity,
                    @ColumnName("classification_code") String classificationCode) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.classificationCode = classificationCode;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getClassificationCode() {
        return classificationCode;
    }
}
