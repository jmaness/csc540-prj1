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

    public List<Staff> findAllMedicalStaffByFacility(Integer facilityId) {
        return staffDAO.findAllMedicalStaffByFacility(facilityId);
    }
}
