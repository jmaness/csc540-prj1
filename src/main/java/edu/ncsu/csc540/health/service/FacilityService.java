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

    /**
     * Returns the facility matching the provided ID
     * @param facilityId The ID of the desired facility
     * @return The desired facility
     */
    public Facility findById(Integer facilityId) {
        return facilityDAO.findById(facilityId);
    }

    /**
     * Returns all facilities
     * @return A List containing all facilities
     */
    public List<Facility> findAllFacilities() {
        return facilityDAO.findAll();
    }

    /**
     * Returns the service matching the provided code
     * @param code The code of the desired service
     * @return The desired service
     */
    public Service findServiceByCode(String code) {
        return serviceDAO.findServiceByCode(code);
    }

    /**
     * Returns all services provided by a facility
     * @param facilityId The ID of the desired facility
     * @return A List containing all relevant services
     */
    public List<Service> findAllServicesByFacility(Integer facilityId) {
        return serviceDAO.findAllServicesByFacilityId(facilityId);
    }

    /**
     * Returns all services
     * @return A List containing all services
     */
    public List<Service> findAllServices() {
        return serviceDAO.findAllServices();
    }
}
