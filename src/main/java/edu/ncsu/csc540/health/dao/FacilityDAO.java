package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Facility;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface FacilityDAO {

    @SqlQuery("select * from facilities where id = :id")
    @RegisterConstructorMapper(Facility.class)
    Facility findById(@Bind("id") Integer id);

    @SqlQuery("select * from facilities order by name")
    @RegisterConstructorMapper(Facility.class)
    List<Facility> findAll();
}
