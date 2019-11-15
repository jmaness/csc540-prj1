package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.Staff;

public interface ActionFactory {
    PatientRoutingPage getPatientRoutingPage(Patient patient);
    PatientCheckInPage getPatientCheckinPage(Patient patient);
    PatientCheckoutAcknowledgementPage getPatientCheckoutAcknowledgementPage(Patient patient, Action previousPage);
    StaffMenuPage getStaffMenuPage(Staff staff);
    StaffPatientReportPage getStaffPatientReportPage(Staff staff, Patient patient, StaffMenuPage staffMenuPage);
}
