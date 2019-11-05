package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;

public class Address {
    private final Integer id;
    private final Integer streetNum;
    private final String street;
    private final String city;
    private final String state;
    private final String country;

    public Address(@ColumnName("id") @Nullable Integer id,
                   @ColumnName("num") Integer streetNum,
                   @ColumnName("street") String street,
                   @ColumnName("city") String city,
                   @ColumnName("state") String state,
                   @ColumnName("country") String country) {
        this.id = id;
        this.streetNum = streetNum;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStreetNum() {
        return streetNum;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
