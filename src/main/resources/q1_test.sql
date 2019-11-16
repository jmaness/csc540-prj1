insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Kate', 'Jones', TO_DATE('02/10/2013', 'MM/DD/YYYY'), 24044444444, 3, 1000);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Beth','Williams', TO_DATE('11/25/1980', 'MM/DD/YYYY'), 5557779999, 4, 1002);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jarry', 'Sob', TO_DATE('11/11/2019', 'MM/DD/YYYY'), 3019199919, 1, 1000);
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Mark','Taker', TO_DATE('03/04/2007', 'MM/DD/YYYY'), 8475689245, 2, 1001);

insert into patient_checkin (patient_id, start_time, end_time) values (25, TO_TIMESTAMP('12/12/2011 11:30:04', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/12/2011 16:30:29', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkin (patient_id, start_time, end_time) values (26, TO_TIMESTAMP('5/16/2014 08:04:43', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('5/16/2014 9:20:14', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkin (patient_id, start_time, end_time) values (27, TO_TIMESTAMP('3/16/2016 03:16:16', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('3/24/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'));
insert into patient_checkin (patient_id, start_time, end_time) values (28, TO_TIMESTAMP('12/25/2018 10:10:10', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/25/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'));

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, time_out, treatment) 
values (5, 'TREATED SUCCESSFULLY', 'Yes','because acknowledged',TO_TIMESTAMP('12/12/2011 16:31:29', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, time_out, treatment)
values (6, 'TREATED SUCCESSFULLY', 'Yes','because acknowledged',TO_TIMESTAMP('5/16/2014 9:21:14', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, time_out, treatment) 
values (7, 'TREATED SUCCESSFULLY', 'Yes','because acknowledged',TO_TIMESTAMP('3/25/2016 11:59:59', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, patient_acknowledge_reason, time_out, treatment) 
values (8, 'TREATED SUCCESSFULLY', 'Yes','because acknowledged',TO_TIMESTAMP('12/26/2018 22:22:22', 'MM/DD/YYYY HH24:MI:SS'), 'Got a test');

insert into negative_experiences (checkin_id, code, description) 
values (6, 'ACQUIRED_INFECTION', 'the facility is dirty');
insert into negative_experiences (checkin_id, code, description) 
values (7, 'MISDIAGNOSIS', 'I do not have cancer');
