package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.time.LocalDate;

public interface PatientDAO {

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

    @SqlQuery("select * from patient_checkins where patient_id = :id and end_time is null")
    @RegisterConstructorMapper(value = PatientCheckIn.class)
    PatientCheckIn findActivePatientCheckin(@Bind("id") Integer patientId);

    @SqlUpdate("insert into patient_checkins (patient_id, start_time, end_time) values (:patientId, :startTime, :endTime)")
    @GetGeneratedKeys("id")
    Integer createCheckIn(@BindBean PatientCheckIn patientCheckin);

    @SqlUpdate("insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) " +
            "values (:checkInId, :symptomCode, :bodyPartCode, :severityScaleValueId, :duration, :reoccurring, :incident)")
    void addSymptom(@BindBean CheckInSymptom checkInSymptom);

    @SqlQuery("select c.id c_id, c.patient_id c_patient_id, c.start_time c_start_time, c.end_time c_end_time, " +
            "cs.checkin_id cs_checkin_id, cs.symptom_code cs_symptom_code, cs.body_part_code cs_body_part_code, " +
            "cs.severity_scale_value_id cs_severity_scale_value_id, cs.duration cs_duration, " +
            "cs.reoccurring cs_reoccurring, cs.incident cs_incident " +
            "from patient_checkins c " +
            "left outer join checkin_symptoms cs " +
            "  on c.id = cs.checkin_id " +
            "where c.id = :id")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findCheckInById(@Bind("id") int id);
}
