CREATE TABLE classification (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE addresses (
	id int NOT NULL,
	num int NOT NULL,
	street varchar2(100) NOT NULL,
	city varchar2(100) NOT NULL,
	state varchar2(100) NOT NULL,
	country varchar2(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE facilities (
	id int NOT NULL,
	name varchar2(100) NOT NULL,
	capacity int NOT NULL,
	classification_code varchar(100) NOT NULL,
	address_id int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (classification_code) REFERENCES classification(code),
	FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE TABLE certifications (
	acronym varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (acronym)
);

CREATE TABLE facility_certifications (
	facility_id int NOT NULL,
	certification_acronym varchar2(100) NOT NULL,
	certification_date DATE NOT NULL,
	expiration_date DATE NOT NULL,
	PRIMARY KEY (facility_id, certification_acronym),
	FOREIGN KEY (facility_id) REFERENCES facilities(id),
	FOREIGN KEY (certification_acronym) REFERENCES certifications(acronym)
);

CREATE TABLE staff (
	id int NOT NULL,
	name varchar2(100) NOT NULL,
	designation varchar2(100) NOT NULL,
	hire_date DATE NOT NULL,
	facility_id int NOT NULL,
	primary_department_code varchar(100) NOT NULL,
	username varchar2(100) NOT NULL,
	password varchar2(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (facility_id) REFERENCES facilities(id)
);

CREATE TABLE departments (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	type varchar2(100) NOT NULL,
	facility_id int NOT NULL,
	director_id int NOT NULL,
	PRIMARY KEY (code),
	FOREIGN KEY (facility_id) REFERENCES facilities(id),
	FOREIGN KEY (director_id) REFERENCES staff(id)
);

ALTER TABLE staff
	ADD FOREIGN KEY (primary_department_code) REFERENCES departments(code);

CREATE TABLE staff_secondary_departments (
	staff_id int NOT NULL,
	department_code varchar2(100) NOT NULL,
	PRIMARY KEY (staff_id, department_code),
	FOREIGN KEY (staff_id) REFERENCES staff(id),
	FOREIGN KEY (department_code) REFERENCES departments(code)
);

CREATE TABLE service (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE department_services (
	department_code varchar2(100) NOT NULL,
	service_code varchar2(100) NOT NULL,
	PRIMARY KEY (department_code, service_code),
	FOREIGN KEY (department_code) REFERENCES departments(code),
	FOREIGN KEY (service_code) REFERENCES service(code)
);

CREATE TABLE service_equipment (
	service_code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (service_code, name),
	FOREIGN KEY (service_code) REFERENCES service(code),
);

CREATE TABLE patients (
	id int NOT NULL,
	first_name varchar2(100) NOT NULL,
	last_name varchar2(100) NOT NULL,
	dob DATE NOT NULL,
	phone varchar2(100) NOT NULL,
	address_id int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (address_id) REFERENCES addresses(id)
);

CREATE TABLE body_parts (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE service_body_parts (
	body_part_code varchar2(100) NOT NULL,
	service_code varchar2(100) NOT NULL,
	PRIMARY KEY (body_part_code, service_code),
	FOREIGN KEY body_part_code REFERENCES body_parts(code),
	FOREIGN KEY service_code REFERENCES service(code)
);

CREATE TABLE severity_scales (
	id int NOT NULL,
	name varchar2(100) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE severity_scale_values (
	id int NOT NULL,
	severity_scale_id int NOT NULL,
	name varchar2(100) NOT NULL,
	ordinal varchar2(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY severity_scale_id REFERENCES severity_scales(id)
);

CREATE TABLE patient_checkin (
	id int NOT NULL,
	patient_id int NOT NULL,
	start TIMESTAMP NOT NULL,
	end TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY patient_id REFERENCES patients(id)
);

CREATE TABLE symptoms (
	code varchar2(100) NOT NULL,
	name varchar2(100) NOT NULL,
	severity_scale_id int NOT NULL,
	body_part_code varchar2(100) NOT NULL,
	PRIMARY KEY (code),
	FOREIGN KEY severity_scale_id REFERENCES severity_scales(id),
	FOREIGN KEY body_part_code REFERENCES body_parts(code),
	CHECK (substr(code,1,3) IN('sym', 'SYM', 'Sym'))
);

CREATE TABLE checkin_symptoms (
	checkin_id int NOT NULL,
	symptom_code varchar2(100) NOT NULL,
	body_part_code varchar2(100) NOT NULL,
	severity_scale_value_id varchar2(100) NOT NULL,
	duration int NOT NULL,
	reoccuring boolean DEFAULT false,
	incident TEXT(1000),
	PRIMARY KEY (checkin_id, symptom_code, severity_scale_value_id),
	FOREIGN KEY checkin_id REFERENCES patient_checkin(id),
	FOREIGN KEY symptom_code REFERENCES symptoms(code),
	FOREIGN KEY body_part_code REFERENCES body_parts(code),
	FOREIGN KEY severity_scale_value_id REFERENCES severity_scale_values(id)
)

CREATE TABLE outcome_reports (
	checkin_id int NOT NULL,
	discharge_status varchar2(100) NOT NULL,
	patient_acknowledged varchar2(100) NOT NULL,
	patient_acknowledge_reason varchar2(100) NOT NULL,
	time_out TIMESTAMP NOT NULL,
	PRIMARY KEY checkin_id,
	FOREIGN KEY checkin_id REFERENCES patient_checkin(id)
);

CREATE TABLE negative_experiences (
	checkin_id int NOT NULL,
	code varchar2(100) NOT NULL,
	description text(1000),
	PRIMARY KEY (checkin_id, code),
	FOREIGN KEY checkin_id REFERENCES outcome_reports(checkin_id),
)

CREATE TABLE priority_lists (
	id int NOT NULL,
	priority varchar2(100) NOT NULL,
	start TIMESTAMP NOT NULL,
	end TIMESTAMP NOT NULL,
	PRIMARY KEY id,
	FOREIGN KEY id REFERENCES patient_checkin(id),
);

CREATE TABLE assessment_rules (
	id int NOT NULL,
	priority varchar2(100) NOT NULL,
	description text(1000) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE assesment_symptoms(
	rule_id int NOT NULL,
	symptom_code varchar2(100) NOT NULL,
	severity_scale_value_id int NOT NULL,
	operation varchar2(100) NOT NULL,
	PRIMARY KEY (rule_id, symptom_code),
	FOREIGN KEY rule_id REFERENCES assessment_rules(id),
	FOREIGN KEY symptom_code REFERENCES symptoms(code),
	FOREIGN KEY severity_scale_value_id REFERENCES severity_scale_values(id)
);

CREATE TABLE referral_statuses (
	checkin_id int NOT NULL,
	facility_id int NOT NULL,
	staff_id int NOT NULL,
	treatment varchar2(100) NOT NULL,
	PRIMARY KEY checkin_id,
	FOREIGN KEY checkin_id REFERENCES outcome_reports(checkin_id),
	FOREIGN KEY facility_id REFERENCES facilities(id),
	FOREIGN KEY staff_id REFERENCES staff(id)
);

CREATE TABLE referral_reasons (
	checkin_id int NOT NULL,
	code varchar2(100) NOT NULL,
	service_code varchar2(100) NOT NULL,
	description text(1000) NOT NULL,
	PRIMARY KEY (checkin_id, code, service_code),
	FOREIGN KEY checkin_id REFERENCES referral_statuses (checkin_id),
	FOREIGN KEY service_code REFERENCES services(code)
)