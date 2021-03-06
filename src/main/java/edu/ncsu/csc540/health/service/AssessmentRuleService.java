package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.AssessmentRuleDAO;
import edu.ncsu.csc540.health.model.AssessmentRule;
import edu.ncsu.csc540.health.model.AssessmentSymptom;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AssessmentRuleService {
    private final AssessmentRuleDAO assessmentRuleDAO;

    @Inject
    public AssessmentRuleService(Jdbi jdbi) {
        this.assessmentRuleDAO = jdbi.onDemand(AssessmentRuleDAO.class);
    }

    /**
     * Creates an assessment rule
     * @param assessmentRule The assessment rule being created
     * @return The generated ID of the created assessment rule
     */
    public Integer createAssessmentRule(AssessmentRule assessmentRule) {
        return assessmentRuleDAO.createAssessmentRule(assessmentRule);
    }

    /**
     * Returns all assessment rules
     * @return A List containing all assessment rules
     */
    public List<AssessmentRule> findAllAssessmentRules() {
        List<AssessmentRule> tempRules = assessmentRuleDAO.findAllAssessmentRules();
        List<AssessmentRule> rules = new ArrayList<>();

        for (AssessmentRule rule : tempRules) {
            rules.add(new AssessmentRule(rule.getId(),
                    rule.getPriority(),
                    rule.getDescription(),
                    assessmentRuleDAO.findAllAssessmentSymptomsByRule(rule.getId())));
        }

        return rules;
    }
}
