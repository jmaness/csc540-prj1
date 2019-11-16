-- Sample classification data from project description doc
insert into classifications (code, name) values ('01', 'Primary');
insert into classifications (code, name) values ('02', 'Secondary');
insert into classifications (code, name) values ('03', 'Tertiary');

insert into certificates (acronym, name, certification_date, expiration_date) values ('CER001', 'Comprehensive Stroke Certification', TO_DATE('11/12/1990', 'MM/DD/YYYY'), TO_DATE('11/11/2025', 'MM/DD/YYYY'));
insert into certificates (acronym, name, certification_date, expiration_date) values ('CER002', 'ISO Certification', TO_DATE('05/09/2011', 'MM/DD/YYYY'), TO_DATE('02/08/2024', 'MM/DD/YYYY'));
insert into certificates (acronym, name, certification_date, expiration_date) values ('CER003', 'Primary Stroke Certification', TO_DATE('01/01/2018', 'MM/DD/YYYY'), TO_DATE('12/31/2028', 'MM/DD/YYYY'));

insert into services (code, name) values ('SER01', 'Emergency');
insert into services (code, name) values ('SGP01', 'General practice');
insert into services (code, name) values ('VIS01', 'Vision');
insert into services (code, name) values ('OTS01', 'Other');

insert into addresses (id, num, street, city, state, country) values (1, 2650, 'Wolf Village Way Box 7220', 'Raleigh', 'NC', 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1000, 'Wolf Hospital', 300, '03', 1);
insert into facility_certifications (facility_id, certification_acronym) values (1000, 'CER001');

insert into addresses (id, num, street, city, state, country) values (2, 2650, '2500 Sacramento', 'Santa Cruz', 'CA', 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1001, 'California Health Care', 150, '02', 2);
insert into facility_certifications (facility_id, certification_acronym) values (1001, 'CER002');

insert into addresses (id, num, street, city, state, country) values (3, 489, 'First Avenue', 'New York', 'NY', 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1002, 'Suny Medical Center', 10, '01', 3);
insert into facility_certifications (facility_id, certification_acronym) values (1002, 'CER002');
insert into facility_certifications (facility_id, certification_acronym) values (1002, 'CER003');

insert into body_parts (code, name) values ('ARM000', 'Left Arm');
insert into body_parts (code, name) values ('ARM001', 'Right Arm');
insert into body_parts (code, name) values ('ABD000', 'Abdominal');
insert into body_parts (code, name) values ('EYE000', 'Eye');
insert into body_parts (code, name) values ('HRT000', 'Heart');
insert into body_parts (code, name) values ('CST000', 'Chest');
insert into body_parts (code, name) values ('HED000', 'Head');
insert into body_parts (code, name) values ('NON000', 'None');

insert into departments (code, name, type, facility_id) values ('GP000', 'General Practice department', 'Medical', 1000);
insert into departments (code, name, type, facility_id) values ('OP000', 'Optometry', 'Medical', 1000);
insert into departments (code, name, type, facility_id) values ('SE000', 'Security', 'Non-medical', 1000);
insert into departments (code, name, type, facility_id) values ('ER000', 'Emergency room', 'Medical', 1001);
insert into departments (code, name, type, facility_id) values ('GP001', 'General Practice department', 'Medical', 1001);
insert into departments (code, name, type, facility_id) values ('ER001', 'Emergency room', 'Medical', 1002);

insert into departments_body_parts (department_code, body_part_code) values ('OP000', 'EYE000');

-- Associate the None body part to all departments
insert into departments_body_parts (department_code, body_part_code) values ('OP000', 'NON000');
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

