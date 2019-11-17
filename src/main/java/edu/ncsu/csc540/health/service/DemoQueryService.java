package edu.ncsu.csc540.health.service;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.result.ResultSetScanner;
import org.jdbi.v3.core.statement.Query;

import javax.inject.Inject;
import java.time.LocalDate;

public class DemoQueryService {
    private Jdbi jdbi;

    @Inject
    public DemoQueryService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void findPatientsWithNegativeExperiences(ResultSetScanner<Void> scanner) {
        runQuery(scanner, "SELECT p.first_name, p.last_name, f.name facility_name, " +
                "pc.start_time, r.out_time, ne.code, ne.description " +
                "FROM patients p, " +
                "    facilities f, " +
                "    patient_checkins pc, " +
                "    outcome_reports r, " +
                "    negative_experiences ne " +
                "WHERE p.id = pc.patient_id " +
                "    AND pc.id = r.checkin_id " +
                "    AND r.checkin_id = ne.checkin_id " +
                "    AND p.facility_id = f.id " +
                "ORDER BY p.last_name, p.first_name, pc.start_time, ne.code");
    }

    public void findFacilitiesWithNoNegativeExperiences(ResultSetScanner<Void> scanner,
                                                        LocalDate startTime, LocalDate endTime) {
        runQuery(scanner, "SELECT id,\n" +
                "       name\n" +
                "FROM facilities MINUS\n" +
                "  (SELECT f.id,\n" +
                "          f.name\n" +
                "   FROM facilities f,\n" +
                "        patients p,\n" +
                "        patient_checkins c,\n" +
                "        negative_experiences n\n" +
                "   WHERE f.id = p.facility_id\n" +
                "     AND p.id = c.patient_id\n" +
                "     AND c.id = n.checkin_id\n" +
                "     AND c.start_time >= ? \n" +
                "     AND c.end_time <= ?)\n" +
                "ORDER BY name", startTime, endTime);
    }

    public void findFacilitiesMostReferred(ResultSetScanner<Void> scanner) {
        runQuery(scanner, "SELECT f1.name,\n" +
                "       f2.name\n" +
                "FROM facilities f1,\n" +
                "     facilities f2,\n" +
                "     (SELECT f.id,\n" +
                "             stats_mode(fr.id) ref_id\n" +
                "      FROM facilities f,\n" +
                "           patients p,\n" +
                "           patient_checkins c,\n" +
                "           referral_statuses rs,\n" +
                "           facilities fr\n" +
                "      WHERE f.id = p.facility_id\n" +
                "        AND p.id = c.patient_id\n" +
                "        AND c.id = rs.checkin_id\n" +
                "        AND rs.facility_id = fr.id\n" +
                "      GROUP BY f.id) x\n" +
                "WHERE x.id = f1.id\n" +
                "  AND x.ref_id = f2.id");
    }

    public void findFacilitiesWithNoNegativeCardiacExperiences(ResultSetScanner<Void> scanner) {
        runQuery(scanner, "SELECT id,\n" +
                "       name\n" +
                "FROM facilities MINUS\n" +
                "  (SELECT f.id,\n" +
                "          f.name\n" +
                "   FROM facilities f,\n" +
                "        patients p,\n" +
                "        patient_checkins c,\n" +
                "        checkin_symptoms cs,\n" +
                "        symptoms s,\n" +
                "        body_parts b,\n" +
                "        negative_experiences n\n" +
                "   WHERE f.id = p.facility_id\n" +
                "     AND p.id = c.patient_id\n" +
                "     AND c.id = cs.checkin_id\n" +
                "     AND cs.symptom_code = s.code\n" +
                "     AND s.body_part_code = b.code\n" +
                "     AND b.code like 'HRT%'\n" +
                "     AND c.id = n.checkin_id)\n" +
                "ORDER BY name");
    }

    public void findFacilitiesWithMostNegativeExperiences(ResultSetScanner<Void> scanner) {
        runQuery(scanner, "SELECT f2.id, f2.name\n" +
                "FROM facilities f2\n" +
                "WHERE\n" +
                "  (SELECT id FROM " +
                "    (SELECT id\n" +
                "     FROM\n" +
                "      (SELECT f.id id,\n" +
                "         count(*) AS num_experiences\n" +
                "       FROM facilities f,\n" +
                "          patients p,\n" +
                "          patient_checkins c,\n" +
                "          negative_experiences n\n" +
                "       WHERE f.id = p.facility_id\n" +
                "         AND p.id = c.patient_id\n" +
                "         AND c.id = n.checkin_id\n" +
                "       GROUP BY f.id)\n" +
                "  ORDER BY num_experiences DESC)\n" +
                "  WHERE ROWNUM <= 1) = f2.id");
    }

    public void findLongestCheckinPhases(ResultSetScanner<Void> scanner) {
        runQuery(scanner, "SELECT f_groups.name AS facility_name,\n" +
                "   f_groups.first_name,\n" +
                "   f_groups.last_name,\n" +
                "   TO_CHAR(cast(f_groups.start_time as date)) AS start_date,\n" +
                "   TO_CHAR(cast((COALESCE(f_groups.end_time, CURRENT_TIMESTAMP)\n" +
                "           - f_groups.start_time)\n" +
                "       as INTERVAL DAY(3) TO SECOND(0))) AS duration,\n" +
                "   f_groups.top_rows AS facility_rank,\n" +
                "   f_groups.sname AS symptom_name\n" +
                "FROM (\n" +
                "   SELECT g.name, g.id, g.first_name, g.last_name,\n" +
                "            g.start_time, g.end_time, sname,\n" +
                "       ROW_NUMBER() OVER (\n" +
                "       PARTITION BY g.name\n" +
                "       ORDER BY (g.end_time - g.start_time) DESC\n" +
                "       ) AS top_rows\n" +
                "   FROM ( SELECT f.name, p.id, p.first_name,\n" +
                "               p.last_name, c.start_time,\n" +
                "               COALESCE(c.end_time, CURRENT_TIMESTAMP) as end_time,\n" +
                "               s.name AS sname\n"+
                "          FROM facilities f, patients p, patient_checkins c,\n" +
                "               checkin_symptoms cs, symptoms s\n" +
                "          WHERE c.patient_id = p.id\n" +
                "            AND p.facility_id = f.id\n" +
                "            AND cs.symptom_code = s.code\n" +
                "            AND cs.checkin_id = c.id) g\n" +
                ") f_groups\n" +
                "WHERE f_groups.top_rows <= 5");
    }

    public void runQuery(ResultSetScanner<Void> scanner, String sql, Object... args) {
        jdbi.useHandle(handle -> {
            Query query = handle.select(sql, args);
            query.scanResultSet(scanner);
        });
    }
}
