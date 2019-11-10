package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.Staff;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface StaffDAO {

    String FIND_STAFF = "select s.id s_id, s.first_name s_first_name, s.last_name s_last_name, s.designation s_designation, s.hire_date s_hire_date, " +
            "s.address_id s_address_id, s.facility_id s_facility_id, s.primary_department_code s_primary_department_code, " +
            "a.id a_id, a.num a_num, a.street a_street, a.city a_city, a.state a_state, a.country a_country " +
            "from staff s, addresses a";


    @SqlQuery(FIND_STAFF + " where s.id = :id and s.address_id = a.id")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Staff findById(@Bind("id") Integer id);

    @SqlQuery(FIND_STAFF + " where s.address_id = a.id and s.facility_id = :facilityId and s.last_name = :lastName and a.city = :city")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    Staff validateSignIn(@Bind("facilityId") Integer facilityID,
                           @Bind("lastName") String lastName,
                           @Bind("city") String city);

    @SqlQuery(FIND_STAFF + " where s.facility_id = :facilityId and s.designation = 'MEDICAL'")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    @RegisterConstructorMapper(value = Address.class, prefix = "a")
    List<Staff> findAllMedicalStaffByFacility(@Bind("facilityId") Integer facilityId);
}
