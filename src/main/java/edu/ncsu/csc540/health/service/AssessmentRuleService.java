package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.AssessmentRuleDAO;
import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;

public class AssessmentRuleService {
    private final AssessmentRuleDAO assessmentRuleDAO;

    @Inject
    public AssessmentRuleService(Jdbi jdbi) {
        this.assessmentRuleDAO = jdbi.onDemand(AssessmentRuleDAO.class);
    }

    public Integer createAssessmentRule(AssessmentRule assessmentRule) {
        return assessmentRuleDAO.createAssessmentRule(assessmentRule);
    }

    public void createAssessmentSymptom(AssessmentSymptom assessmentSymptom) {
        assessmentRuleDAO.createAssessmentSymptom(assessmentSymptom);
    }
}
