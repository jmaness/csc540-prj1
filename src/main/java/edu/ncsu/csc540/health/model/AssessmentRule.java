package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.util.List;

public class AssessmentRule {
    private final Integer id;
    private final Priority priority;
    private final String description;
    private final List<AssessmentSymptom> assessmentSymptoms;

    public AssessmentRule(@Nullable @ColumnName("id") Integer id,
                          @Nested("p") Priority priority,
                          @ColumnName("description") String description,
                          @Nullable List<AssessmentSymptom> assessmentSymptoms) {
        this.id = id;
        this.priority = priority;
        this.description = description;
        this.assessmentSymptoms = assessmentSymptoms;
    }

    public int getId() {
        return id;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public List<AssessmentSymptom> getAssessmentSymptoms() {
        return assessmentSymptoms;
    }
}
