CREATE TABLE classifications (
    code VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    PRIMARY KEY (code),
    CONSTRAINT check_class_code
        CHECK (code = '01' OR code = '02' OR code = '03')
);

CREATE TABLE addresses (
    id INTEGER NOT NULL CHECK (id > 0),
    num INTEGER NOT NULL,
    street VARCHAR2(100) NOT NULL CHECK (street <> ''),
    city VARCHAR2(100) NOT NULL CHECK (city <> ''),
    state VARCHAR2(100) NOT NULL CHECK (state <> ''),
    zip VARCHAR2(100),
    country VARCHAR2(100) NOT NULL CHECK (country <> ''),
    PRIMARY KEY (id),
    CONSTRAINT check_num
        CHECK (0 <= num)
);

CREATE TABLE facilities (
    id INTEGER NOT NULL CHECK (id > 0),
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    capacity INTEGER NOT NULL,
    classification_code VARCHAR2(100) NOT NULL,
    address_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (classification_code) REFERENCES classifications (code),
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    CONSTRAINT check_capacity
        CHECK (0 <= capacity)
);

CREATE TABLE certificates (
    acronym VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    certification_date DATE NOT NULL,
    expiration_date DATE NOT NULL,
    PRIMARY KEY (acronym),
    CONSTRAINT check_acronym_alphanum
        CHECK (regexp_like(acronym,'^[a-zA-Z0-9]*$')),
    CONSTRAINT exp_cert_order_check
        CHECK (certification_date < expiration_date)
);

CREATE TABLE facility_certifications (
    facility_id INTEGER NOT NULL,
    certification_acronym VARCHAR2(100) NOT NULL,
    PRIMARY KEY (facility_id, certification_acronym),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    FOREIGN KEY (certification_acronym) REFERENCES certificates (acronym)
);

CREATE TABLE departments (
    code VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    type VARCHAR2(100) NOT NULL,
    facility_id INTEGER NOT NULL,
    PRIMARY KEY (code),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    CONSTRAINT check_type
        CHECK (type = 'Medical' OR type = 'Non-medical'),
    CONSTRAINT check_dept_alphanum
        CHECK (regexp_like(code,'^[a-zA-Z0-9]*$'))
);

CREATE TABLE staff (
    id INTEGER NOT NULL CHECK (id > 0),
    first_name VARCHAR2(100) NOT NULL CHECK (first_name <> ''),
    last_name VARCHAR2(100) NOT NULL CHECK (last_name <> ''),
    designation VARCHAR2(100) NOT NULL,
    dob DATE NOT NULL,
    hire_date DATE NOT NULL,
    address_id INTEGER NOT NULL,
    facility_id INTEGER NOT NULL,
    primary_department_code VARCHAR2(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    FOREIGN KEY (primary_department_code) REFERENCES departments (code),
    CONSTRAINT designation_check
        CHECK (designation = 'Medical' OR designation = 'Non-medical')
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
    PRIMARY KEY (code),
    CONSTRAINT check_svc_alphanum
        CHECK (regexp_like(code,'^[a-zA-Z0-9]*$'))
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
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    PRIMARY KEY (service_code, name),
    FOREIGN KEY (service_code) REFERENCES services (code)
);

CREATE TABLE patients (
    id INTEGER NOT NULL CHECK (id > 0),
    first_name VARCHAR2(100) NOT NULL CHECK (first_name <> ''),
    last_name VARCHAR2(100) NOT NULL CHECK (last_name <> ''),
    dob DATE NOT NULL,
    phone VARCHAR2(100) NOT NULL CHECK (LENGTH(phone) >= 10),
    address_id INTEGER NOT NULL,
    facility_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id)
);

CREATE TABLE body_parts (
    code VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    PRIMARY KEY (code),
    CONSTRAINT check_part_alphanum
        CHECK (regexp_like(code,'^[a-zA-Z0-9]*$'))
);

CREATE TABLE departments_body_parts (
    body_part_code VARCHAR2(100) NOT NULL,
    department_code VARCHAR2(100) NOT NULL,
    PRIMARY KEY (body_part_code, department_code),
    FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
    FOREIGN KEY (department_code) REFERENCES departments (code)
);

