package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.AddressDAO;
import edu.ncsu.csc540.health.dao.PatientDAO;
import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.Patient;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatientService {
    private final AddressDAO addressDAO;
    private final PatientDAO patientDAO;

    @Inject
    public PatientService(Jdbi jdbi) {
        addressDAO = jdbi.onDemand(AddressDAO.class);
        patientDAO = jdbi.onDemand(PatientDAO.class);
    }

    @Transactional
    public Patient signUp(Patient patient) {
        // Store the address
        Integer addressId = addressDAO.create(patient.getAddress());
        Address address = addressDAO.findById(addressId);

        // Store the patient referencing the new address
        int patientId = patientDAO.create(new Patient(null,
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDob(),
                address,
                patient.getPhone()));

        return patientDAO.findById(patientId);
    }
}