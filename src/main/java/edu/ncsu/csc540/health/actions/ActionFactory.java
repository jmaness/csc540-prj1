package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Patient;
import edu.ncsu.csc540.health.model.Staff;

public interface ActionFactory {
    PatientRoutingPage getPatientRoutingPage(Patient patient);
    StaffMenuPage getStaffMenuPage(Staff staff);
}
