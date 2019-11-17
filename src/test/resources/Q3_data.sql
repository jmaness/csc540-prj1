
-- Wolf Hospital patients
insert into addresses (id, num, street, city, state, zip, country) values (300, '95', 'Merry', 'New Bedford', 'Massachusetts', '02745', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (301, '5201', 'Bellgrove', 'Boston', 'Massachusetts', '02114', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (302, '10', 'Menomonie', 'Philadelphia', 'Pennsylvania', '19151', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (303, '66013', 'Armistice', 'Denver', 'Colorado', '80228', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (304, '0515', 'Schiller', 'Trenton', 'New Jersey', '08695', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (305, '578', 'Kenwood', 'Frankfort', 'Kentucky', '40618', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (306, '4', 'Anderson', 'Plano', 'Texas', '75074', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (307, '86', 'Nancy', 'Topeka', 'Kansas', '66611', 'US');

insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (300, 'Madison', 'Cuckoo', TO_DATE('02/26/1981', 'MM/DD/YYYY'), '4997081124', 300, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (301, 'Nester', 'Stryde', TO_DATE('09/15/1979', 'MM/DD/YYYY'), '6134616407', 301, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (302, 'Anna-maria', 'Mitchley', TO_DATE('01/05/1985', 'MM/DD/YYYY'), '5223894744', 302, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (303, 'Lou', 'Anthony', TO_DATE('06/28/1995', 'MM/DD/YYYY'), '5626867537', 303, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (304, 'Karlotta', 'Finlaison', TO_DATE('07/21/1980', 'MM/DD/YYYY'), '9936090220', 304, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (305, 'Bartholemy', 'Buttery', TO_DATE('07/10/1979', 'MM/DD/YYYY'), '2762574496', 305, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (306, 'Adelaide', 'Zarfai', TO_DATE('05/07/1978', 'MM/DD/YYYY'), '7791092980', 306, 1000);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (307, 'Lida', 'Corwood', TO_DATE('08/08/1981', 'MM/DD/YYYY'), '3722214648', 307, 1000);

insert into patient_checkins (id, patient_id, start_time, end_time) values (300, 300, TO_TIMESTAMP('2019-11-15 06:38:33', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 07:11:16', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (301, 301, TO_TIMESTAMP('2019-11-15 19:39:40', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 19:43:17', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (302, 302, TO_TIMESTAMP('2019-11-15 03:35:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 13:08:09', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (303, 303, TO_TIMESTAMP('2019-11-15 21:00:41', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 04:35:18', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (304, 304, TO_TIMESTAMP('2019-11-15 13:17:24', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 23:23:10', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (305, 305, TO_TIMESTAMP('2019-11-15 02:33:30', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 14:45:38', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (306, 306, TO_TIMESTAMP('2019-11-15 19:13:22', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 13:44:09', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (307, 307, TO_TIMESTAMP('2019-11-15 11:13:25', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 00:36:37', 'YYYY-MM-DD HH24:MI:SS'));

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (300, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (301, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (302, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (303, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (304, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (305, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (306, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (307, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');

insert into referral_statuses (checkin_id, facility_id, staff_id) values (300, 1001, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (301, 1001, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (302, 1001, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (303, 1001, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (304, 1001, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (305, 1002, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (306, 1002, 67001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (307, 1002, 67001);


-- California patients
insert into addresses (id, num, street, city, state, zip, country) values (308, '19', 'Toban', 'Norfolk', 'Virginia', '23504', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (309, '872', 'Columbus', 'Southfield', 'Michigan', '48076', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (310, '50', 'Sherman', 'Tulsa', 'Oklahoma', '74133', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (311, '1590', 'Express', 'Charlotte', 'North Carolina', '28272', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (312, '3', 'Pond', 'Columbus', 'Ohio', '43268', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (313, '0841', 'East', 'Miami', 'Florida', '33180', 'US');

insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (308, 'Courtenay', 'Gonnel', TO_DATE('06/30/1982', 'MM/DD/YYYY'), '2867014946', 308, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (309, 'Worthy', 'Heball', TO_DATE('04/19/1995', 'MM/DD/YYYY'), '6999662954', 309, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (310, 'Burch', 'Mibourne', TO_DATE('03/06/1984', 'MM/DD/YYYY'), '4616450827', 310, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (311, 'Fifi', 'Tother', TO_DATE('12/27/1993', 'MM/DD/YYYY'), '8985897571', 311, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (312, 'Giles', 'Davley', TO_DATE('11/13/1983', 'MM/DD/YYYY'), '2856863647', 312, 1001);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (313, 'Bartlett', 'Oglevie', TO_DATE('11/16/1987', 'MM/DD/YYYY'), '4964798696', 313, 1001);

insert into patient_checkins (id, patient_id, start_time, end_time) values (308, 308, TO_TIMESTAMP('2019-11-15 06:49:41', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 05:31:23', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (309, 309, TO_TIMESTAMP('2019-11-15 16:25:09', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 21:01:40', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (310, 310, TO_TIMESTAMP('2019-11-15 18:58:40', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 11:57:35', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (311, 311, TO_TIMESTAMP('2019-11-15 05:51:49', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 13:00:15', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (312, 312, TO_TIMESTAMP('2019-11-15 05:56:58', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 13:32:49', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (313, 313, TO_TIMESTAMP('2019-11-15 11:40:27', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 19:56:15', 'YYYY-MM-DD HH24:MI:SS'));

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (308, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (309, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (310, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (311, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (312, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (313, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');

insert into referral_statuses (checkin_id, facility_id, staff_id) values (308, 1000, 91001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (309, 1000, 91001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (310, 1000, 91001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (311, 1000, 91001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (312, 1000, 91001);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (313, 1002, 91001);



-- Suny patients
insert into addresses (id, num, street, city, state, zip, country) values (314, '3828', 'Maple', 'San Antonio', 'Texas', '78265', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (315, '50', 'Dawn', 'South Bend', 'Indiana', '46699', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (316, '4418', 'Talisman', 'San Diego', 'California', '92170', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (317, '02', 'Forest Run', 'Lubbock', 'Texas', '79405', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (318, '08431', 'Weeping Birch', 'Arlington', 'Virginia', '22225', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (319, '498', 'Sugar', 'Los Angeles', 'California', '90094', 'US');

insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (314, 'Gare', 'Piggford', TO_DATE('10/11/1991', 'MM/DD/YYYY'), '6546189310', 314, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (315, 'Rosamond', 'Mayler', TO_DATE('11/12/1977', 'MM/DD/YYYY'), '5794486872', 315, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (316, 'Adolph', 'Verma', TO_DATE('01/05/1995', 'MM/DD/YYYY'), '1286457077', 316, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (317, 'Simonne', 'Lindblom', TO_DATE('10/27/1981', 'MM/DD/YYYY'), '1557632969', 317, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (318, 'Marge', 'Lunt', TO_DATE('02/19/1995', 'MM/DD/YYYY'), '3019421135', 318, 1002);
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (319, 'Bernadina', 'Hicks', TO_DATE('03/04/1988', 'MM/DD/YYYY'), '7989881759', 319, 1002);

insert into patient_checkins (id, patient_id, start_time, end_time) values (314, 314, TO_TIMESTAMP('2019-11-15 01:20:53', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 13:12:03', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (315, 315, TO_TIMESTAMP('2019-11-15 04:24:35', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 07:22:34', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (316, 316, TO_TIMESTAMP('2019-11-15 22:41:26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 17:14:14', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (317, 317, TO_TIMESTAMP('2019-11-15 19:22:55', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 15:03:57', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (318, 318, TO_TIMESTAMP('2019-11-15 22:39:31', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 14:53:37', 'YYYY-MM-DD HH24:MI:SS'));
insert into patient_checkins (id, patient_id, start_time, end_time) values (319, 319, TO_TIMESTAMP('2019-11-15 18:38:04', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2019-11-16 06:02:59', 'YYYY-MM-DD HH24:MI:SS'));

insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (314, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (315, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (316, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (317, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (318, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');
insert into outcome_reports (checkin_id, discharge_status, patient_acknowledged, out_time, treatment) values (319, 'REFERRED', 1, TO_TIMESTAMP('2019-11-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Got a test');

insert into referral_statuses (checkin_id, facility_id, staff_id) values (314, 1000, 67002);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (315, 1000, 67002);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (316, 1000, 67002);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (317, 1000, 67002);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (318, 1000, 67002);
insert into referral_statuses (checkin_id, facility_id, staff_id) values (319, 1001, 67002);