insert into addresses (id, num, street, city, state, zip, country) values (4, 83, 'Vernon St', 'Scotch Plains', 'NJ', '07076', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (89001, 'Medical', 'Robot', 'Medical', TO_DATE('04/19/2019', 'MM/DD/YYYY'), TO_DATE('06/21/2019', 'MM/DD/YYYY'), 4, 1000, 'OP000');

insert into addresses (id, num, street, city, state, zip, country) values (5, 69, 'Holly Dr', 'Blacksburg', 'VA', '24060', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (93001, 'Musical', 'Robert', 'Medical', TO_DATE('01/29/1983', 'MM/DD/YYYY'), TO_DATE('08/29/2018', 'MM/DD/YYYY'), 5, 1001, 'ER000');

insert into addresses (id, num, street, city, state, zip, country) values (6, 7540, 'Plymouth Court', 'Derry', 'NH', '03038', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (67001, 'Muscular', 'Rob', 'Medical', TO_DATE('12/09/1967', 'MM/DD/YYYY'), TO_DATE('10/12/1983', 'MM/DD/YYYY'), 6, 1000, 'GP000');

insert into addresses (id, num, street, city, state, zip, country) values (7, 8196, 'Big Rock Cove Road', 'Lutherville Timonium', 'MD', '21093', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (88001, 'Mechanical', 'Roboto', 'Medical', TO_DATE('05/18/1988', 'MM/DD/YYYY'), TO_DATE('06/21/2019', 'MM/DD/YYYY'), 7, 1000, 'GP000');
insert into staff_secondary_departments (staff_id, department_code) values (88001, 'OP000');

insert into addresses (id, num, street, city, state, zip, country) values (8, 697, 'Lawrence Ave.', 'Teaneck', 'NJ', '07666','US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (91001, 'Millenium', 'Roberten', 'Medical', TO_DATE('06/28/1991', 'MM/DD/YYYY'), TO_DATE('09/20/2018', 'MM/DD/YYYY'), 8, 1001, 'GP001');

insert into addresses (id, num, street, city, state, zip, country) values (9, 685, 'South Chapel Lane', 'Branford', 'CT', '06405', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (66001, 'Missionary', 'Robinson', 'Medical', TO_DATE('07/08/1966', 'MM/DD/YYYY'), TO_DATE('10/01/1993', 'MM/DD/YYYY'), 9, 1001, 'ER000');

insert into addresses (id, num, street, city, state, zip, country) values (10, 7056, 'W. Piper Dr.', 'Macon', 'GA', '31204', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (89002, 'Miscellaneous', 'Robotor', 'Non-medical', TO_DATE('04/19/1989', 'MM/DD/YYYY'), TO_DATE('08/19/2014', 'MM/DD/YYYY'), 10, 1000, 'SE000');

insert into addresses (id, num, street, city, state, zip, country) values (11, 40, 'N. Peachtree Drive', 'Sunnyside', 'NY', '11104', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (93002, 'Musician', 'Robot', 'Non-medical', TO_DATE('01/29/1993', 'MM/DD/YYYY'), TO_DATE('10/18/2017', 'MM/DD/YYYY'), 11, 1000, 'SE000');

insert into addresses (id, num, street, city, state, zip, country) values (12, 22, 'Sutor St.', 'Laurel', 'MD', '20707', 'US');
insert into staff (id, first_name, last_name, designation, dob, hire_date, address_id, facility_id, primary_department_code) values (67002, 'Massaging', 'Robin', 'Medical', TO_DATE('12/09/1967', 'MM/DD/YYYY'), TO_DATE('12/10/1990', 'MM/DD/YYYY'), 12, 1002, 'ER001');

insert into department_directors (staff_id, department_code) values (93001, 'ER000');
insert into department_directors (staff_id, department_code) values (67001, 'GP000');
insert into department_directors (staff_id, department_code) values (91001, 'GP001');
insert into department_directors (staff_id, department_code) values (89001, 'OP000');
insert into department_directors (staff_id, department_code) values (89001, 'SE000');


-- Severity scales and associated values
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

insert into severity_scales(id, name) values (5, 'Moderate/Heavy');
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (17, 5, 'Moderate', 1);
insert into severity_scale_values(id, severity_scale_id, name, ordinal) values (18, 5, 'Heavy', 2);


-- All possible symptoms
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM001', 'Pain', 1, 'NON000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM002', 'Diarrhea', 2, 'ABD000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM003', 'Fever', 3, 'NON000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM004', 'Physical Exam', 4, 'NON000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM005', 'Lightheadedness', 2, 'HED000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM006', 'Blurred vision', 2, 'EYE000');
insert into symptoms (code, name, severity_scale_id, body_part_code) values ('SYM007', 'Bleeding', 5, 'NON000');


-- Patients
insert into addresses (id, num, street, city, state, country) values (13, 100, 'Avent Ferry Road', 'Raleigh', 'North Carolina', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (1, 'John', 'Smith', TO_DATE('01/01/1990', 'MM/DD/YYYY'), '9007004567', 13, 1000);
insert into patient_checkins (id, patient_id, start_time, end_time) values (1, 1, TO_TIMESTAMP('11/16/2019 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (1, 'SYM003', 'NON000', 14, 1, 0, 'Unknown');


insert into addresses (id, num, street, city, state, country) values (14, 1016, 'Lexington Road', 'New York', 'New York', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (2, 'Jane', 'Doe', TO_DATE('02/29/2000', 'MM/DD/YYYY'), '9192453245', 14, 1000);
insert into patient_checkins (id, patient_id, start_time, end_time) values (2, 2, TO_TIMESTAMP('11/16/2019 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (2, 'SYM001', 'ARM000', 5, 3, 1, 'Fell off bike');


insert into addresses (id, num, street, city, state, country) values (15, 1022, 'Amphitheatre Parkway', 'Mountain View', 'California', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (3, 'Rock', 'Star', TO_DATE('08/31/1970', 'MM/DD/YYYY'), '5403127893', 15, 1001);
insert into patient_checkins (id, patient_id, start_time, end_time) values (3, 3, TO_TIMESTAMP('11/16/2019 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (3, 'SYM002', 'NON000', 12, 1, 0, 'Pepper challenge');


insert into addresses (id, num, street, city, state, country) values (16, 1210, 'Sacramento', 'Santa Cruz', 'California', 'US');
insert into patients (id, first_name, last_name, dob, phone, address_id, facility_id) values (4, 'Sheldon', 'Cooper', TO_DATE('05/26/1984', 'MM/DD/YYYY'), '6184628437', 16, 1002);
insert into patient_checkins (id, patient_id, start_time, end_time) values (4, 4, TO_TIMESTAMP('11/16/2019 10:00:00', 'MM/DD/YYYY HH24:MI:SS'), null);
insert into checkin_symptoms (checkin_id, symptom_code, body_part_code, severity_scale_value_id, duration, reoccurring, incident) values (4, 'SYM006', 'EYE000', 11, 1, 0, 'Unknown');


-- Assessment Rules
insert into assessment_rules (id, priority, description) values (1, 'HIGH', 'Pain in chest > 7 plus high Fever is high priority');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (1, 'SYM001', 'CST000', 7, 'GREATER_THAN');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (1, 'SYM003', null, 14, 'EQUAL_TO');

insert into assessment_rules (id, priority, description) values (2, 'HIGH', 'headache of scale > 7, plus blurred vision, plus lightheadedness is priority high');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (2, 'SYM001', 'HED000', 7, 'GREATER_THAN');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (2, 'SYM006', null, 11, 'GREATER_THAN_EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (2, 'SYM005', null, 11, 'GREATER_THAN_EQUAL_TO');

insert into assessment_rules (id, priority, description) values (3, 'NORMAL', 'headache of scale <=7, plus blurred vision is priority normal');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (3, 'SYM001', 'HED000', 7, 'LESS_THAN_OR_EQUAL_TO');
insert into assessment_symptoms (rule_id, symptom_code, body_part_code, severity_scale_value_id, operation) values (3, 'SYM006', null, 11, 'GREATER_THAN_EQUAL_TO');
