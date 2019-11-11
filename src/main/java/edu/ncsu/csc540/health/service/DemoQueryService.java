package edu.ncsu.csc540.health.service;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultSetScanner;
import org.jdbi.v3.core.statement.Query;

import javax.inject.Inject;

public class DemoQueryService {
    private Jdbi jdbi;

    @Inject
    public DemoQueryService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void findAllFacilities(ResultSetScanner<Void> scanner) {
        jdbi.useHandle(handle -> {
            Query query = handle.createQuery("select id, name, capacity from facilities order by name");
            query.scanResultSet(scanner);
        });
    }
}
