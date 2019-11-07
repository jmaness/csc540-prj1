package edu.ncsu.csc540.health.model;

public enum NegativeExperienceCode {
    MISDIAGNOSIS("Misdiagnosis"),
    ACQUIRED_INFECTION("Patient acquired an infection during hospital stay");

    private String label;

    NegativeExperienceCode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
