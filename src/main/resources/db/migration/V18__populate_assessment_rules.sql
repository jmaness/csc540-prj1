insert into addresses (num, street, city, state, country) values (123, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (1, 'Jamie', 'Gachie', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 11, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (5, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (5, 'SYM003', 'NON000', 1, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (5, 'SYM008', 'HED000', 6, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (234, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (2, 'Logan', 'Willett', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 12, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (6, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (6, 'SYM003', 'NON000', 1, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (6, 'SYM008', 'HED000', 7, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (345, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (3, 'Zaq', 'Balogun', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 13, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (7, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (7, 'SYM003', 'NON000', 2, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (7, 'SYM008', 'HED000', 9, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (456, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (4, 'Alex', 'Ngugi', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 14, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (8, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (8, 'SYM001', 'NON000', 10, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (8, 'SYM002', 'HED000', 2, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (8, 'SYM007', 'NON000', 3, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (8, 'SYM009', 'NON000', 10, 3, 1, 'Fell off bike');

insert into assessment_rules (priority, description) values ('Normal', 'Common Cold');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1, 'SYM003', 1, '<=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1, 'SYM008', 7, '<=');

insert into assessment_rules (priority, description) values ('High', 'Pneumonia');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (2, 'SYM003', 1, '>=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (2, 'SYM008', 7, '>=');

insert into assessment_rules (priority, description) values ('Quarantine', 'Zombie');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (3, 'SYM003', 2, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (3, 'SYM008', 9, '>=');

insert into assessment_rules (priority, description) values ('Quarantine', 'Ebola');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (4, 'SYM001', 10, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (4, 'SYM002', 2, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (4, 'SYM007', 3, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (4, 'SYM009', 6, '>=');

