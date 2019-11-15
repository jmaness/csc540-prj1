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
insert into departments_body_parts (department_code, body_part_code) values ('GP001', 'HED000');

insert into departments_body_parts (department_code, body_part_code) values ('GP000', 'NON000');
insert into departments_body_parts (department_code, body_part_code) values ('OP000', 'NON000');
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

/* Old hospitals. Still using their addresses. */
insert into addresses (id, num, street, city, state, country) values (4, 100, 'UNC Street', 'Chapel Hill', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('UNC Hospital', 500, '01', 4);
insert into addresses (id, num, street, city, state, country) values (5, 200, 'Duke Street', 'Durham', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('Duke Hospital', 500, '01', 5);
insert into addresses (id, num, street, city, state, country) values (6, 300, 'NCSU Street', 'Raleigh', 'North Carolina', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('NCSU Hospital', 500, '01', 6);