CREATE TABLE severity_scales (
    id INTEGER NOT NULL CHECK (id > 0),
    name VARCHAR2(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE severity_scale_values (
    id INTEGER NOT NULL CHECK (id > 0),
    severity_scale_id INTEGER NOT NULL,
    name VARCHAR2(100) NOT NULL CHECK (name <> ''),
    ordinal INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (severity_scale_id) REFERENCES severity_scales (id)
);

CREATE TABLE patient_checkins (
    id INTEGER NOT NULL CHECK (id > 0),
    patient_id INTEGER NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    active NUMBER(1) DEFAULT 1 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (patient_id) REFERENCES patients (id)
);

CREATE TABLE symptoms (
    code varchar2(100) NOT NULL,
    name varchar2(100) NOT NULL CHECK (name <> ''),
    severity_scale_id INTEGER NOT NULL,
    body_part_code VARCHAR2(100) NOT NULL,
    PRIMARY KEY (code),
    FOREIGN KEY (severity_scale_id) REFERENCES severity_scales (id),
    FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
    CONSTRAINT check_sys_alphanum
        CHECK (regexp_like(code,'^SYM[a-zA-Z0-9]*$'))
);

CREATE TABLE checkin_symptoms (
    checkin_id INTEGER NOT NULL,
    symptom_code VARCHAR2(100) NOT NULL,
    body_part_code VARCHAR2(100) NOT NULL,
    severity_scale_value_id INTEGER NOT NULL,
    duration INTEGER NOT NULL CHECK (duration > 0),
    reoccurring NUMBER(1) NOT NULL,
    incident VARCHAR2(1000),
    PRIMARY KEY (checkin_id, symptom_code),
    FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id) ON DELETE CASCADE,
    FOREIGN KEY (symptom_code) REFERENCES symptoms (code),
    FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
    FOREIGN KEY (severity_scale_value_id) REFERENCES severity_scale_values (id),
    CONSTRAINT reoccuring_bool
        CHECK (reoccurring = 0 OR reoccurring = 1)
);

CREATE TABLE outcome_reports (
    checkin_id INTEGER NOT NULL,
    discharge_status VARCHAR2(100) NOT NULL,
    treatment VARCHAR2(1024) NOT NULL,
    out_time TIMESTAMP NOT NULL,
    patient_acknowledged NUMBER(1),
    patient_acknowledge_reason VARCHAR2(1000),
    PRIMARY KEY (checkin_id),
    FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id),
    CONSTRAINT treatment_not_empty
        CHECK (treatment <> ''),
    CONSTRAINT check_discharge
    CHECK (discharge_status = 'TREATED_SUCCESSFULLY'
        OR discharge_status = 'REFERRED'
        OR discharge_status = 'DECEASED')
);

CREATE TABLE negative_experiences (
    checkin_id INTEGER NOT NULL,
    code VARCHAR2(100) NOT NULL,
    description VARCHAR2(1000),
    PRIMARY KEY (checkin_id, code),
    FOREIGN KEY (checkin_id) REFERENCES outcome_reports (checkin_id),
    CONSTRAINT check_code
        CHECK (code = 'MISDIAGNOSIS' OR code = 'ACQUIRED_INFECTION')
);

CREATE TABLE priority_lists (
    checkin_id INTEGER NOT NULL,
    priority VARCHAR2(100) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    PRIMARY KEY (checkin_id),
    FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id),
    CONSTRAINT priority_check_lists
        CHECK (priority = 'HIGH' OR priority = 'NORMAL' OR priority = 'QUARANTINE')
);

CREATE TABLE assessment_rules (
    id INTEGER NOT NULL CHECK (id > 0),
    priority VARCHAR2(100) NOT NULL,
    description VARCHAR2(1000) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT priority_check_rules
        CHECK (priority = 'HIGH' OR priority = 'NORMAL' OR priority = 'QUARANTINE')
);

CREATE TABLE assessment_symptoms(
    rule_id INTEGER NOT NULL,
    symptom_code VARCHAR2(100) NOT NULL,
    body_part_code VARCHAR2(100),
    severity_scale_value_id INTEGER NOT NULL,
    operation VARCHAR2(100) NOT NULL,
    PRIMARY KEY (rule_id, symptom_code),
    FOREIGN KEY (rule_id) REFERENCES assessment_rules (id),
    FOREIGN KEY (symptom_code) REFERENCES symptoms (code),
    FOREIGN KEY (body_part_code) REFERENCES body_parts (code),
    FOREIGN KEY (severity_scale_value_id) REFERENCES severity_scale_values (id)
);

