package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.AddressDAO;
import edu.ncsu.csc540.health.dao.OutcomeReportDAO;
import edu.ncsu.csc540.health.dao.PatientDAO;
import edu.ncsu.csc540.health.model.Address;
import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.DischargeStatus;
import edu.ncsu.csc540.health.model.OutcomeReport;
import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.PatientVitals;
import edu.ncsu.csc540.health.model.Priority;
import edu.ncsu.csc540.health.model.ReferralStatus;
import edu.ncsu.csc540.health.model.Symptom;
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

    @Transactional
    public void submitOutcomeReport(OutcomeReport outcomeReport) {
        outcomeReportDAO.insertOutcomeReport(outcomeReport);

        if (outcomeReport.getReferralStatus() != null) {
            outcomeReportDAO.insertReferralStatus(outcomeReport.getReferralStatus());
            outcomeReport.getReferralStatus().getReasons().forEach(outcomeReportDAO::insertReferralReason);
        }

        outcomeReport.getNegativeExperiences()
                .forEach(outcomeReportDAO::insertNegativeExperience);
    }

    @Transactional
    public void acknowledgeOutcomeReport(Integer checkInId) {
        outcomeReportDAO.acknowledgeOutcomeReport(checkInId);
        patientDAO.setVisitComplete(checkInId);
    }

    @Transactional
    public void rejectOutcomeReport(Integer checkInId, String reason) {
        outcomeReportDAO.rejectOutcomeReport(checkInId, reason);
        patientDAO.setVisitComplete(checkInId);
    }

    @Transactional
    public OutcomeReport findOutcomeReport(Integer checkInId) {
        OutcomeReport outcomeReport = outcomeReportDAO.findOutcomeReportById(checkInId);

        ReferralStatus referralStatus = null;
        if (outcomeReport.getDischargeStatus() == DischargeStatus.REFERRED) {
            referralStatus = outcomeReportDAO.findReferralStatusByCheckInId(checkInId);
        }

        return new OutcomeReport(outcomeReport.getCheckInId(),
                outcomeReport.getDischargeStatus(),
                referralStatus,
                outcomeReport.getTreatment(),
                outcomeReport.getOutTime(),
                outcomeReport.getNegativeExperiences(),
                outcomeReport.getPatientAcknowledged(),
                outcomeReport.getPatientAcknowledgedReason());
    }

    @Transactional
    public List<Patient> findAllPriorityPatients(Integer facilityId) {
        return patientDAO.findAllPriorityPatients(facilityId);
    }

    @Transactional
    public List<Patient> findAllVitalsPatients(Integer facilityId) {
        return patientDAO.findAllVitalsPatients(facilityId);
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

    public void updatePriorityListEndtime(Integer checkInId, Timestamp endTime) {
        patientDAO.updatePriorityListEndTime(checkInId, endTime);
    }

    public List<Patient> getPatientPriorityList() {
        return patientDAO.getPatientPriorityList();
    }

    public Integer findPriorityListCheckInId(Integer patientId) {
        return patientDAO.findPriorityListCheckInId(patientId);
    }

    public void addPatientToPriorityList(PatientCheckIn checkIn, Priority priority, Timestamp timestamp) {
        patientDAO.addPatientToPriorityList(checkIn.getId(), priority, timestamp);
    }

    public void addPatientVitals(PatientVitals vitals) {
        patientDAO.addPatientVitals(vitals);
    }
}
