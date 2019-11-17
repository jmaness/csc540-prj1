package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.PatientVitals;
import edu.ncsu.csc540.health.model.Priority;
import edu.ncsu.csc540.health.model.Symptom;
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

    /**
     * Inserts a new patient into the database
     * @param patient The patient being inserted
     * @return The generated ID of the inserted patient
     */
    @SqlUpdate("insert into patients (facility_id, first_name, last_name, dob, address_id, phone) " +
               "values (:facilityId, :firstName, :lastName, :dob, :address.id, :phone)")
    @GetGeneratedKeys("id")
    Integer createPatient(@BindBean Patient patient);

    /**
     * Finds the patient matching the provided ID
     * @param id The ID of the desired patient
     * @return The matching patient
     */
    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a where p.id = :id and p.address_id = a.id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    Patient findPatientById(@Bind("id") Integer id);

    /**
     * Finds the patient matching the validatory information provided
     * @param facilityID The facility ID of the desired patient
     * @param lastName The last name of the desired patient
     * @param dob The date of birth of the desired patient
     * @param city The city listed on the address of the desired patient
     * @return The matching patient (or null, if no such patient exists)
     */
    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a " +
            "where p.address_id = a.id and p.facility_id = :facilityId and p.last_name = :lastName and p.dob = :dob and a.city = :city")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    Patient validateSignIn(@Bind("facilityId") Integer facilityID,
                           @Bind("lastName") String lastName,
                           @Bind("dob") LocalDate dob,
                           @Bind("city") String city);

    /**
     * Inserts a new check-in into the database
     * @param patientCheckin The check-in being inserted
     * @return The generated ID of the check-in being inserted
     */
    @SqlUpdate("insert into patient_checkins (patient_id, start_time, end_time) values (:patientId, :startTime, :endTime)")
    @GetGeneratedKeys("id")
    Integer createCheckIn(@BindBean PatientCheckIn patientCheckin);

    /**
     * Inserts a new symptom into the database
     * @param checkInSymptom The symptom being inserted
     */
    @SqlUpdate("insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) " +
            "values (:checkInId, :symptomCode, :bodyPartCode, :severityScaleValueId, :duration, :reoccurring, :incident)")
    void addSymptom(@BindBean CheckInSymptom checkInSymptom);

    /**
     * Finds a check-in matching the provided ID
     * @param id The ID of the desired check-in
     * @return The matching check-in
     */
    @SqlQuery(FIND_CHECKINS + " where c.id = :id")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findCheckInById(@Bind("id") int id);

    /**
     * Finds the *active* check-in associated with the patient matching the provided ID
     * @param patientId The ID of the patient associated with the desired check-in
     * @return The matching check-in
     */
    @SqlQuery(FIND_CHECKINS + " where c.patient_id = :patientId and c.active = 1")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findActivePatientCheckin(@Bind("patientId") Integer patientId);

    /**
     * Finds all patients on a facility's (matching the provided ID) priority list whose check-ins are still active
     * @param facilityId The ID of the facility to which the desired priority list belongs
     * @return A List containing the matching patients
     */
    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, patient_checkins c, priority_lists r " +
            "where p.address_id = a.id and p.id = c.patient_id and r.checkin_id = c.id and p.facility_id = :facility_id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> findAllPriorityPatients(@Bind("facility_id") Integer facilityId);

    /**
     * Finds all patients in a facility (matching the provided ID) whose check-ins are still active and are waiting on a
     * staff member to enter their vitals
     * @param facilityId The ID of the facility to which the desired check-in list belongs
     * @return A List containing the matching patients
     */
    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, patient_checkins c " +
            "where p.address_id = a.id and p.id = c.patient_id and c.end_time is null and p.facility_id = :facility_id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> findAllVitalsPatients(@Bind("facility_id") Integer facilityId);

    /**
     * Finds all symptoms associated with the active check-in of the patient matching the provided ID
     * @param id The ID of the desired patient
     * @return A List containing the matching symptoms
     */
    @SqlQuery("select s.code s_code, s.name s_name, " +
            "c.id sc_id, c.name sc_name, " +
            "b.code sb_code, b.name sb_name " +
            "from symptoms s, severity_scales c, body_parts b, patient_checkins p, checkin_symptoms h, priority_lists r " +
            "where s.severity_scale_id = c.id and s.body_part_code = b.code and p.patient_id = :id and p.id = h.checkin_id and h.symptom_code = s.code and r.checkin_id = p.id")
    @RegisterConstructorMapper(value = Symptom.class, prefix = "s")
    List<Symptom> findAllPatientSymptoms(@Bind("id") int id);

    /**
     * Updates a patient's (matching the provided ID) check-in so that its end-time reflects the provided end-time
     * @param id The ID of the patient associated with the desired check-in
     * @param endTime The end-time with which the check-in should be updated
     */
    @SqlUpdate("update patient_checkins " +
            "set end_time = :end_time " +
            "where patient_id = :id")
    void updateCheckInEndTime(@Bind("id") int id,
                              @Bind("end_time") Timestamp endTime);

    /**
     * Returns a list of all patients in the facility matching the provided ID who have received treatment and whose check-ins
     * are still active
     * @param facilityId The ID of the facility to which the desired patients belong
     * @return A List containing the matching patients
     */
    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id pa_id, a.num pa_num, a.street pa_street, a.city pa_city, a.state pa_state, a.country pa_country " +
            "from patients p, addresses a, priority_lists pl, patient_checkins pc " +
            "    left outer join outcome_reports r on pc.id = r.checkin_id " +
            "where p.address_id = a.id " +
            "    and p.id = pc.patient_id " +
            "    and pc.active = 1 " +
            "    and pc.end_time is not null " +
            "    and pc.id = pl.checkin_id " +
            "    and pl.end_time is not null " +
            "    and p.facility_id = :facilityId " +
            "    and r.out_time is null")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> getTreatedPatientList(@Bind("facilityId") Integer facilityId);

    /**
     * Updates a patient's (associated with the check-in matching the provided ID) entry on a priority list so that its
     * end-time reflects the provided end-time
     * @param checkInId The ID of the check-in associated with the desired patient (and, by extension, the desired priority
     *                  list entry)
     * @param endTime The end-time with which the priority list entry should be updated
     */
    @SqlUpdate("update priority_lists " +
            "set end_time = :end_time " +
            "where checkin_id = :checkin_id")
    void updatePriorityListEndTime(@Bind("checkin_id") int checkInId,
                                   @Bind("end_time") Timestamp endTime);

    /**
     * Finds the check-in ID of a patient (matching the provided patient ID) on a priority list
     * @param patientId The ID of the patient associated with the desired check-in
     * @return The desired check-in ID
     */
    @SqlQuery("select r.checkin_id " +
            "from patient_checkins c, priority_lists r " +
            "where c.patient_id = :patient_id and r.checkin_id = c.id")
    Integer findPriorityListCheckInId(@Bind("patient_id") Integer patientId);

    /**
     * Inserts a new entry into the priority list
     * @param checkinId The check-in ID associated to the new priority list entry
     * @param priority The priority associated to the new priority list entry
     * @param startTime The start time associated ot the new priority list entry
     */
    @SqlUpdate("insert into priority_lists (checkin_id, priority, start_time) " +
            "values (:checkin_id, :priority, :start_time)")
    void addPatientToPriorityList(@Bind("checkin_id") Integer checkinId,
                                  @Bind("priority") Priority priority,
                                  @Bind("start_time") Timestamp startTime);

    /**
     * Inserts a patient's vitals into the database
     * @param vitals The patient's vitals being inserted
     */
    @SqlUpdate("insert into patient_vitals (checkin_id, temperature, systolic_blood_pressure, diastolic_blood_pressure) " +
            "values (:checkInId, :temperature, :systolicBloodPressure, :diastolicBloodPressure)")
    void addPatientVitals(@BindBean PatientVitals vitals);

    /**
     * Updates a check-in matching the provided ID to mark it as inactive
     * @param checkInId The ID of the desired check-in
     */
    @SqlUpdate("update patient_checkins set active = 0 where id = :checkInId")
    void setVisitComplete(@Bind("checkInId") Integer checkInId);
}
