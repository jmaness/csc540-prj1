package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.StaffDAO;
import edu.ncsu.csc540.health.model.Staff;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;

public class StaffService {
    private final StaffDAO staffDAO;

    @Inject
    public StaffService(Jdbi jdbi) {
        staffDAO = jdbi.onDemand(StaffDAO.class);
    }

    @Transactional
    public Staff signIn(Integer facilityID, String lastName, String city) {
        return staffDAO.validateSignIn(facilityID, lastName, city);
    }
}
