package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class Staff {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String designation;
    private final LocalDate hireDate;
    private final Integer facilityId;
    private final Department primaryDepartment;
    private final Address address;

    public Staff(@ColumnName("id") @Nullable Integer id,
                 @ColumnName("first_name") String firstName,
                 @ColumnName("last_name") String lastName,
                 @ColumnName("designation") String designation,
                 @ColumnName("hire_date") LocalDate hireDate,
                 @ColumnName("facility_id") Integer facilityId,
                 @Nullable Department primaryDepartment,
                 @Nested("a") Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.hireDate = hireDate;
        this.facilityId = facilityId;
        this.primaryDepartment = primaryDepartment;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public Department getPrimaryDepartment() {
        return primaryDepartment;
    }

    public Address getAddress() {
        return address;
    }

    public String getDisplayString() { return firstName + " " + lastName; }
}
