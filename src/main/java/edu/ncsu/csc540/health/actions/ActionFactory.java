package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.Staff;

public interface ActionFactory {
    PatientRoutingPage getPatientRoutingPage(Patient patient);
    PatientCheckInPage getPatientCheckinPage(Patient patient);
    StaffMenuPage getStaffMenuPage(Staff staff);
    StaffPatientReportPage getStaffPatientReportPage(Staff staff, PatientCheckIn patientCheckIn, StaffMenuPage staffMenuPage);
}
