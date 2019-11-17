package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.BodyPart;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface BodyPartDAO {

    /**
     * Finds all body parts in the database
     * @return A List containing all of the body parts
     */
    @SqlQuery("select * from body_parts " +
            "order by code")
    @RegisterConstructorMapper(BodyPart.class)
    List<BodyPart> findAllBodyParts();
}
