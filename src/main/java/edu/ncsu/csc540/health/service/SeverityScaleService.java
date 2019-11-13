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

    @Transactional
    public int addSeverityScale(SeverityScale scale) {
        return severityScaleDAO.createScale(scale);
    }

    @Transactional
    public void addSeverityScaleValue(SeverityScaleValue scaleValue) {
        severityScaleDAO.createScaleValue(scaleValue);
    }
}
