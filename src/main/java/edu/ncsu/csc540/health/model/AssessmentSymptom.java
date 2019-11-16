package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;

public class AssessmentSymptom {
    private final Integer ruleId;
    private final Symptom symptom;
    private final String bodyPartCode;
    private final SeverityScaleValue severityScaleValue;
    private final Operation operation;

    public AssessmentSymptom(@Nullable @ColumnName("rule_id") Integer ruleId,
                             @Nested("s") Symptom symptom,
                             @ColumnName("body_part_code") String bodyPartCode,
                             @Nested("v") SeverityScaleValue severityScaleValue,
                             @ColumnName("operation") Operation operation) {
        this.ruleId = ruleId;
        this.symptom = symptom;
        this.bodyPartCode = bodyPartCode;
        this.severityScaleValue = severityScaleValue;
        this.operation = operation;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public Symptom getSymptom() {
        return symptom;
    }

    public String getBodyPartCode() {
        return bodyPartCode;
    }

    public SeverityScaleValue getSeverityScaleValue() {
        return severityScaleValue;
    }

    public Operation getOperation() {
        return operation;
    }
}
