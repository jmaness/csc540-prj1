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
import java.util.Optional;

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
    public Optional<OutcomeReport> findOutcomeReport(Integer checkInId) {
        return Optional.ofNullable(outcomeReportDAO.findOutcomeReportById(checkInId))
                .map(outcomeReport -> {
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
                });
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

    public Integer findPriorityListCheckInId(Integer patientId) {
        return patientDAO.findPriorityListCheckInId(patientId);
    }

    public void addPatientToPriorityList(PatientCheckIn checkIn, Priority priority, Timestamp timestamp) {
        patientDAO.addPatientToPriorityList(checkIn.getId(), priority, timestamp);
    }

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
