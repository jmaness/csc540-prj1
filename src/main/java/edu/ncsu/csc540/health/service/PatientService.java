package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.AddressDAO;
import edu.ncsu.csc540.health.dao.OutcomeReportDAO;
import edu.ncsu.csc540.health.dao.PatientDAO;
import edu.ncsu.csc540.health.model.*;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class PatientService {
    private final AddressDAO addressDAO;
    private final PatientDAO patientDAO;
    private final OutcomeReportDAO outcomeReportDAO;

    @Inject
    public PatientService(Jdbi jdbi) {
        addressDAO = jdbi.onDemand(AddressDAO.class);
        patientDAO = jdbi.onDemand(PatientDAO.class);
        outcomeReportDAO = jdbi.onDemand(OutcomeReportDAO.class);
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
        return findActivePatientCheckIn(patient) != null;
    }

    public PatientCheckIn findActivePatientCheckIn(Patient patient) {
        return patientDAO.findActivePatientCheckin(patient.getId());
    }

    public PatientCheckIn findCheckInByPatient(Patient patient) {
        return patientDAO.findCheckInByPatient(patient);
    }

    @Transactional
    public void submitOutcomeReport(OutcomeReport outcomeReport) {
        outcomeReportDAO.insertOutcomeReport(outcomeReport);

        if (outcomeReport.getReferralStatus() != null) {
            outcomeReportDAO.insertReferralStatus(outcomeReport.getReferralStatus());
            outcomeReport.getReferralStatus().getReasons().forEach(outcomeReportDAO::insertReferralReason);
        }

        outcomeReport.getNegativeExperiences()
                .forEach(outcomeReportDAO::insertNegativeExperience);

        patientDAO.setComplete(outcomeReport.getCheckInId());
    }

    @Transactional
    public List<Patient> findAllPriorityPatients() {
        return patientDAO.findAllPriorityPatients();
    }

    @Transactional
    public List<Patient> findAllVitalsPatients() {
        return patientDAO.findAllVitalsPatients();
    }

    @Transactional
    public List<Symptom> findAllPatientSymptoms(Patient patient) {
        return patientDAO.findAllPatientSymptoms(patient.getId());
    }

    @Transactional
    public void updateCheckInEndtime(Patient patient, Timestamp endTime) {
        patientDAO.updateCheckInEndTime(patient.getId(), endTime);
    }

    public List<Patient> getTreatedPatientList(Integer facilityId) {
        return patientDAO.getTreatedPatientList(facilityId);
    }

    public List<Patient> getPatientPriorityList() {
        return patientDAO.getPatientPriorityList();
    }

    public void addPatientToPriorityList(PatientCheckIn checkIn, Priority priority, Timestamp timestamp) {
        patientDAO.addPatientToPriorityList(checkIn.getId(), priority.getName(), timestamp);
    }
}
