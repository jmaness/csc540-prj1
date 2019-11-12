package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface AssessmentRuleDAO {

    @SqlUpdate("insert into assessment_rules (priority, description) " +
            "values (:priority, :description)")
    @GetGeneratedKeys("id")
    Integer createAssessmentRule(@BindBean AssessmentRule assessmentRule);

    @SqlUpdate("insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) " +
            "values (:rule_id, :symptom.code, :severityScaleValue.id, :operation)")
    void createAssessmentSymptom(@BindBean AssessmentSymptom assessmentSymptom);
}
