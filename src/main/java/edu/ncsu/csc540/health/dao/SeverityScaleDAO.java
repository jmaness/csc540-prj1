package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface SeverityScaleDAO {
    @SqlQuery("select * from severity_scales")
    @RegisterConstructorMapper(SeverityScale.class)
    List<SeverityScale> findAllSeverityScales();

    @SqlQuery("select * from severity_scale_values where severity_scale_id = :severityScaleId order by ordinal")
    @RegisterConstructorMapper(SeverityScaleValue.class)
    List<SeverityScaleValue> findSeverityScaleValues(@Bind("severityScaleId") Integer severityScaleId);
}
