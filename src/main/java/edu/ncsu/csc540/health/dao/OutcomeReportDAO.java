package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.NegativeExperience;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.ReferralReason;
import edu.ncsu.csc540.health.model.ReferralStatus;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @SqlUpdate("update outcome_reports " +
            "set patient_acknowledged = 1, " +
            "    patient_acknowledge_reason = null " +
            "where checkin_id = :checkInId")
    void acknowledgeOutcomeReport(@Bind("checkInId") Integer checkInId);

    @SqlUpdate("update outcome_reports " +
            "set patient_acknowledged = 0, " +
            "    patient_acknowledge_reason = :reason " +
            "where checkin_id = :checkInId")
    void rejectOutcomeReport(@Bind("checkInId") Integer checkInId, @Bind("reason") String reason);

    @SqlQuery("select r.checkin_id r_checkin_id, r.discharge_status r_discharge_status, r.treatment r_treatment, " +
            "r.out_time r_out_time, r.patient_acknowledged r_patient_acknowledged, r.patient_acknowledge_reason r_patient_acknowledge_reason, " +
            "ne.checkin_id ne_checkin_id, ne.code ne_code, ne.description ne_description " +
            "from outcome_reports r " +
            "    left outer join negative_experiences ne on r.checkin_id = ne.checkin_id " +
            "where r.checkin_id = :id")
    @RegisterConstructorMapper(value = OutcomeReport.class, prefix = "r")
    @RegisterConstructorMapper(value = NegativeExperience.class, prefix = "ne")
    @UseRowReducer(OutcomeReportRowReducer.class)
    OutcomeReport findOutcomeReportById(@Bind("id") Integer id);

    @SqlQuery("select rs.checkin_id rs_checkin_id, rs.facility_id rs_facility_id, rs.staff_id rs_staff_id, " +
            "rr.checkin_id rr_checkin_id, rr.code rr_code, rr.service_code rr_service_code, rr.description rr_description " +
            "from referral_statuses rs " +
            "    left outer join referral_reasons rr on rs.checkin_id = rr.checkin_id " +
            "where rs.checkin_id = :checkInId")
    @RegisterConstructorMapper(value = ReferralStatus.class, prefix = "rs")
    @RegisterConstructorMapper(value = ReferralReason.class, prefix = "rr")
    @UseRowReducer(ReferralStatusRowReducer.class)
    ReferralStatus findReferralStatusByCheckInId(@Bind("checkInId") Integer checkInId);

    class OutcomeReportRowReducer implements LinkedHashMapRowReducer<Integer, OutcomeReport> {

        @Override
        public void accumulate(Map<Integer, OutcomeReport> container, RowView rowView) {
            final OutcomeReport outcomeReport = container.computeIfAbsent(rowView.getColumn("r_checkin_id", Integer.class),
                    id -> rowView.getRow(OutcomeReport.class));

            if (rowView.getColumn("ne_checkin_id", Integer.class) != null) {
                List<NegativeExperience> allNegativeExperiences = Optional.ofNullable(outcomeReport.getNegativeExperiences())
                        .orElse(new ArrayList<>());

                allNegativeExperiences.add(rowView.getRow(NegativeExperience.class));

                container.put(outcomeReport.getCheckInId(), new OutcomeReport(
                        outcomeReport.getCheckInId(),
                        outcomeReport.getDischargeStatus(),
                        null,
                        outcomeReport.getTreatment(),
                        outcomeReport.getOutTime(),
                        allNegativeExperiences,
                        outcomeReport.getPatientAcknowledged(),
                        outcomeReport.getPatientAcknowledgedReason()
                ));
            }
        }
    }

    class ReferralStatusRowReducer implements LinkedHashMapRowReducer<Integer, ReferralStatus> {

        @Override
        public void accumulate(Map<Integer, ReferralStatus> container, RowView rowView) {
            final ReferralStatus referralStatus = container.computeIfAbsent(rowView.getColumn("rs_checkin_id", Integer.class),
                    id -> rowView.getRow(ReferralStatus.class));

            if (rowView.getColumn("rr_checkin_id", Integer.class) != null) {
                List<ReferralReason> allReasons = Optional.ofNullable(referralStatus.getReasons())
                        .orElse(new ArrayList<>());

                allReasons.add(rowView.getRow(ReferralReason.class));

                container.put(referralStatus.getCheckInId(), new ReferralStatus(
                        referralStatus.getCheckInId(),
                        referralStatus.getFacilityId(),
                        referralStatus.getStaffId(),
                        allReasons));
            }
        }
    }
}
