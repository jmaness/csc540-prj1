package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.util.Set;

public class Service {
    private String code;
    private String name;
    private Set<Equipment> equipment;

    public Service(@ColumnName("code") String code,
                   @ColumnName("name") String name,
                   @Nullable Set<Equipment> equipment) {
        this.code = code;
        this.name = name;
        this.equipment = equipment;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Set<Equipment> getEquipment() {
        return equipment;
    }

    public String getDisplayString() {
        return code + " " + name;
    }
}
