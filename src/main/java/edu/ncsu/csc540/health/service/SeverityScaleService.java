package edu.ncsu.csc540.health.service;

import com.google.inject.persist.Transactional;
import edu.ncsu.csc540.health.dao.*;
import edu.ncsu.csc540.health.model.SeverityScale;
import edu.ncsu.csc540.health.model.SeverityScaleValue;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Inject;

public class SeverityScaleService {
    private final SeverityScaleDAO severityScaleDAO;

    @Inject
    public SeverityScaleService(Jdbi jdbi) {
        severityScaleDAO = jdbi.onDemand(SeverityScaleDAO.class);
    }

    /**
     * Adds a new severity scale to the system
     * @param scale The severity scale being added
     * @return The generated ID of the added severity scale
     */
    @Transactional
    public int addSeverityScale(SeverityScale scale) {
        return severityScaleDAO.createScale(scale);
    }

    /**
     * Adds a new severity scale value to the system
     * @param scaleValue The severity scale value being added
     */
    @Transactional
    public void addSeverityScaleValue(SeverityScaleValue scaleValue) {
        severityScaleDAO.createScaleValue(scaleValue);
    }
}
