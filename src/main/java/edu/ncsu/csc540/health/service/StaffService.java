package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.StaffDAO;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.Department;
import edu.ncsu.csc540.health.model.Staff;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import java.util.List;

public class StaffService {
    private final StaffDAO staffDAO;

    @Inject
    public StaffService(Jdbi jdbi) {
        staffDAO = jdbi.onDemand(StaffDAO.class);
    }

    /**
     * Validates a staff member's credentials and returns a Staff object representing their account info
     * @param facilityID The staff member's facility ID
     * @param lastName The staff member's last name
     * @param city The city listed on the staff member's address
     * @return A Staff object representing the staff member's account info (or null, if the validation check failed)
     */
    @Transactional
    public Staff signIn(Integer facilityID, String lastName, String city) {
        Staff tempStaff = staffDAO.validateSignIn(facilityID, lastName, city);

        if (tempStaff == null)
            return null;

        Department tempDepartment = staffDAO.findStaffDepartment(tempStaff.getId());
        List<BodyPart> bodyParts = staffDAO.findDepartmentBodyParts(tempDepartment.getCode());

        Department department = new Department(tempDepartment.getCode(),
                tempDepartment.getName(),
                tempDepartment.getType(),
                tempDepartment.getFacilityId(),
                bodyParts);

        Staff staff = new Staff(tempStaff.getId(),
                tempStaff.getFirstName(),
                tempStaff.getLastName(),
                tempStaff.getDesignation(),
                tempStaff.getHireDate(),
                tempStaff.getFacilityId(),
                department,
                tempStaff.getAddress());

        return staff;
    }

    /**
     * Returns a staff member matching the provided ID
     * @param staffId The ID of the desired staff member
     * @return The matching staff member
     */
    public Staff findById(Integer staffId) {
        return staffDAO.findById(staffId);
    }

    /**
     * Returns all staff members that work at a given facility
     * @param facilityId The ID of the desired facility
     * @return A List containing all matching staff members
     */
    public List<Staff> findAllMedicalStaffByFacility(Integer facilityId) {
        return staffDAO.findAllMedicalStaffByFacility(facilityId);
    }
}
