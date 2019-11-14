package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.*;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface PatientDAO {

    String FIND_CHECKINS = "select c.id c_id, c.patient_id c_patient_id, c.start_time c_start_time, c.end_time c_end_time, " +
            "cs.checkin_id cs_checkin_id, cs.symptom_code cs_symptom_code, cs.body_part_code cs_body_part_code, " +
            "cs.severity_scale_value_id cs_severity_scale_value_id, cs.duration cs_duration, " +
            "cs.reoccurring cs_reoccurring, cs.incident cs_incident " +
            "from patient_checkins c " +
            "left outer join checkin_symptoms cs " +
            "  on c.id = cs.checkin_id ";

    @SqlUpdate("insert into patients (facility_id, first_name, last_name, dob, address_id, phone) " +
               "values (:facilityId, :firstName, :lastName, :dob, :address.id, :phone)")
    @GetGeneratedKeys("id")
    Integer create(@BindBean Patient patient);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a where p.id = :id and p.address_id = a.id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    Patient findById(@Bind("id") Integer id);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a " +
            "where p.address_id = a.id and p.facility_id = :facilityId and p.last_name = :lastName and p.dob = :dob and a.city = :city")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    Patient validateSignIn(@Bind("facilityId") Integer facilityID,
                           @Bind("lastName") String lastName,
                           @Bind("dob") LocalDate dob,
                           @Bind("city") String city);

    @SqlUpdate("insert into patient_checkins (patient_id, start_time, end_time) values (:patientId, :startTime, :endTime)")
    @GetGeneratedKeys("id")
    Integer createCheckIn(@BindBean PatientCheckIn patientCheckin);

    @SqlUpdate("insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) " +
            "values (:checkInId, :symptomCode, :bodyPartCode, :severityScaleValueId, :duration, :reoccurring, :incident)")
    void addSymptom(@BindBean CheckInSymptom checkInSymptom);

    @SqlQuery(FIND_CHECKINS + " where c.id = :id")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findCheckInById(@Bind("id") int id);

    @SqlQuery(FIND_CHECKINS +
            "left outer join outcome_reports r " +
            "    on c.id = r.checkin_id " +
            "where c.patient_id = :patientId and r.out_time is null")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findActivePatientCheckin(@Bind("patientId") Integer patientId);

    @SqlQuery("select c.id c_id, c.patient_id c_patient_id, c.start_time c_start_time, c.end_time c_end_time, " +
            "cs.checkin_id cs_checkin_id, cs.symptom_code cs_symptom_code, cs.body_part_code cs_body_part_code, " +
            "cs.severity_scale_value_id cs_severity_scale_value_id, cs.duration cs_duration, " +
            "cs.reoccurring cs_reoccurring, cs.incident cs_incident " +
            "from patient_checkins c " +
            "left outer join checkin_symptoms cs " +
            "  on c.id = cs.checkin_id " +
            "where c.patient_id = :id and c.end_time is null")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findCheckInByPatient(@BindBean Patient patient);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, patient_checkins c, priority_lists r " +
            "where p.address_id = a.id and p.id = c.patient_id and r.checkin_id = c.id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> findAllPriorityPatients();

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, patient_checkins c " +
            "where p.address_id = a.id and p.id = c.patient_id and c.end_time is null")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> findAllVitalsPatients();

    @SqlQuery("select s.code s_code, s.name s_name, " +
            "c.id sc_id, c.name sc_name, " +
            "b.code sb_code, b.name sb_name " +
            "from symptoms s, severity_scales c, body_parts b, patient_checkins p, checkin_symptoms h, priority_lists r " +
            "where s.severity_scale_id = c.id and s.body_part_code = b.code and p.patient_id = :id and p.id = h.checkin_id and h.symptom_code = s.code and r.checkin_id = p.id")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    List<Symptom> findAllPatientSymptoms(@Bind("id") int id);

    @SqlUpdate("update patient_checkins " +
            "set end_time = :end_time " +
            "where patient_id = :id")
    void updateCheckInEndTime(@Bind("id") int id,
                              @Bind("end_time") Timestamp endTime);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, patient_checkins pc, priority_lists pl " +
            "where p.address_id = a.id " +
            "    and p.id = pc.patient_id " +
            "    and pc.active = 1 " +
            "    and pc.end_time is not null " +
            "    and pc.id = pl.checkin_id " +
            "    and pl.end_time is not null " +
            "    and p.facility_id = :facilityId")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> getTreatedPatientList(@Bind("facilityId") Integer facilityId);

    @SqlUpdate("update patient_checkins set active = 0 where id = :checkInId")
    void setComplete(@Bind("checkInId") Integer checkInId);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, priority_lists r, patient_checkins c " +
            "where r.checkin_id = c.id and c.patient_id = p.id and p.address_id = a.id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> getPatientPriorityList();

    @SqlUpdate("insert into priority_lists (checkin_id, priority, start_time) " +
            "values (:checkin_id, :priority, :start_time)")
    void addPatientToPriorityList(@Bind("checkin_id") Integer checkinId,
                                  @Bind("priority") String priority,
                                  @Bind("start_time") Timestamp startTime);

    @SqlUpdate("insert into patient_vitals (checkin_id, temperature, systolic_blood_pressure, diastolic_blood_pressure) " +
            "values (:checkInId, :temperature, :systolicBloodPressure, :diastolicBloodPressure")
    void addPatientVitals(@BindBean PatientVitals vitals);

    @SqlQuery("select v.checkin_id v_checkin_id, v.temperature v_temperature, v.systolic_blood_pressure v_systolic_blood_pressure, v.diastolic_blood_pressure, v_diastolic blood pressure " +
            "from patient_vitals v" +
            "where v.checkin_id = :checkin_id")
    @RegisterConstructorMapper(value = PatientVitals.class, prefix = "v")
    PatientVitals findPatientVitalsByCheckIn(@Bind("checkin_id") Integer checkInId);
}
