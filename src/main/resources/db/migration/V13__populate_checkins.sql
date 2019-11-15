insert into symptoms (name, severity_scale_id, body_part_code) values ('Pain', 1, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Diarrhea', 2, 'ABD000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Fever', 3, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Physical Exam', 4, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Lightheadedness', 2, 'HED000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Blurred vision', 2, 'EYE000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Bleeding', 5, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Headache', 1, 'HED000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Tenderness (to the touch)', 1, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Tightness', 1, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Numbness', 1, 'NON000');
insert into symptoms (name, severity_scale_id, body_part_code) values ('Shortness of breath', 1, 'CST000');

insert into patient_checkins (patient_id, start_time, end_time) values (2001, TO_TIMESTAMP('09/14/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('09/15/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1001, 'SYM103', 'NON000', 14, 1, 0, 'Unknown');

insert into patient_checkins (patient_id, start_time, end_time) values (2002, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/24/2010 19:12:09', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1002, 'SYM101', 'ARM000', 5, 3, 1, 'Fell off bike');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1002, 'SYM107', 'ARM000', 19, 3, 1, 'Fell off bike');

insert into patient_checkins (patient_id, start_time, end_time) values (2003, TO_TIMESTAMP('05/01/2011 10:30:05', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('05/01/2011 13:02:57', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1003, 'SYM102', 'NON000', 12, 1, 0, 'Pepper challenge');

insert into patient_checkins (patient_id, start_time, end_time) values (2004, TO_TIMESTAMP('11/18/2019 15:00:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('11/18/2019 16:15:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1004, 'SYM106', 'EYE000', 11, 1, 0, 'Unknown');
