package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface SeverityScaleDAO {

    /**
     * Finds all severity scales in the database
     * @return A List containing all of the severity scales
     */
    @SqlQuery("select * from severity_scales")
    @RegisterConstructorMapper(SeverityScale.class)
    List<SeverityScale> findAllSeverityScales();

    /**
     * Finds all severity scale values associated with the severity scale matching the provided ID
     * @param severityScaleId The ID of the desired severity scale
     * @return A List containing all of the matching severity scale values
     */
    @SqlQuery("select * from severity_scale_values where severity_scale_id = :severityScaleId order by ordinal")
    @RegisterConstructorMapper(SeverityScaleValue.class)
    List<SeverityScaleValue> findSeverityScaleValues(@Bind("severityScaleId") Integer severityScaleId);

    /**
     * Inserts a new severity scale into the database
     * @param scale The severity scale being inserted
     * @return The generated ID of the inserted severity scale
     */
    @SqlUpdate("insert into severity_scales (name) " +
            "values (:name)")
    @GetGeneratedKeys("id")
    Integer createScale(@BindBean SeverityScale scale);

    /**
     * Inserts a new severity scale value into the database
     * @param scaleValue The severity scale value being inserted
     */
    @SqlUpdate("insert into severity_scale_values (severity_scale_id, name, ordinal) " +
            "values (:severityScaleId, :name, :ordinal)")
    void createScaleValue(@BindBean SeverityScaleValue scaleValue);

    /**
     * Finds the severity scale value matching the provided ID
     * @param severityScaleValueId The ID of the desired severity scale value
     * @return The matching severity scale value
     */
    @SqlQuery("select * " +
            "from severity_scale_values " +
            "where id = :id")
    @RegisterConstructorMapper(SeverityScaleValue.class)
    SeverityScaleValue findSeverityScaleValueById(@Bind("id") Integer severityScaleValueId);
}
