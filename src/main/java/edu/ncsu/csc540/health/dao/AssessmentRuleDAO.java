package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.*;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface AssessmentRuleDAO {

    @SqlUpdate("insert into assessment_rules (priority, description) " +
            "values (:priority, :description)")
    @GetGeneratedKeys("id")
    Integer createAssessmentRule(@BindBean AssessmentRule assessmentRule);

    @SqlUpdate("insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) " +
            "values (:ruleId, :symptom.code, :body_part_code, :severityScaleValue.id, :operation)")
    void createAssessmentSymptom(@BindBean AssessmentSymptom assessmentSymptom);

    @SqlQuery("select r.id r_id, r.priority pr_priority, r.description r_description " +
            "s.code rs_code, s.name rs_name, " +
            "c.id sc_id, c.name sc_name, " +
            "b.code sb_code, b.name sb_name " +
            "from assessment_rules r, symptoms s, severity_scales c, body_parts b " +
            "where r.id = :id")
    @RegisterConstructorMapper(value = AssessmentRule.class, prefix = "r")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    @RegisterConstructorMapper(value = Priority.class, prefix = "p")
    AssessmentRule findById(@Bind("id") Integer id);

    @SqlQuery("select r.id r_id, r.priority r_priority, r.description r_description, " +
            "e.rule_id re_rule_id, e.body_part_code re_body_part_code, e.severity_scale_value_id re_severity_scale_value_id, e.operation re_operation, " +
            "s.code res_code, s.name res_name, " +
            "c.id resc_id, c.name resc_name, " +
            "b.code resb_code, b.name resb_name " +
            "from assessment_rules r, assessment_symptoms e, severity_scales c, body_parts b, symptoms s " +
            "where e.rule_id = r.id and e.symptom_code = s.code and s.severity_scale_id = c.id and s.body_part_code = b.code")
    @RegisterConstructorMapper(value = AssessmentRule.class, prefix = "r")
    @RegisterConstructorMapper(value = AssessmentSymptom.class, prefix = "e")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    @RegisterConstructorMapper(value = BodyPart.class, prefix = "b")
    List<AssessmentRule> findAllAssessmentRules();

    @SqlQuery("select e.rule_id e_rule_id, e.body_part_code e_body_part_code, e.operation e_operation, " +
            "s.code es_code, s.name es_name, " +
            "c.id esc_id, c.name esc_name, " +
            "b.code esb_code, b.name esb_name, " +
            "v.id ev_id, v.severity_scale_id ev_severity_scale_id, v.name ev_name, v.ordinal ev_ordinal " +
            "from assessment_symptoms e, symptoms s, severity_scales c, body_parts b, severity_scale_values v " +
            "where e.rule_id = :rule_id and e.symptom_code = s.code and s.severity_scale_id = c.id and s.body_part_code = b.code and e.severity_scale_value_id = v.id")
    @RegisterConstructorMapper(value = AssessmentSymptom.class, prefix = "e")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    @RegisterConstructorMapper(value = BodyPart.class, prefix = "b")
    @RegisterConstructorMapper(value = SeverityScaleValue.class, prefix = "v")
    List<AssessmentSymptom> findAllAssessmentSymptomsByRule(@Bind("rule_id") Integer ruleId);
}
