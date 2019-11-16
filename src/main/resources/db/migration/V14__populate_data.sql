insert into classifications (code, name) values ('01', 'Primary');
insert into classifications (code, name) values ('02', 'Secondary');
insert into classifications (code, name) values ('03', 'Tertiary');

insert into certifications (acronym, name) values ('CER001', 'Comprehensive Stroke Certification');
insert into certifications (acronym, name) values ('CER002', 'ISO Certification');
insert into certifications (acronym, name) values ('CER003', 'Primary Stroke Certification');

insert into services (code, name) values ('SER01', 'Emergency');
insert into services (code, name) values ('SGP01', 'General practice');
insert into services (code, name) values ('VIS01', 'Vision');
insert into services (code, name) values ('OTS01', 'Other');

insert into addresses (id, num, street, city, state, country) values (1, 2650, 'Wolf Village Way Box 7220', 'Raleigh', 'NC', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('Wolf Hospital', 300, '03', 1);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (2001, 'CER001', TO_DATE('11/12/1990', 'MM/DD/YYYY'), TO_DATE('11/11/2025', 'MM/DD/YYYY'));

insert into addresses (id, num, street, city, state, country) values (2, 2650, '2500 Sacramento', 'Santa Cruz', 'CA', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('California Health Care', 150, '02', 2);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (2002, 'CER002', TO_DATE('05/09/2011', 'MM/DD/YYYY'), TO_DATE('02/08/2024', 'MM/DD/YYYY'));

insert into addresses (id, num, street, city, state, country) values (3, 489, 'First Avenue', 'New York', 'NY', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('Suny Medical Center', 10, '01', 3);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (2003, 'CER002', TO_DATE('05/09/2011', 'MM/DD/YYYY'), TO_DATE('02/08/2024', 'MM/DD/YYYY'));
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (2003, 'CER003', TO_DATE('01/01/2018', 'MM/DD/YYYY'), TO_DATE('12/31/2028', 'MM/DD/YYYY'));

insert into body_parts (code, name) values ('ARM000', 'Left Arm');
insert into body_parts (code, name) values ('ARM001', 'Right Arm');
insert into body_parts (code, name) values ('ABD000', 'Abdominal');
insert into body_parts (code, name) values ('EYE000', 'Eye');
insert into body_parts (code, name) values ('HRT000', 'Heart');
insert into body_parts (code, name) values ('CST000', 'Chest');
insert into body_parts (code, name) values ('HED000', 'Head');
insert into body_parts (code, name) values ('NON000', 'None');

insert into departments (code, name, type, facility_id) values ('GP000', 'General Practice department', 'Medical', 2001);
insert into departments (code, name, type, facility_id) values ('OP000', 'Optometry', 'Medical', 2001);
insert into departments (code, name, type, facility_id) values ('SE000', 'Security', 'Non-medical', 2001);
insert into departments (code, name, type, facility_id) values ('ER000', 'Emergency room', 'Medical', 2002);
insert into departments (code, name, type, facility_id) values ('GP001', 'General Practice department', 'Medical', 2002);
insert into departments (code, name, type, facility_id) values ('ER001', 'Emergency room', 'Medical', 2003);

insert into departments_body_parts (department_code, body_part_code) values ('OP000', 'EYE000');
/*
insert into departments_body_parts (department_code, body_part_code) values ('GP001', 'HED000');
insert into departments_body_parts (department_code, body_part_code) values ('OP000', 'NON000');
*/
insert into departments_body_parts (department_code, body_part_code) values ('GP000', 'NON000');
insert into departments_body_parts (department_code, body_part_code) values ('SE000', 'NON000');
insert into departments_body_parts (department_code, body_part_code) values ('ER000', 'NON000');
insert into departments_body_parts (department_code, body_part_code) values ('GP001', 'NON000');
insert into departments_body_parts (department_code, body_part_code) values ('ER001', 'NON000');

insert into department_services (department_code, service_code) values ('ER000', 'SER01');
insert into department_services (department_code, service_code) values ('ER000', 'OTS01');
insert into department_services (department_code, service_code) values ('GP000', 'SGP01');
insert into department_services (department_code, service_code) values ('GP000', 'OTS01');
insert into department_services (department_code, service_code) values ('GP001', 'SGP01');
insert into department_services (department_code, service_code) values ('GP001', 'OTS01');
insert into department_services (department_code, service_code) values ('OP000', 'VIS01');
insert into department_services (department_code, service_code) values ('OP000', 'OTS01');

insert into service_equipment (service_code, name) values ('SER01', 'ER combo rack');
insert into service_equipment (service_code, name) values ('SGP01', 'Blood pressure monitor');
insert into service_equipment (service_code, name) values ('SGP01', 'Thermometer');
insert into service_equipment (service_code, name) values ('VIS01', 'Vision Screener');

/* Old hospitals. Still using their addresses.
insert into addresses (id, num, street, city, state, country) values (4, 100, 'UNC Street', 'Chapel Hill', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('UNC Hospital', 500, '01', 4);
insert into addresses (id, num, street, city, state, country) values (5, 200, 'Duke Street', 'Durham', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('Duke Hospital', 500, '01', 5);
insert into addresses (id, num, street, city, state, country) values (6, 300, 'NCSU Street', 'Raleigh', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('NCSU Hospital', 500, '01', 6);
*/

insert into addresses (id, num, street, city, state, zip, country) values (4, 83, 'Vernon St', 'Scotch Plains', 'NJ', '07076', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (5, 69, 'Holly Drive', 'Blacksburg', 'VA', '24060', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (6, 7540, 'Plymouth Court', 'Derry', 'NH', '03038', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (7, 8196, 'Big Rock Cove Road', 'Lutherville Timonium', 'MD', '21093', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (8, 697, 'Lawrence Ave.', 'Teaneck', 'NJ', '07666', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (9, 685, 'South Chapel Lane', 'Branford', 'CT', '06405', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (10, 7056, 'W. Piper Dr.', 'Macon', 'GA', '31204', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (11, 40, 'N. Peachtree Drive', 'Sunnyside', 'NY', '11104', 'US');
insert into addresses (id, num, street, city, state, zip, country) values (12, 22, 'Sutor St.', 'Laurel', 'MD', '20707', 'US');

insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (89001, 'Medical', 'Robot', 'Medical', TO_DATE('04/19/2019', 'MM/DD/YYYY'), TO_DATE('06/21/2019', 'MM/DD/YYYY'), 4, 2001, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93001, 'Musical', 'Robert', 'Medical', TO_DATE('01/29/1983', 'MM/DD/YYYY'), TO_DATE('08/29/2018', 'MM/DD/YYYY'), 5, 2002, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67001, 'Muscular', 'Rob', 'Medical', TO_DATE('12/09/1967', 'MM/DD/YYYY'), TO_DATE('10/12/1983', 'MM/DD/YYYY'), 6, 2001, 'GP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (88001, 'Mechanical', 'Roboto', 'Medical', TO_DATE('05/18/1988', 'MM/DD/YYYY'), TO_DATE('06/21/2019', 'MM/DD/YYYY'), 7, 2001, 'GP000');
insert into staff_secondary_departments (staff_id, department_code) values (88001, 'OP000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (91001, 'Millenium', 'Roberten', 'Medical', TO_DATE('06/28/1991', 'MM/DD/YYYY'), TO_DATE('09/20/2018', 'MM/DD/YYYY'), 8, 2002, 'GP001');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (66001, 'Missionary', 'Robinson', 'Medical', TO_DATE('07/08/1966', 'MM/DD/YYYY'), TO_DATE('10/01/1993', 'MM/DD/YYYY'), 9, 2002, 'ER000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (89002, 'Miscellaneous', 'Robotor', 'Non-medical', TO_DATE('04/19/1989', 'MM/DD/YYYY'), TO_DATE('08/19/2014', 'MM/DD/YYYY'), 10, 2001, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (93002, 'Musician', 'Robot', 'Non-medical', TO_DATE('01/29/1993', 'MM/DD/YYYY'), TO_DATE('10/18/2017', 'MM/DD/YYYY'), 11, 2001, 'SE000');

insert into staff (id, first_name, last_name, designation, hire_date, address_id, facility_id, primary_department_code) values (67002, 'Massaging', 'Robin', 'Medical', TO_DATE('12/09/1967', 'MM/DD/YYYY'), TO_DATE('12/10/1990', 'MM/DD/YYYY'), 12, 2003, 'ER001');

insert into department_directors (staff_id, department_code) values (93001, 'ER000');
insert into department_directors (staff_id, department_code) values (67001, 'GP000');
insert into department_directors (staff_id, department_code) values (91001, 'GP001');
insert into department_directors (staff_id, department_code) values (89001, 'OP000');
insert into department_directors (staff_id, department_code) values (89001, 'SE000');


insert into severity_scales (id, name) values (1, '1-10');
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (1, 1, '1', 1);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (2, 1, '2', 3);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (3, 1, '3', 4);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (4, 1, '4', 5);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (5, 1, '5', 6);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (6, 1, '6', 7);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (7, 1, '7', 8);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (8, 1, '8', 9);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (9, 1, '9', 9);
insert into severity_scale_values (id, severity_scale_id, name, ordinal) values (10, 1, '10', 10);

insert into severity_scales(id, name) values (2, 'Normal/Severe');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (11, 2, 'Normal', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (12, 2, 'Severe', 2);

insert into severity_scales(id, name) values (3, 'Low/High');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (13, 3, 'Low', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (14, 3, 'High', 2);

insert into severity_scales(id, name) values (4, 'Normal/Premium');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (15, 4, 'Normal', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (16, 4, 'Premium', 2);

insert into severity_scales(id, name) values (5, 'Light/Normal/Heavy');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (17, 5, 'Light', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (18, 5, 'Normal', 2);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (19, 5, 'Heavy', 3);



insert into addresses (num, street, city, state, country) values (100, 'Avent Ferry Road', 'Raleigh', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('John', 'Smith', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 4, 2001);

insert into addresses (num, street, city, state, country) values (1016, 'Lexington Road', 'New York', 'New York', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jane', 'Doe', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 5, 2001);

insert into addresses (num, street, city, state, country) values (1022, 'Amphitheatre Parkway', 'Mountain View', 'California', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Rock', 'Star', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 4, 2002);

insert into addresses (num, street, city, state, country) values (1210, 'Sacramento', 'Santa Cruz', 'California', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Sheldon', 'Cooper', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 4, 2003);


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

/*
insert into patient_checkins (patient_id, start_time, end_time) values (2001, TO_TIMESTAMP('09/14/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('09/15/2015 06:14:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1001, 'SYM103', 'NON000', 14, 1, 0, 'Unknown');

insert into patient_checkins (patient_id, start_time, end_time) values (2002, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('12/24/2010 19:12:09', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1002, 'SYM101', 'ARM000', 5, 3, 1, 'Fell off bike');
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1002, 'SYM107', 'ARM000', 19, 3, 1, 'Fell off bike');

insert into patient_checkins (patient_id, start_time, end_time) values (2003, TO_TIMESTAMP('05/01/2011 10:30:05', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('05/01/2011 13:02:57', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1003, 'SYM102', 'NON000', 12, 1, 0, 'Pepper challenge');

insert into patient_checkins (patient_id, start_time, end_time) values (2004, TO_TIMESTAMP('11/18/2019 15:00:00', 'MM/DD/YYYY HH24:MI:SS'), TO_TIMESTAMP('11/18/2019 16:15:00', 'MM/DD/YYYY HH24:MI:SS'));
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1004, 'SYM106', 'EYE000', 11, 1, 0, 'Unknown');



insert into addresses (num, street, city, state, country) values (123, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Jamie', 'Gachie', TO_DATE('01/01/1990', 'MM/DD/YYYY'), 9007004567, 5, 2002);

insert into patient_checkins (patient_id, start_time, end_time) values (2005, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1005, 'SYM103', 'NON000', 13, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1005, 'SYM108', 'HED000', 6, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (234, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Logan', 'Willett', TO_DATE('02/29/2000', 'MM/DD/YYYY'), 9192453245, 5, 2002);

insert into patient_checkins (patient_id, start_time, end_time) values (2006, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1006, 'SYM103', 'NON000', 13, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1006, 'SYM108', 'HED000', 7, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (345, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Zaq', 'Balogun', TO_DATE('08/31/1970', 'MM/DD/YYYY'), 5403127893, 5, 2002);

insert into patient_checkins (patient_id, start_time, end_time) values (2007, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1007, 'SYM103', 'NON000', 14, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1007, 'SYM108', 'HED000', 9, 3, 1, 'Fell off bike');

insert into addresses (num, street, city, state, country) values (456, 'Street Ave', 'Chapel Hill', 'North Carolina', 'US');
insert into patients (first_name, last_name, dob, phone, address_id, facility_id) values ('Alex', 'Ngugi', TO_DATE('05/26/1984', 'MM/DD/YYYY'), 6184628437, 5, 2002);

insert into patient_checkins (patient_id, start_time, end_time) values (2008, TO_TIMESTAMP('12/24/2010 15:23:23', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM101', 'NON000', 10, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM102', 'HED000', 12, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM107', 'NON000', 19, 3, 1, 'Fell off bike');
insert into checkin_symptoms(checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1008, 'SYM109', 'NON000', 10, 3, 1, 'Fell off bike');

insert into assessment_rules (priority, description) values ('NORMAL', 'Common Cold');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1001, 'SYM103', 13, 'LESS_THAN_EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1001, 'SYM108', 7, 'LESS_THAN_EQUAL_TO');

insert into assessment_rules (priority, description) values ('HIGH', 'Pneumonia');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1002, 'SYM103', 13, 'GREATER_THAN_EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1002, 'SYM108', 7, 'GREATER_THAN_EQUAL_TO');

insert into assessment_rules (priority, description) values ('QUARANTINE', 'Zombie');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1003, 'SYM103', 14, 'EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1003, 'SYM108', 9, 'GREATER_THAN_EQUAL_TO');

insert into assessment_rules (priority, description) values ('QUARANTINE', 'Ebola');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM101', 10, 'EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM102', 12, 'EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM107', 19, 'EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, severity_scale_value_id, operation) values (1004, 'SYM109', 6, 'GREATER_THAN_EQUAL_TO');

*/