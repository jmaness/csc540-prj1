package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.AssessmentRuleDAO;
import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import java.util.List;

public class AssessmentRuleService {
    private final AssessmentRuleDAO assessmentRuleDAO;

    @Inject
    public AssessmentRuleService(Jdbi jdbi) {
        this.assessmentRuleDAO = jdbi.onDemand(AssessmentRuleDAO.class);
    }

    public Integer createAssessmentRule(AssessmentRule assessmentRule) {
        Integer id = assessmentRuleDAO.createAssessmentRule(assessmentRule);

        for (AssessmentSymptom symptom : assessmentRule.getAssessmentSymptoms())
            assessmentRuleDAO.createAssessmentSymptom(new AssessmentSymptom(id,
                    symptom.getSymptom(),
                    symptom.getSeverityScaleValue(),
                    symptom.getOperation()));

        return id;
    }

    public List<AssessmentRule> findAllAssessmentRules() {
        return assessmentRuleDAO.findAllAssessmentRules();
    }
}
