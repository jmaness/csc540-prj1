insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Kate', 'Jones', TO_DATE('02/10/2013', 'MM/DD/YYYY'), 24044444444, 3, 2004);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Beth','Williams', TO_DATE('11/25/1980', 'MM/DD/YYYY'), 5557779999, 4, 2006);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jarry', 'Sob', TO_DATE('11/11/2019', 'MM/DD/YYYY'), 3019199919, 1, 2004);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Mark','Taker', TO_DATE('03/04/2007', 'MM/DD/YYYY'), 8475689245, 2, 2005);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jarry', 'Son', TO_DATE('11/11/2019', 'MM/DD/YYYY'), 3019199919, 1, 2004);

insert into patient_checkins (patient_id, start_time, end_time) values (2009, TO_TIMESTAMP('12/12/2011 11:30:04', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/12/2011 16:30:29', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (patient_id, start_time, end_time) values (2010, TO_TIMESTAMP('5/16/2014 08:04:43', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('5/16/2014 9:20:14', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (patient_id, start_time, end_time) values (2011, TO_TIMESTAMP('3/16/2016 03:16:16', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('3/24/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (patient_id, start_time, end_time) values (2012, TO_TIMESTAMP('12/25/2018 10:10:10', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/25/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkins (patient_id, start_time, end_time) values (2013, TO_TIMESTAMP('3/16/2016 03:16:15', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('3/24/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'));

insert into symptoms (name, severity_scale_id, body_part_code) values ('Arrhythmia', 1, 'HRT000');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1009, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1010, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1011, 'SYM113', 'HRT000', 6, 1, 0, 'Unknown');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1013, 'SYM113', 'HRT000', 14, 1, 0, 'Unknown');

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1009, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/12/2011 16:31:29', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1010, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('5/16/2014 9:21:14', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1011, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('3/25/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1012, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/26/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1013, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('3/27/2016 12:00:00', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');

insert into negative_experiences (checkin_id, code, description)
values (1010, 'ACQUIRED_INFECTION', 'the facility is dirty');
insert into negative_experiences (checkin_id, code, description)
values (1011, 'MISDIAGNOSIS', 'I do not have cancer');
insert into negative_experiences (checkin_id, code, description)
values (1013, 'MISDIAGNOSIS', 'I do not have the flu');

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1001, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/12/2011 16:31:29', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1002, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('5/16/2014 9:21:14', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1003, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('3/25/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, out_time, treatment)
values (1004, 'TREATED_SUCCESSFULLY', 1,'because acknowledged',TO_TIMESTAMP('12/26/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');

insert into referral_statuses(checkin_id, facility_id, staff_id) values (1001, 2005, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1002, 2005, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1003, 2001, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1004, 2001, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1009, 2001, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1011, 2001, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1012, 2004, 89001);
insert into referral_statuses(checkin_id, facility_id, staff_id) values (1013, 2001, 89001);