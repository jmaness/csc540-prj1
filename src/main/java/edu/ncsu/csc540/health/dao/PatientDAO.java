package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckin;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;

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
            "where p.address_id = a.id and p.facility_id = :facility_id and p.last_name = :last_name and p.dob = :dob and a.city = :city")
    @RegisterConstructorMapper(value = Patient.class, prefix = "p")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Patient validateSignIn(@Bind("facility_id") Integer facilityID,
                           @Bind("last_name") String lastName,
                           @Bind("dob") LocalDate dob,
                           @Bind("city") String city);

    @SqlQuery("select * from patient_checkin where patient_id = :id and end_time is null")
    @RegisterConstructorMapper(value = PatientCheckin.class)
    PatientCheckin findActivePatientCheckin(@Bind("id") Integer patientId);
}
