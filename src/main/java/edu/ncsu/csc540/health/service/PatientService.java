package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.AddressDAO;
import edu.ncsu.csc540.health.dao.PatientDAO;
import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.List;

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
                patient.getFacilityId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDob(),
                address,
                patient.getPhone()));

        return patientDAO.findById(patientId);
    }

    @Transactional
    public Patient signIn(Integer facilityID, String lastName, LocalDate dob, String city) {
        return patientDAO.validateSignIn(facilityID, lastName, dob, city);
    }

    @Transactional
    public PatientCheckIn checkIn(PatientCheckIn patientCheckin) {
        int checkInId = patientDAO.createCheckIn(patientCheckin);
        patientCheckin.getSymptoms().forEach(symptom -> patientDAO.addSymptom(new CheckInSymptom(
                checkInId,
                symptom.getSymptomCode(),
                symptom.getBodyPartCode(),
                symptom.getSeverityScaleValueId(),
                symptom.getDuration(),
                symptom.isReoccurring(),
                symptom.getIncident()
        )));
        return patientDAO.findCheckInById(checkInId);
    }

    public boolean isCheckedIn(Patient patient) {
        return patientDAO.findActivePatientCheckin(patient.getId()) != null;
    }

    public List<Patient> getTreatedPatientList() {
        return patientDAO.getTreatedPatientList();
    }
}
