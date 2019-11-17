package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
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

    /**
     * Returns all symptoms
     * @return A List containing all symptoms
     */
    public List<Symptom> findAllSymptoms() {
        return symptomDAO.findAll();
    }

    /**
     * Returns all body parts
     * @return A List containing all body parts
     */
    public List<BodyPart> findAllBodyParts() {
        return bodyPartDAO.findAllBodyParts();
    }

    /**
     * Returns all severity scales
     * @return A List containing all severity scales
     */
    public List<SeverityScale> findAllSeverityScales() {
        return severityScaleDAO.findAllSeverityScales();
    }

    /**
     * Returns all severity scale values associated to a given severity scale
     * @param severityScaleId The ID of the desired severity scale
     * @return A List containing all matching severity scale values
     */
    public List<SeverityScaleValue> findSeverityScaleValues(Integer severityScaleId) {
        return severityScaleDAO.findSeverityScaleValues(severityScaleId);
    }

    /**
     * Adds a symptom to the system
     * @param symptom The symptom being added
     * @return An object representing the information associated with the added symptom
     */
    @Transactional
    public Symptom createSymptom(Symptom symptom) {
        String symptomCode = symptomDAO.create(symptom);
        return symptomDAO.findByCode(symptomCode);
    }
}
