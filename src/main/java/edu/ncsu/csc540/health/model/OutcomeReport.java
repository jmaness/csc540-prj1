package edu.ncsu.csc540.health.model;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public class OutcomeReport {
    private Integer checkInId;
    private DischargeStatus dischargeStatus;
    private ReferralStatus referralStatus;
    private String treatment;
    private LocalDateTime outTime;
    private List<NegativeExperience> negativeExperiences;
    private Boolean patientAcknowledged;
    private String patientAcknowledgedReason;

    public OutcomeReport(@ColumnName("checkin_id") Integer checkInId,
                         @ColumnName("discharge_status") DischargeStatus dischargeStatus,
                         @Nested("rs") @Nullable ReferralStatus referralStatus,
                         @ColumnName("treatment") String treatment,
                         @ColumnName("out_time") LocalDateTime outTime,
                         @Nullable List<NegativeExperience> negativeExperiences,
                         @ColumnName("patient_acknowledged") @Nullable Boolean patientAcknowledged,
                         @ColumnName("patient_acknowledged_reason") @Nullable String patientAcknowledgedReason) {
        this.checkInId = checkInId;
        this.dischargeStatus = dischargeStatus;
        this.referralStatus = referralStatus;
        this.treatment = treatment;
        this.outTime = outTime;
        this.negativeExperiences = negativeExperiences;
        this.patientAcknowledged = patientAcknowledged;
        this.patientAcknowledgedReason = patientAcknowledgedReason;
    }

    public Integer getCheckInId() {
        return checkInId;
    }

    public DischargeStatus getDischargeStatus() {
        return dischargeStatus;
    }

    public ReferralStatus getReferralStatus() {
        return referralStatus;
    }

    public String getTreatment() {
        return treatment;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public List<NegativeExperience> getNegativeExperiences() {
        return negativeExperiences;
    }

    public Boolean getPatientAcknowledged() {
        return patientAcknowledged;
    }

    public String getPatientAcknowledgedReason() {
        return patientAcknowledgedReason;
    }
}
