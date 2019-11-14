package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.NegativeExperience;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.ReferralReason;
import edu.ncsu.csc540.health.model.ReferralStatus;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface OutcomeReportDAO {

    @SqlUpdate("insert into outcome_reports (checkin_id, discharge_status, treatment, out_time, patient_acknowledged, patient_acknowledge_reason) " +
            "values (:checkInId, :dischargeStatus, :treatment, :outTime, :patientAcknowledged, :patientAcknowledgedReason)")
    void insertOutcomeReport(@BindBean OutcomeReport outcomeReport);

    @SqlUpdate("insert into referral_reasons (checkin_id, code, service_code, description) " +
            "values (:checkInId, :code, :serviceCode, :description)")
    void insertReferralReason(@BindBean ReferralReason referralReason);

    @SqlUpdate("insert into referral_statuses (checkin_id, facility_id, staff_id) " +
            "values (:checkInId, :facilityId, :staffId)")
    void insertReferralStatus(@BindBean ReferralStatus referralStatus);

    @SqlUpdate("insert into negative_experiences (checkin_id, code, description) " +
            "values (:checkInId, :code, :description)")
    void insertNegativeExperience(@BindBean NegativeExperience negativeExperience);
}