CREATE TABLE referral_statuses (
    checkin_id INTEGER NOT NULL,
    facility_id INTEGER,
    staff_id INTEGER NOT NULL,
    PRIMARY KEY (checkin_id),
    FOREIGN KEY (checkin_id) REFERENCES outcome_reports (checkin_id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    FOREIGN KEY (staff_id) REFERENCES staff (id),
    CONSTRAINT facility_id_check
        CHECK (0 <= facility_id OR facility_id IS NULL)
);

CREATE TABLE referral_reasons (
    checkin_id INTEGER NOT NULL,
    code VARCHAR2(100) NOT NULL,
    service_code VARCHAR2(100) NOT NULL,
    description VARCHAR2(1000),
    PRIMARY KEY (checkin_id, code, service_code),
    FOREIGN KEY (checkin_id) REFERENCES referral_statuses (checkin_id),
    FOREIGN KEY (service_code) REFERENCES services (code),
    CONSTRAINT check_ref_code
        CHECK (code = 'SERVICE_UNAVAILABLE'
                   OR code = 'SERVICE_NOT_PRESENT_AT_FACILITY'
                   OR code = 'NONPAYMENT')
);

CREATE TABLE patient_vitals (
    checkin_id INTEGER NOT NULL,
    temperature NUMBER NOT NULL,
    systolic_blood_pressure INTEGER NOT NULL,
    diastolic_blood_pressure INTEGER NOT NULL,
    PRIMARY KEY (checkin_id),
    FOREIGN KEY (checkin_id) REFERENCES patient_checkins (id),
    CONSTRAINT temperature_check
        CHECK (0 < temperature),
    CONSTRAINT blood_pressure_check
        CHECK (0 < systolic_blood_pressure AND 0 < diastolic_blood_pressure)
);

CREATE OR REPLACE TRIGGER referral_reasons_count_check
    BEFORE INSERT OR UPDATE ON referral_reasons
    FOR EACH ROW
DECLARE
    reason_count number;
BEGIN
    SELECT COUNT(*) INTO reason_count FROM referral_reasons WHERE checkin_id = :new.checkin_id;

    IF (reason_count >= 4)
    THEN
        RAISE_APPLICATION_ERROR( -20001,
                                 'Maximum number of allowed referral reasons exceeded');
    END IF;
END;
/

CREATE SEQUENCE address_sequence START WITH 2001;

CREATE OR REPLACE TRIGGER addresses_on_insert
    BEFORE INSERT ON addresses
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, address_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE patient_sequence START WITH 2001;

CREATE OR REPLACE TRIGGER patients_on_insert
    BEFORE INSERT ON patients
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, patient_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE facility_sequence START WITH 2001;

CREATE OR REPLACE TRIGGER facilities_on_insert
    BEFORE INSERT ON facilities
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, facility_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE staff_sequence START WITH 100001;

CREATE OR REPLACE TRIGGER staff_on_insert
    BEFORE INSERT ON staff
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, staff_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE patient_checkin_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER patient_checkin_on_insert
    BEFORE INSERT ON patient_checkins
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, patient_checkin_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE symptom_sequence START WITH 101;

CREATE OR REPLACE TRIGGER symptoms_on_insert
    BEFORE INSERT ON symptoms
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.code, 'SYM' || LPAD(symptom_sequence.nextval, 3, '0'))
    INTO :new.code
    FROM dual;
END;
/

CREATE SEQUENCE assessment_rule_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER assessment_rules_on_insert
    BEFORE INSERT ON assessment_rules
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, assessment_rule_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE severity_scales_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER severity_scales_on_insert
    BEFORE INSERT ON severity_scales
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, severity_scales_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE SEQUENCE scale_values_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER scale_values_on_insert
    BEFORE INSERT ON severity_scale_values
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, scale_values_sequence.nextval)
    INTO :new.id
    FROM dual;
END;
/

CREATE OR REPLACE TRIGGER certification_date_check
    BEFORE INSERT OR UPDATE ON certificates
    FOR EACH ROW
BEGIN
    IF( :new.certification_date >= CURRENT_DATE )
    THEN
        RAISE_APPLICATION_ERROR( -20001,
                                 'Invalid date' );
    END IF;
END;
/

CREATE OR REPLACE TRIGGER staff_hire_date_check
    BEFORE INSERT OR UPDATE ON staff
    FOR EACH ROW
BEGIN
    IF( :new.hire_date >= CURRENT_DATE )
    THEN
        RAISE_APPLICATION_ERROR( -20001,
                                 'Invalid date' );
    END IF;
END;
/

CREATE OR REPLACE TRIGGER patient_dob_check
    BEFORE INSERT OR UPDATE ON patients
    FOR EACH ROW
BEGIN
    IF( :new.dob >= CURRENT_DATE )
    THEN
        RAISE_APPLICATION_ERROR( -20001,
                                 'Invalid date' );
    END IF;
END;
/
