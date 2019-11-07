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
import java.util.List;

public interface PatientDAO {

    @SqlUpdate("insert into patients (facility_id, first_name, last_name, dob, address_id, phone) " +
               "values (:facilityId, :firstName, :lastName, :dob, :address.id, :phone)")
    @GetGeneratedKeys("id")
    Integer create(@BindBean Patient patient);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id a_id, a.num a_num, a.street a_street, a.city a_city, a.state a_state, a.country a_country " +
            "from patients p, addresses a where p.id = :id and p.address_id = a.id")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Patient findById(@Bind("id") Integer id);

    @SqlQuery("select p.id p_id, p.facility_id p_facility_id, p.first_name p_first_name, p.last_name p_last_name, p.dob p_dob, p.phone p_phone, " +
            "a.id a_id, a.num a_num, a.street a_street, a.city a_city, a.state a_state, a.country a_country " +
            "from patients p, addresses a " +
            "where p.address_id = a.id and p.facility_id = :facilityId and p.last_name = :lastName and p.dob = :dob and a.city = :city")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Patient validateSignIn(@Bind("facilityId") Integer facilityID,
                           @Bind("lastName") String lastName,
                           @Bind("dob") LocalDate dob,
                           @Bind("city") String city);

    @SqlQuery("select * from patient_checkin where patient_id = :id and end_time is null")
    @RegisterConstructorMapper(value = PatientCheckIn.class)
    PatientCheckIn findActivePatientCheckin(@Bind("id") Integer patientId);

    @SqlUpdate("insert into patient_checkin (patient_id, start_time, end_time) values (:patientId, :startTime, :endTime)")
    @GetGeneratedKeys("id")
    Integer createCheckIn(@BindBean PatientCheckIn patientCheckin);

    @SqlUpdate("insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) " +
            "values (:checkInId, :symptomCode, :bodyPartCode, :severityScaleValueId, :duration, :reoccurring, :incident)")
    void addSymptom(@BindBean CheckInSymptom checkInSymptom);

    @SqlQuery("select c.id c_id, c.patient_id c_patient_id, c.start_time c_start_time, c.end_time c_end_time, " +
            "cs.checkin_id cs_checkin_id, cs.symptom_code cs_symptom_code, cs.body_part_code cs_body_part_code, " +
            "cs.severity_scale_value_id cs_severity_scale_value_id, cs.duration cs_duration, " +
            "cs.reoccurring cs_reoccurring, cs.incident cs_incident " +
            "from patient_checkin c " +
            "left outer join checkin_symptoms cs " +
            "  on c.id = cs.checkin_id " +
            "where c.id = :id")
    @RegisterConstructorMapper(value = PatientCheckIn.class, prefix = "c")
    @RegisterConstructorMapper(value = CheckInSymptom.class, prefix = "cs")
    @UseRowReducer(PatientCheckInRowReducer.class)
    PatientCheckIn findCheckInById(@Bind("id") int id);

    @SqlQuery("select p.id, p.first_name, p.last_name, p.dob, p.phone, p.address_id, p.facility_id " +
            "from patients p " +
            "where p.id in(" +
            "    select pc.patient_id" +
            "    from patient_checkin pc" +
            "    where pc.end_time is not null and pc.id in(" +
            "        select pl.checkin_id" +
            "        from priority_lists pl" +
            "        where pl.end_time is not null" +
            "));")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    List<Patient> getTreatedPatientList();
}
