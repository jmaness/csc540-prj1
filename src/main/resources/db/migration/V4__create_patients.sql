CREATE TABLE patients (
    id NUMBER NOT NULL,
    first_name VARCHAR2(255) NOT NULL,
    last_name VARCHAR2(255) NOT NULL,
    dob DATE NOT NULL,
    address_id INT NOT NULL,
    phone VARCHAR2(20) NOT NULL,
    primary key (id)
);

CREATE SEQUENCE patient_sequence;

CREATE OR REPLACE TRIGGER patients_on_insert
    BEFORE INSERT ON patients
    FOR EACH ROW
BEGIN
    SELECT patient_sequence.nextval
    INTO :new.id
    FROM dual;
END;
