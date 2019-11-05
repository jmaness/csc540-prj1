package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.Staff;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;

public interface StaffDAO {
    @SqlQuery("select s.id s_id, s.first_name s_first_name, s.last_name s_last_name, s.designation s_designation, s.hire_date s_hire_date, " +
            "s.address_id s_address_id, s.facility_id s_facility_id, s.primary_department s_primary_department, " +
            "a.id a_id, a.num a_num, a.street a_street, a.city a_city, a.state a_state, a.country a_country " +
            "from staff s, addresses a where s.id = :id and s.address_id = a.id")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Staff findById(@Bind("id") Integer id);

    @SqlQuery("select s.id s_id, s.first_name s_first_name, s.last_name s_last_name, s.designation s_designation, s.hire_date s_hire_date, " +
            "s.address_id s_address_id, s.facility_id s_facility_id, s.primary_department s_primary_department, " +
            "a.id a_id, a.num a_num, a.street a_street, a.city a_city, a.state a_state, a.country a_country " +
            "from staff s, addresses a " +
            "where s.address_id = a.id and s.facility_id = :facility_id and s.last_name = :last_name and a.city = :city")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Staff validateSignIn(@Bind("facility_id") Integer facilityID,
                           @Bind("last_name") String lastName,
                           @Bind("city") String city);
}
