package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.AddressDAO;
import edu.ncsu.csc540.health.dao.OutcomeReportDAO;
import edu.ncsu.csc540.health.dao.PatientDAO;
import edu.ncsu.csc540.health.dao.SeverityScaleDAO;
import edu.ncsu.csc540.health.model.*;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PatientService {
    private final AddressDAO addressDAO;
    private final PatientDAO patientDAO;
    private final SeverityScaleDAO severityScaleDAO;
    private final OutcomeReportDAO outcomeReportDAO;
    private AssessmentRuleService assessmentRuleService;

    @Inject
    public PatientService(Jdbi jdbi, AssessmentRuleService assessmentRuleService) {
        this.addressDAO = jdbi.onDemand(AddressDAO.class);
        this.patientDAO = jdbi.onDemand(PatientDAO.class);
        this.severityScaleDAO = jdbi.onDemand(SeverityScaleDAO.class);
        this.outcomeReportDAO = jdbi.onDemand(OutcomeReportDAO.class);
        this.assessmentRuleService = assessmentRuleService;
    }

    /**
     * Creates an account for a patient
     * @param patient The patient whose account is being created
     * @return A Patient object representing the patient's account info
     */
    @Transactional
    public Patient signUp(Patient patient) {
        // Store the address
        Integer addressId = addressDAO.create(patient.getAddress());
        Address address = addressDAO.findById(addressId);

        // Store the patient referencing the new address
        int patientId = patientDAO.createPatient(new Patient(null,
                patient.getFacilityId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDob(),
                address,
                patient.getPhone()));

        return patientDAO.findPatientById(patientId);
    }

    /**
     * Validates a patient's credentials and returns a Patient object representing their account info
     * @param facilityID The patient's facility ID
     * @param lastName The patient's last name
     * @param dob The patient's date of birth
     * @param city The city listed on the patient's address
     * @return A Patient object representing the patient's account info (or null, if the validation check failed)
     */
    @Transactional
    public Patient signIn(Integer facilityID, String lastName, LocalDate dob, String city) {
        return patientDAO.validateSignIn(facilityID, lastName, dob, city);
    }

    /**
     * Checks a patient into a facility
     * @param patientCheckin An object representing the patient's check-in
     * @return An object representing the patient's check-in
     */
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

    /**
     * Determines whether the given patient has an active check-in or not
     * @param patient The patient in question
     * @return True if the patient has an active check-in; false otherwise
     */
    public boolean isCheckedIn(Patient patient) {
        return findActivePatientCheckIn(patient) != null;
    }

    /**
     * Returns a Patient object if the given patient has an active check-in
     * @param patient The patient in question
     * @return A Patient object representing the patient's account info (or null if the patient doesn't have an active check-in)
     */
    public PatientCheckIn findActivePatientCheckIn(Patient patient) {
        return patientDAO.findActivePatientCheckin(patient.getId());
    }

    /**
     * Submits the given outcome report
     * @param outcomeReport The outcome report being submitted
     */
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

    /**
     * Allows the patient to acknowledge an outcome report and thus complete their check-out
     * @param checkInId The ID of the check-in to which the outcome report is associated
     */
    @Transactional
    public void acknowledgeOutcomeReport(Integer checkInId) {
        outcomeReportDAO.acknowledgeOutcomeReport(checkInId);
        patientDAO.setVisitComplete(checkInId);
    }

    /**
     * Allows the patient to reject an outcome report and thus complete their check-out
     * @param checkInId The ID of the check-in to which the outcome report is associated
     * @param reason The reason for which the patient is rejecting the outcome report
     */
    @Transactional
    public void rejectOutcomeReport(Integer checkInId, String reason) {
        outcomeReportDAO.rejectOutcomeReport(checkInId, reason);
        patientDAO.setVisitComplete(checkInId);
    }

    /**
     * Returns the outcome report associated with the check-in matching the provided ID
     * @param checkInId The ID of the desired check-in
     * @return The matching outcome report
     */
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

    /**
     * Returns all active patients on the priority list for a given facility
     * @param facilityId The ID of the desired facility
     * @return A List containing all of the matching patients
     */
    @Transactional
    public List<Patient> findAllPriorityPatients(Integer facilityId) {
        return patientDAO.findAllPriorityPatients(facilityId);
    }

    /**
     * Returns all active patients awaiting vitals entry for a given facility
     * @param facilityId The ID of the desired facility
     * @return A List containing all of the matching patients
     */
    @Transactional
    public List<Patient> findAllVitalsPatients(Integer facilityId) {
        return patientDAO.findAllVitalsPatients(facilityId);
    }

    /**
     * Returns all symptoms associated with the active check-in of a given patient
     * @param patient The patient whose symptoms are being retrieved
     * @return A List containing all relevant symptoms
     */
    @Transactional
    public List<Symptom> findAllPatientSymptoms(Patient patient) {
        return patientDAO.findAllPatientSymptoms(patient.getId());
    }

    /**
     * Updates the check-in end-time of a given patient
     * @param patient The patient whose check-in is being updated
     * @param endTime The end-time being written to the check-in
     */
    @Transactional
    public void updateCheckInEndtime(Patient patient, Timestamp endTime) {
        patientDAO.updateCheckInEndTime(patient.getId(), endTime);
    }

    /**
     * Gets a list of all active patients who have been treated at a given facility
     * @param facilityId The ID of the desired facility
     * @return A List containing all relevant patients
     */
    public List<Patient> getTreatedPatientList(Integer facilityId) {
        return patientDAO.getTreatedPatientList(facilityId);
    }

    /**
     * Updates the priority list entry end-time of a given patient (identified by associated check-in)
     * @param checkInId The ID of the check-in associated to the desired patient
     * @param endTime The end-time being written to the priority list entry
     */
    public void updatePriorityListEndtime(Integer checkInId, Timestamp endTime) {
        patientDAO.updatePriorityListEndTime(checkInId, endTime);
    }

    /**
     * Returns the check-in ID associated with a given patient's entry on a priority list
     * @param patientId The ID of the desired patient
     * @return The matching check-in ID
     */
    public Integer findPriorityListCheckInId(Integer patientId) {
        return patientDAO.findPriorityListCheckInId(patientId);
    }

    /**
     * Adds a patient to a priority list
     * @param checkIn The check-in associated with the desired patient
     * @param priority The priority of the patient's entry on the priority list
     * @param timestamp The start-time for the patient's priority list entry
     */
    public void addPatientToPriorityList(PatientCheckIn checkIn, Priority priority, Timestamp timestamp) {
        patientDAO.addPatientToPriorityList(checkIn.getId(), priority, timestamp);
    }

    /**
     * Logs patient's vitals and assesses patient's priority based on their listed symptoms
     * @param vitals The vitals of the patient being logged
     * @return The patient's priority level, as determined by their listed symptoms
     */
    @Transactional
    public Priority confirmPatientVitals(PatientVitals vitals) {
        PatientCheckIn checkIn = patientDAO.findCheckInById(vitals.getCheckInId());
        Patient selectedPatient = patientDAO.findPatientById(checkIn.getPatientId());

        patientDAO.addPatientVitals(vitals);
        updateCheckInEndtime(selectedPatient, new Timestamp(System.currentTimeMillis()));

        List<AssessmentRule> rules = assessmentRuleService.findAllAssessmentRules();
        List<CheckInSymptom> symptoms = checkIn.getSymptoms();

        List<AssessmentRule> applicableRules = new ArrayList<>();

        for (AssessmentRule rule : rules) {
            List<AssessmentSymptom> aSymptoms = rule.getAssessmentSymptoms();
            boolean ruleMatched = true;

            for (AssessmentSymptom aSymptom : aSymptoms) {
                boolean symptomMatched = false;

                for (CheckInSymptom symptom : symptoms) {
                    if (symptom.getSymptomCode().equalsIgnoreCase(aSymptom.getSymptom().getCode()) &&
                            (symptom.getBodyPartCode().equalsIgnoreCase(aSymptom.getBodyPartCode())
                            || aSymptom.getBodyPartCode().equalsIgnoreCase("NON000"))) {
                        Operation operation = aSymptom.getOperation();
                        SeverityScaleValue value = severityScaleDAO.findSeverityScaleValueById(symptom.getSeverityScaleValueId());

                        switch (operation) {
                            case LESS_THAN:
                                if (value.getOrdinal() < aSymptom.getSeverityScaleValue().getOrdinal())
                                    symptomMatched = true;
                                break;
                            case LESS_THAN_EQUAL_TO:
                                if (value.getOrdinal() <= aSymptom.getSeverityScaleValue().getOrdinal())
                                    symptomMatched = true;
                                break;
                            case EQUAL_TO:
                                if (value.getOrdinal().equals(aSymptom.getSeverityScaleValue().getOrdinal()))
                                    symptomMatched = true;
                                break;
                            case GREATER_THAN_EQUAL_TO:
                                if (value.getOrdinal() >= aSymptom.getSeverityScaleValue().getOrdinal())
                                    symptomMatched = true;
                                break;
                            case GREATER_THAN:
                                if (value.getOrdinal() > aSymptom.getSeverityScaleValue().getOrdinal())
                                    symptomMatched = true;
                                break;
                            default:
                                //Oh god what have you done
                                break;
                        }

                        if (symptomMatched)
                            break;
                    }
                }

                if (!symptomMatched) {
                    ruleMatched = false;
                    break;
                }
            }

            if (ruleMatched)
                applicableRules.add(rule);
        }

        Priority priority = Priority.NORMAL;

        for (AssessmentRule rule : applicableRules)
            priority = priority.ordinal() >= rule.getPriority().ordinal() ? priority : rule.getPriority();

        addPatientToPriorityList(checkIn, priority, new Timestamp(System.currentTimeMillis()));
        return priority;
    }
}
