package edu.ncsu.csc540.health.dao;

import edu.ncsu.csc540.health.model.BodyPart;
import edu.ncsu.csc540.health.model.CheckInSymptom;
import edu.ncsu.csc540.health.model.Equipment;
import edu.ncsu.csc540.health.model.PatientCheckIn;
import edu.ncsu.csc540.health.model.Service;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ServiceDAO {

    @SqlQuery("select s.code s_code, s.name s_name, " +
            "    se.name se_name, sb.body_part_code sb_body_part_code " +
            "from services s, department_services ds, departments d " +
            "left outer join service_equipment se on s.code = se.service_code " +
            "left outer join service_body_parts sb on s.code = sb.service_code " +
            "where s.code = se.service_code " +
            "    and s.code = sb.service_code " +
            "    and s.code = ds.code " +
            "    and ds.department_code = d.code" +
            "    and d.facility_id = :facilityId")
    @RegisterConstructorMapper(value = Service.class, prefix = "s")
    @RegisterConstructorMapper(value = Equipment.class, prefix = "se")
    @RegisterConstructorMapper(value = BodyPart.class, prefix = "sb")
    @UseRowReducer(ServiceRowReducer.class)
    List<Service> findAllServicesByFacilityId(@Bind("facilityId") Integer facilityId);


    class ServiceRowReducer implements LinkedHashMapRowReducer<String, Service> {

        @Override
        public void accumulate(Map<String, Service> container, RowView rowView) {
            final Service service = container.computeIfAbsent(rowView.getColumn("s_code", String.class),
                    id -> rowView.getRow(Service.class));

            if (rowView.getColumn("se_name", String.class) != null) {
                Set<Equipment> allEquipment = Optional.ofNullable(service.getEquipment())
                        .orElse(new HashSet<>());

                allEquipment.add(rowView.getRow(Equipment.class));

                container.put(service.getCode(), new Service(
                        service.getCode(),
                        service.getName(),
                        allEquipment,
                        service.getBodyPartCodes()
                ));
            }

            String bodyPartCode = rowView.getColumn("sb_body_part_code", String.class);
            if (bodyPartCode != null) {
                Set<String> allBodyPartCodes = Optional.ofNullable(service.getBodyPartCodes())
                        .orElse(new HashSet<>());

                allBodyPartCodes.add(bodyPartCode);

                container.put(service.getCode(), new Service(
                        service.getCode(),
                        service.getName(),
                        service.getEquipment(),
                        allBodyPartCodes
                ));
            }
        }
    }
}
