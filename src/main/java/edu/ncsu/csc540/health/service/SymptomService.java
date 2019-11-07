package edu.ncsu.csc540.health.service;

import edu.ncsu.csc540.health.dao.BodyPartDAO;
import edu.ncsu.csc540.health.dao.SeverityScaleDAO;
import edu.ncsu.csc540.health.dao.SymptomDAO;
import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import edu.ncsu.csc540.health.model.Symptom;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Singleton
public class SymptomService {
    private final SymptomDAO symptomDAO;
    private final BodyPartDAO bodyPartDAO;
    private final SeverityScaleDAO severityScaleDAO;

    @Inject
    public SymptomService(Jdbi jdbi) {
        symptomDAO = jdbi.onDemand(SymptomDAO.class);
        bodyPartDAO = jdbi.onDemand(BodyPartDAO.class);
        severityScaleDAO = jdbi.onDemand(SeverityScaleDAO.class);
    }

    public List<Symptom> findAllSymptoms() {
        return symptomDAO.findAll();
    }

    public List<BodyPart> findAllBodyParts() {
        return bodyPartDAO.findAllBodyParts();
    }

    public List<SeverityScale> findAllSeverityScales() {
        return severityScaleDAO.findAllSeverityScales();
    }

    public List<SeverityScaleValue> findSeverityScaleValues(Integer severityScaleId) {
        return severityScaleDAO.findSeverityScaleValues(severityScaleId);
    }

    public Symptom createSymptom(Symptom symptom) {
        String symptomCode = symptomDAO.create(symptom);
        //return symptomDAO.findByCode(symptomCode);
        Symptom symptom_fetch = symptomDAO.findByCode(symptomCode);
        System.out.println(symptom_fetch.getName());
        return null;
    }
}
