package edu.ncsu.csc540.health.model;

public enum DischargeStatus {
    TREATED_SUCCESSFULLY("Treated Successfully"),
    DECEASED("Deceased"),
    REFERRED("Referred");

    private String label;

    DischargeStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
