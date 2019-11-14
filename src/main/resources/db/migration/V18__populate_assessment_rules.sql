insert into addresses (num, street, city, state, country) values (123, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jamie', 'Gachie', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 5, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (2005, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1005, 'SYM103', 'NON000', 13, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1005, 'SYM108', 'HED000', 7, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (234, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Logan', 'Willett', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 5, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (2006, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1006, 'SYM103', 'NON000', 13, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1006, 'SYM108', 'HED000', 7, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (345, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Zaq', 'Balogun', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 5, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (2007, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1007, 'SYM103', 'NON000', 14, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1007, 'SYM108', 'HED000', 9, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (456, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Alex', 'Ngugi', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 5, 1000);

insert into patient_checkins (patient_id, start_time, end_time) values (2008, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM101', 'NON000', 10, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM102', 'HED000', 12, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM107', 'NON000', 19, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM109', 'NON000', 10, 3, 1, 'Fell off bike');

insert into assessment_rules (priority, description) values ('Normal', 'Common Cold');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1001, 'SYM103', 13, '<=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1001, 'SYM108', 7, '<=');

insert into assessment_rules (priority, description) values ('High', 'Pneumonia');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1002, 'SYM103', 13, '>=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1002, 'SYM108', 7, '>=');

insert into assessment_rules (priority, description) values ('Quarantine', 'Zombie');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1003, 'SYM103', 14, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1003, 'SYM108', 9, '>=');

insert into assessment_rules (priority, description) values ('Quarantine', 'Ebola');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM101', 10, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM102', 12, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM107', 19, '=');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM109', 6, '>=');

