package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.Department;
import edu.ncsu.csc540.health.model.Staff;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface StaffDAO {

    String FIND_STAFF = "select s.id s_id, s.first_name s_first_name, s.last_name s_last_name, s.designation s_designation, s.hire_date s_hire_date, " +
            "s.address_id s_address_id, s.facility_id s_facility_id, " +
            "a.id sa_id, a.num sa_num, a.street sa_street, a.city sa_city, a.state sa_state, a.country sa_country " +
            "from staff s, addresses a " +
            "    where s.address_id = a.id ";

    /**
     * Finds the staff member matching the provided ID
     * @param id The ID of the desired staff member
     * @return The matching staff member
     */
    @SqlQuery(FIND_STAFF + " and s.id = :id and s.address_id = a.id")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    Staff findById(@Bind("id") Integer id);

    /**
     * Finds the staff member matching the validatory information provided
     * @param facilityID The facility ID of the desired staff member
     * @param lastName The last name of the desired staff member
     * @param city The city listed on the address of the desired staff member
     * @return The matching staff member (or null, if no such patient exists)
     */
    @SqlQuery(FIND_STAFF + " and s.facility_id = :facilityId and s.last_name = :lastName and a.city = :city")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    Staff validateSignIn(@Bind("facilityId") Integer facilityID,
                           @Bind("lastName") String lastName,
                           @Bind("city") String city);

    /**
     * Finds all medical staff members associated with the facility matching the provided ID
     * @param facilityId The ID of the desired facility
     * @return A List containing all of the matching staff members
     */
    @SqlQuery(FIND_STAFF + " and s.facility_id = :facilityId and s.designation = 'Medical'")
    @RegisterConstructorMapper(value = Staff.class, prefix = "s")
    List<Staff> findAllMedicalStaffByFacility(@Bind("facilityId") Integer facilityId);

    /**
     * Finds the department of the staff member matching the provided ID
     * @param id The ID of the desired staff member
     * @return The matching department
     */
    @SqlQuery("select d.code d_code, d.name d_name, d.type d_type, d.facility_id d_facility_id " +
            "from departments d, staff s " +
            "where s.id = :id and s.primary_department_code = d.code")
    @RegisterConstructorMapper(value = Department.class, prefix = "d")
    Department findStaffDepartment(@Bind("id") Integer id);

    /**
     * Finds all body parts serviced by the department matching the provided code
     * @param code The code of the desired department
     * @return A List containing all of the matching body parts
     */
    @SqlQuery("select b.code b_code, b.name b_name " +
            "from body_parts b, departments_body_parts d " +
            "where d.department_code = :code and d.body_part_code = b.code")
    @RegisterConstructorMapper(value = BodyPart.class, prefix = "b")
    List<BodyPart> findDepartmentBodyParts(@Bind("code") String code);
}
