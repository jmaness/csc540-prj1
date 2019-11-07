CREATE TABLE classifications (
	code VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE addresses (
	id INTEGER NOT NULL,
	num INTEGER NOT NULL,
	street VARCHAR2(100) NOT NULL,
	city VARCHAR2(100) NOT NULL,
	state VARCHAR2(100) NOT NULL,
	country VARCHAR2(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE facilities (
	id INTEGER NOT NULL,
	name VARCHAR2(100) NOT NULL,
	capacity INTEGER NOT NULL,
	classification_code VARCHAR2(100) NOT NULL,
	address_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (classification_code) REFERENCES classifications (code),
	FOREIGN KEY (address_id) REFERENCES addresses (id)
);

CREATE TABLE certifications (
	acronym VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	PRIMARY KEY (acronym)
);

CREATE TABLE facility_certifications (
	facility_id INTEGER NOT NULL,
	certification_acronym VARCHAR2(100) NOT NULL,
	certification_date DATE NOT NULL,
	expiration_date DATE NOT NULL,
	PRIMARY KEY (facility_id, certification_acronym),
	FOREIGN KEY (facility_id) REFERENCES facilities (id),
	FOREIGN KEY (certification_acronym) REFERENCES certifications (acronym)
);

CREATE TABLE departments (
	code VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	type VARCHAR2(100) NOT NULL,
	facility_id INTEGER NOT NULL,
	PRIMARY KEY (code),
	FOREIGN KEY (facility_id) REFERENCES facilities (id)
);

CREATE TABLE staff (
	id INTEGER NOT NULL,
	first_name VARCHAR2(100) NOT NULL,
	last_name VARCHAR2(100) NOT NULL,
	designation VARCHAR2(100) NOT NULL,
	hire_date DATE NOT NULL,
	address_id INTEGER NOT NULL,
	facility_id INTEGER NOT NULL,
	primary_department_code VARCHAR2(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (facility_id) REFERENCES facilities (id),
	FOREIGN KEY (address_id) REFERENCES addresses (id),
	FOREIGN KEY (primary_department_code) REFERENCES departments (code)
);

CREATE TABLE department_directors (
    department_code VARCHAR2(100) NOT NULL,
    staff_id INTEGER NOT NULL,
    PRIMARY KEY (department_code),
    FOREIGN KEY (department_code) REFERENCES departments (code),
    FOREIGN KEY (staff_id) REFERENCES staff (id)
);

CREATE TABLE staff_secondary_departments (
	staff_id INTEGER NOT NULL,
	department_code varchar2(100) NOT NULL,
	PRIMARY KEY (staff_id, department_code),
	FOREIGN KEY (staff_id) REFERENCES staff (id),
	FOREIGN KEY (department_code) REFERENCES departments (code)
);

CREATE TABLE services (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE department_services (
	department_code varchar2(100) NOT NULL,
	service_code varchar2(100) NOT NULL,
	PRIMARY KEY (department_code, service_code),
	FOREIGN KEY (department_code) REFERENCES departments (code),
	FOREIGN KEY (service_code) REFERENCES services (code)
);

CREATE TABLE service_equipment (
	service_code VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	PRIMARY KEY (service_code, name),
	FOREIGN KEY (service_code) REFERENCES services (code)
);

CREATE TABLE patients (
	id INTEGER NOT NULL,
	first_name VARCHAR2(100) NOT NULL,
	last_name VARCHAR2(100) NOT NULL,
	dob DATE NOT NULL,
	phone VARCHAR2(100) NOT NULL,
	address_id INTEGER NOT NULL,
	facility_id INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (address_id) REFERENCES addresses (id),
	FOREIGN KEY (facility_id) REFERENCES facilities (id)
);

CREATE TABLE body_parts (
	code VARCHAR2(100) NOT NULL,
	name VARCHAR2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE service_body_parts (
	body_part_code VARCHAR2(100) NOT NULL,
	service_code VARCHAR2(100) NOT NULL,
	PRIMARY KEY (body_part_code, service_code),
	FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
	FOREIGN KEY (service_code) REFERENCES services (code)
);

CREATE TABLE severity_scales (
	id INTEGER NOT NULL,
	name VARCHAR2(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE severity_scale_values (
	id INTEGER NOT NULL,
	severity_scale_id INTEGER NOT NULL,
	name VARCHAR2(100) NOT NULL,
	ordinal INTEGER NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (severity_scale_id) REFERENCES severity_scales (id)
);

CREATE TABLE patient_checkins (
	id INTEGER NOT NULL,
	patient_id INTEGER NOT NULL,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE symptoms (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	severity_scale_id INTEGER NOT NULL,
	body_part_code VARCHAR2(100),
	PRIMARY KEY (code),
	FOREIGN KEY (severity_scale_id) REFERENCES severity_scales (id),
	FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
	CHECK (upper(substr(code,1,3)) IN ('SYM'))
);

CREATE TABLE checkin_symptoms (
	checkin_id INTEGER NOT NULL,
	symptom_code VARCHAR2(100) NOT NULL,
	body_part_code VARCHAR2(100) NOT NULL,
	severity_scale_value_id INTEGER NOT NULL,
	duration INTEGER NOT NULL,
	reoccurring NUMBER(1) NOT NULL,
	incident VARCHAR2(1000),
	PRIMARY KEY (checkin_id, symptom_code),
	FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id),
	FOREIGN KEY (symptom_code) REFERENCES symptoms (code),
	FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
	FOREIGN KEY (severity_scale_value_id) REFERENCES severity_scale_values (id)
);

CREATE TABLE outcome_reports (
	checkin_id INTEGER NOT NULL,
	discharge_status VARCHAR2(100) NOT NULL,
	treatment CLOB NOT NULL,
    out_time TIMESTAMP NOT NULL,
	patient_acknowledged NUMBER(1),
	patient_acknowledge_reason CLOB,
	PRIMARY KEY (checkin_id),
	FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id)
);

CREATE TABLE negative_experiences (
	checkin_id INTEGER NOT NULL,
	code VARCHAR2(100) NOT NULL,
	description CLOB,
	PRIMARY KEY (checkin_id, code),
	FOREIGN KEY (checkin_id) REFERENCES outcome_reports (checkin_id)
);

CREATE TABLE priority_lists (
	checkin_id INTEGER NOT NULL,
	priority VARCHAR2(100) NOT NULL,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP NOT NULL,
	PRIMARY KEY (checkin_id),
	FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id)
);

CREATE TABLE assessment_rules (
	id INTEGER NOT NULL,
	priority VARCHAR2(100) NOT NULL,
	description CLOB NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE assessment_symptoms(
	rule_id INTEGER NOT NULL,
	symptom_code VARCHAR2(100) NOT NULL,
	severity_scale_value_id INTEGER NOT NULL,
	operation VARCHAR2(100) NOT NULL,
	PRIMARY KEY (rule_id, symptom_code),
	FOREIGN KEY (rule_id) REFERENCES assessment_rules (id),
	FOREIGN KEY (symptom_code) REFERENCES symptoms (code),
	FOREIGN KEY (severity_scale_value_id) REFERENCES severity_scale_values (id)
);

CREATE TABLE referral_statuses (
	checkin_id INTEGER NOT NULL,
	facility_id INTEGER NOT NULL,
	staff_id INTEGER NOT NULL,
	treatment VARCHAR2(100) NOT NULL,
	PRIMARY KEY (checkin_id),
	FOREIGN KEY (checkin_id) REFERENCES outcome_reports (checkin_id),
	FOREIGN KEY (facility_id) REFERENCES facilities (id),
	FOREIGN KEY (staff_id) REFERENCES staff (id)
);

CREATE TABLE referral_reasons (
	checkin_id INTEGER NOT NULL,
	code VARCHAR2(100) NOT NULL,
	service_code VARCHAR2(100) NOT NULL,
	description CLOB,
	PRIMARY KEY (checkin_id, code, service_code),
	FOREIGN KEY (checkin_id) REFERENCES referral_statuses (checkin_id),
	FOREIGN KEY (service_code) REFERENCES services (code)
);
