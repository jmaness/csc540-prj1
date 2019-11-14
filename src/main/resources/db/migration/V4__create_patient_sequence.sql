CREATE SEQUENCE patient_sequence START WITH 2000;

CREATE OR REPLACE TRIGGER patients_on_insert
    BEFORE INSERT ON patients
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, patient_sequence.nextval)
    INTO :new.id
    FROM dual;
END;