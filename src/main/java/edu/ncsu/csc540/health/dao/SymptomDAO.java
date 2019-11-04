package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface SymptomDAO {

    @SqlQuery("select * from symptoms")
    @RegisterConstructorMapper(Symptom.class)
    List<Symptom> findAll();
}
