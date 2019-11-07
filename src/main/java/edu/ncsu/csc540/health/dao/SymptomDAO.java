package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.util.List;

public interface SymptomDAO {

    @SqlQuery("select * from symptoms")
    @RegisterConstructorMapper(Symptom.class)
    List<Symptom> findAll();

    @SqlQuery("select s.code s_code, s.name s_name, " +
            "c.id c_id, c.name c_name, " +
            "b.code b_code, b.name b_name " +
            "from symptoms s, severity_scales c, body_parts b " +
            "where s.code = :code and s.severity_scale_id = c.id and s.body_part_code = b.code")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    @RegisterConstructorMapper(value = SeverityScale.class, prefix = "c")
    //@RegisterConstructorMapper(value = BodyPart.class, prefix = "b")
    Symptom findByCode(@Bind("code") String code);

    @SqlUpdate("insert into symptoms (name, severity_scale_id, body_part_code) " +
            "values (:name, :severityScale.id, :bodyPart.code)")
    @GetGeneratedKeys("code")
    String create(@BindBean Symptom symptom);
}
