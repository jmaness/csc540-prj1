package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.FacilityDAO;
import edu.ncsu.csc540.health.dao.ServiceDAO;
import edu.ncsu.csc540.health.model.Facility;
import edu.ncsu.csc540.health.model.Service;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class FacilityService {
    private FacilityDAO facilityDAO;
    private ServiceDAO serviceDAO;

    @Inject
    public FacilityService(Jdbi jdbi) {
        facilityDAO = jdbi.onDemand(FacilityDAO.class);
        serviceDAO = jdbi.onDemand(ServiceDAO.class);
    }

    public List<Facility> findAllFacilities() {
        return facilityDAO.findAll();
    }

    public List<Service> findAllServicesByFacility(Integer facilityId) {
        return serviceDAO.findAllServicesByFacilityId(facilityId);
    }
}
