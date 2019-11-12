package edu.ncsu.csc540.health.model;

public enum ReferralReasonCode {
    SERVICE_UNAVAILABLE("Service unavailable at time of visit"),
    SERVICE_NOT_PRESENT_AT_FACILITY("Service not present at facility"),
    NONPAYMENT("Non-payment");

    private String label;

    ReferralReasonCode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
