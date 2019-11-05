package edu.ncsu.csc540.health.actions;

import edu.ncsu.csc540.health.model.Patient;

public interface ActionFactory {
    PatientRoutingPage getPatientRoutingPage(Patient patient);
}
