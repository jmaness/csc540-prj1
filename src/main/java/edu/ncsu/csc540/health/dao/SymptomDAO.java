package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface SymptomDAO {

    String FIND_SYMPTOMS = "select s.code s_code, s.name s_name, " +
            "c.id sc_id, c.name sc_name, " +
            "b.code sb_code, b.name sb_name " +
            "from symptoms s, severity_scales c, body_parts b " +
            "where s.severity_scale_id = c.id and s.body_part_code = b.code " +
            "order by s.code";

    /**
     * Finds all symptoms in the database
     * @return A List containing all of the symptoms
     */
    @SqlQuery(FIND_SYMPTOMS)
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    List<Symptom> findAll();

    /**
     * Finds the symptom matching the code provided
     * @param code The code of the desired symptom
     * @return The matching symptom
     */
    @SqlQuery(FIND_SYMPTOMS + " and s.code = :code")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    Symptom findByCode(@Bind("code") String code);

    /**
     * Inserts a new symptom into the database
     * @param symptom The symptom being inserted
     * @return The generated code of the inserted symptom
     */
    @SqlUpdate("insert into symptoms (name, severity_scale_id, body_part_code) " +
            "values (:name, :severityScale.id, :bodyPart.code)")
    @GetGeneratedKeys("code")
    String create(@BindBean Symptom symptom);
}
