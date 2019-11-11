insert into patient_checkin (patient_id, start_time, end_time) values (1, TO_TIMESTAMP('09/14/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('09/15/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccuring, incident) values (1, 'SYM003', 'NON000', 14, 1, 0, 'Unknown');

insert into patient_checkin (patient_id, start_time, end_time) values (2, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/24/2010 19:12:09', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccuring, incident) values (2, 'SYM001', 'ARM000', 5, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccuring, incident) values (2, 'SYM007', 'ARM000', 19, 3, 1, 'Fell off bike');

insert into patient_checkin (patient_id, start_time, end_time) values (3, TO_TIMESTAMP('05/01/2011 10:30:05', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('05/01/2011 13:02:57', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccuring, incident) values (3, 'SYM002', 'NON000', 12, 1, 0, 'Pepper challenge');

insert into patient_checkin (patient_id, start_time, end_time) values (4, TO_TIMESTAMP('11/18/2019 15:00:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('11/18/2019 16:15:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccuring, incident) values (4, 'SYM006', 'EYE000', 11, 1, 0, 'Unknown');