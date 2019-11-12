package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AssessmentRuleDAO {

    @SqlUpdate("insert into assessment_rules (priority, description) " +
            "values (:priority, :description)")
    @GetGeneratedKeys("id")
    Integer createAssessmentRule(@BindBean AssessmentRule assessmentRule);

    @SqlUpdate("insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) " +
            "values (:ruleId, :symptom.code, :severityScaleValue.id, :operation)")
    void createAssessmentSymptom(@BindBean AssessmentSymptom assessmentSymptom);

    @SqlQuery("select r.id r_id, r.priority r_priority, r.description r_description " +
            "s.code rs_code, s.name rs_name, " +
            "c.id sc_id, c.name sc_name, " +
            "b.code sb_code, b.name sb_name " +
            "from assessment_rules r, symptoms s, severity_scales c, body_parts b " +
            "where r.id = :id")
    @RegisterConstructorMapper(value = AssessmentRule.class, prefix = "r")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    AssessmentRule findById(@Bind("id") Integer id);
}
