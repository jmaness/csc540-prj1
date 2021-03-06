insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (101, 'Kate', 'Jones', TO_DATE('02/10/2013', 'MM/DD/YYYY'), '24044444444', 3, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (102, 'Beth','Williams', TO_DATE('11/25/1980', 'MM/DD/YYYY'), '5557779999', 4, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (103, 'Jarry', 'Sob', TO_DATE('11/11/2019', 'MM/DD/YYYY'), '3019199919', 1, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (104, 'Mark','Taker', TO_DATE('03/04/2007', 'MM/DD/YYYY'), '8475689245', 2, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (105, 'Jarry', 'Son', TO_DATE('11/11/2019', 'MM/DD/YYYY'), '3019199919', 1, 1000);

insert into patient_checkins (id, patient_id, start_time, end_time) values (101, 101, TO_TIMESTAMP('12/12/2011 11:30:04', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/12/2011 16:30:29', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (102, 102, TO_TIMESTAMP('5/16/2014 08:04:43', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('5/16/2014 9:20:14', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (103, 103, TO_TIMESTAMP('3/16/2016 03:16:16', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('3/24/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (104, 104, TO_TIMESTAMP('12/25/2018 10:10:10', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/25/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (105, 105, TO_TIMESTAMP('3/16/2016 03:16:15', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('3/24/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'));

insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM113', 'Arrhythmia', 1, 'HRT000');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (101, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (102, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (103, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (104, 'SYM113', 'HRT000', 14, 1, 0, 'Unknown');

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (101, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/12/2011 16:31:29', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (102, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('5/16/2014 9:21:14', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (103, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('3/25/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (104, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/26/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (105, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('3/27/2016 12:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');

insert into negative_experiences (checkin_id, code, description) values (102, 'ACQUIRED_INFECTION', 'the facility is dirty');
insert into negative_experiences (checkin_id, code, description) values (102, 'MISDIAGNOSIS', 'I do not have asthma');
insert into negative_experiences (checkin_id, code, description) values (103, 'MISDIAGNOSIS', 'I do not have cancer');
insert into negative_experiences (checkin_id, code, description) values (105, 'MISDIAGNOSIS', 'I do not have the flu');
