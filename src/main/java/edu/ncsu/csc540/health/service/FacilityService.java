package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.FacilityDAO;
import edu.ncsu.csc540.health.model.Facility;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class FacilityService {
    private FacilityDAO facilityDAO;

    @Inject
    public FacilityService(Jdbi jdbi) {
        facilityDAO = jdbi.onDemand(FacilityDAO.class);
    }

    public List<Facility> findAllFacilities() {
        return facilityDAO.findAll();
    }
}
