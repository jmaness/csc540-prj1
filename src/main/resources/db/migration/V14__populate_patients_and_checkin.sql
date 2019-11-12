insert into departments_body_parts(body_part_code, department_code) values ('1', 'head');
insert into addresses (num, street, city, state, country) values (501, 'Sicky Sick Ave', 'Raleigh', 'NC', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jim', 'Jam', DATE '1997-11-06', '1234567890', 2, 2003);
insert into patient_checkins (patient_id, start_time, end_time) values (1, current_timestamp, null);
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1, 'SYM02', '1', 7, 5, 0, 'Kicked in the head by a rambunctious but well-meaning donkey');
insert into priority_lists (checkin_id, priority, start_time, end_time) values (1, 'high', current_timestamp, null);