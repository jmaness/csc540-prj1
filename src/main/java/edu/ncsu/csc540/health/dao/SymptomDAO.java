package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    @RegisterRowMapper(SymptomMapper.class)
    Symptom findByCode(@Bind("code") String code);

    @SqlUpdate("insert into symptoms (name, severity_scale_id, body_part_code) " +
            "values (:name, :severityScale.id, :bodyPart.code)")
    @GetGeneratedKeys("code")
    String create(@BindBean Symptom symptom);

    class SymptomMapper implements RowMapper<Symptom> {
        @Override
        public Symptom map(ResultSet rs, StatementContext ctx) throws SQLException {
            SeverityScale scale = new SeverityScale(rs.getInt("c_id"),
                    rs.getString("c_name"));
            BodyPart part = new BodyPart(rs.getString("b_code"),
                    rs.getString("b_name"));
            Symptom symptom = new Symptom(rs.getString("s_code"),
                    rs.getString("s_name"),
                    scale,
                    part);

            return symptom;
        }
    }
}
