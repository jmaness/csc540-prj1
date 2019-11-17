package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Facility;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface FacilityDAO {

    /**
     * Finds the facility matching a provided ID
     * @param id The ID of the desired facility
     * @return The matching facility
     */
    @SqlQuery("select * from facilities where id = :id")
    @RegisterConstructorMapper(Facility.class)
    Facility findById(@Bind("id") Integer id);

    /**
     * Finds all facilities in the database
     * @return A List containing all of the facilities
     */
    @SqlQuery("select * from facilities order by name")
    @RegisterConstructorMapper(Facility.class)
    List<Facility> findAll();
}
