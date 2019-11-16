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
    @SqlQuery("select * from severity_scales")
    @RegisterConstructorMapper(SeverityScale.class)
    List<SeverityScale> findAllSeverityScales();

    @SqlQuery("select * from severity_scale_values where severity_scale_id = :severityScaleId order by ordinal")
    @RegisterConstructorMapper(SeverityScaleValue.class)
    List<SeverityScaleValue> findSeverityScaleValues(@Bind("severityScaleId") Integer severityScaleId);

    @SqlUpdate("insert into severity_scales (name) " +
            "values (:name)")
    @GetGeneratedKeys("id")
    Integer createScale(@BindBean SeverityScale scale);

    @SqlUpdate("insert into severity_scale_values (severity_scale_id, name, ordinal) " +
            "values (:severityScaleId, :name, :ordinal)")
    void createScaleValue(@BindBean SeverityScaleValue scaleValue);

    @SqlUpdate("select * " +
            "from severity_scale_values " +
            "where id = :id")
    @RegisterConstructorMapper(SeverityScaleValue.class)
    SeverityScaleValue findSeverityScaleValueById(@Bind("id") Integer severityScaleValueId);
}
