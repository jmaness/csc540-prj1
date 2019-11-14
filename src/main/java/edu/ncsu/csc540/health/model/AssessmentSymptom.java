package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;

public class AssessmentSymptom {
    private final Integer ruleId;
    private final Symptom symptom;
    private final Integer severityScaleValueId;
    private final Operation operation;

    public AssessmentSymptom(@Nullable @ColumnName("rule_id") Integer ruleId,
                             @Nested("s") Symptom symptom,
                             @ColumnName("severity_scale_value_id") Integer severityScaleValueId,
                             @ColumnName("operation") String operation) {
        this.ruleId = ruleId;
        this.symptom = symptom;
        this.severityScaleValueId = severityScaleValueId;
        this.operation = Operation.fromString(operation);
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public Integer getSeverityScaleValueId() {
        return severityScaleValueId;
    }

    public Operation getOperation() {
        return operation;
    }
}
