package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class Patient {
    private final Integer id;
    private final Integer facilityId;
    private final String firstName;
    private final String lastName;
    private final LocalDate dob;
    private final Address address;
    private final String phone;

    public Patient(@ColumnName("id") @Nullable Integer id,
                   @ColumnName("facility_id") Integer facilityId,
                   @ColumnName("first_name") String firstName,
                   @ColumnName("last_name") String lastName,
                   @ColumnName("dob") LocalDate dob,
                   @Nested("a") Address address,
                   @ColumnName("phone") String phone) {
        this.id = id;
        this.facilityId = facilityId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
