package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class Staff {
    private final Integer id;
    private final String first_name;
    private final String last_name;
    private final String designation;
    private final LocalDate hire_date;
    private final Integer facility_id;
    private final String primary_department;
    private final Address address;

    public Staff(@ColumnName("id") @Nullable Integer id,
                 @ColumnName("first_name") String firstName,
                 @ColumnName("last_name") String lastName,
                 @ColumnName("designation") String designation,
                 @ColumnName("hire_date") LocalDate hire_date,
                 @ColumnName("primary_department_code") String primary_department,
                 @Nested("a") @Nullable Address address,
                 @ColumnName("facility_id") Integer facilityId) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.designation = designation;
        this.hire_date = hire_date;
        this.primary_department = primary_department;
        this.facility_id = facilityId;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getHire_date() {
        return hire_date;
    }

    public Integer getFacility_id() {
        return facility_id;
    }

    public String getPrimary_department() {
        return primary_department;
    }

    public String getDisplayString() { return id + ": " + first_name + " " + last_name; }
}
