insert into certifications (acronym, name) values ('CSC000', 'Comprehensive Stroke Certification');
insert into certifications (acronym, name) values ('ISO000', 'ISO Certification');
insert into certifications (acronym, name) values ('ISO001', 'ISO Certification');
insert into certifications (acronym, name) values ('PSC000', 'Primary Stroke Certification');

insert into services (code, name) values ('SER01', 'Emergency');
insert into services (code, name) values ('SGP01', 'General practice');
insert into services (code, name) values ('VIS01', 'Vision');

insert into addresses (num, street, city, state, zip, country) values (2650, 'Wolf Village Way Box 7220', 'Raleigh', 'NC', 27695, 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1000, 'Wolf Hospital', 300, '03', 1);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (1000, 'CSC000', TO_DATE('01/01/2000', 'MM/DD/YYYY'), TO_DATE('01/01/2020', 'MM/DD/YYYY'));

insert into addresses (num, street, city, state, zip, country) values (2650, '2500 Sacramento', 'Santa Cruz', 'CA', 90021, 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1001, 'California Health Care', 150, '02', 2);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (1001, 'ISO000', TO_DATE('01/01/2001', 'MM/DD/YYYY'), TO_DATE('01/01/2021', 'MM/DD/YYYY'));


insert into addresses (num, street, city, state, zip, country) values (489, 'First Avenue', 'New York', 'NY', 10001, 'US');
insert into facilities (id, name, capacity, classification_code, address_id) values (1002, 'Suny Medical Center', 10, '01', 3);
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (1002, 'ISO001', TO_DATE('01/01/2002', 'MM/DD/YYYY'), TO_DATE('01/01/2022', 'MM/DD/YYYY'));
insert into facility_certifications (facility_id, certification_acronym, certification_date, expiration_date) values (1002, 'PSC000', TO_DATE('01/01/2000', 'MM/DD/YYYY'), TO_DATE('01/01/2020', 'MM/DD/YYYY'));

insert into departments (code, name, type, facility_id) values ('GP000', 'General Practice department', 'medical', 1000);
insert into departments (code, name, type, facility_id) values ('OP000', 'Optometry', 'medical', 1000);
insert into departments (code, name, type, facility_id) values ('SE000', 'Security', 'non-medical', 1000);
insert into departments (code, name, type, facility_id) values ('ER001', 'Emergency room', 'medical', 1001);
insert into departments (code, name, type, facility_id) values ('GP001', 'General Practice department', 'medical', 1001);
insert into departments (code, name, type, facility_id) values ('ER001', 'Emergency room', 'medical', 1002);

insert into service_body_parts (body_part_code, service_code) values ('EYE000', 'VIS01');
insert into department_services (department_code, service_code) values ('ER000', 'SER01');
insert into department_services (department_code, service_code) values ('GP000', 'SGP01');
insert into department_services (department_code, service_code) values ('GP001', 'SGP01');
insert into department_services (department_code, service_code) values ('OP000', 'VIS01');

insert into service_equipment (service_code, name) values ('ER000', 'ER combo rack');
insert into service_equipment (service_code, name) values ('GP000', 'Blood pressure monitor');
insert into service_equipment (service_code, name) values ('GP001', 'Blood pressure monitor');
insert into service_equipment (service_code, name) values ('GP000', 'Thermometer');
insert into service_equipment (service_code, name) values ('GP001', 'Thermometer');
insert into service_equipment (service_code, name) values ('OP000', 'Vision Screener');

/* Old hospitals */
insert into addresses (num, street, city, state, country) values (100, 'UNC Street', 'Chapel Hill', 'NC', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('UNC Hospital', 500, '01', 4);
insert into addresses (num, street, city, state, country) values (200, 'Duke Street', 'Durham', 'NC', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('Duke Hospital', 500, '01', 5);
insert into addresses (num, street, city, state, country) values (300, 'NCSU Street', 'Raleigh', 'NC', 'US');
insert into facilities (name, capacity, classification_code, address_id) values ('NCSU Hospital', 500, '01', 6);