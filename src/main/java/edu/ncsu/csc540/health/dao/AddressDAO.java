package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;


public interface AddressDAO {

    /**
     * Adds an address to the database
     * @param address The address being added
     * @return The generated ID of the address
     */
    @SqlUpdate("insert into addresses (num, street, city, state, country) values (:streetNum, :street, :city, :state, :country)")
    @GetGeneratedKeys("id")
    Integer create(@BindBean Address address);

    /**
     * Finds the address matching the provided ID
     * @param id The ID of the desired address
     * @return The matched address
     */
    @SqlQuery("select * from addresses where id = :id")
    @RegisterConstructorMapper(Address.class)
    Address findById(@Bind("id") Integer id);

    /**
     * Updates a given address's attributes
     * @param address The address being updated
     */
    @SqlUpdate("update addresses " +
               "set num = :streetNum, " +
               "    street = :street, " +
               "    city = :city, " +
               "    state = :state, " +
               "    country = :country " +
               "where id = :id")
    void update(@BindBean Address address);
}
