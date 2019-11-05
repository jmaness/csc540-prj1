create sequence patient_sequence;

CREATE OR REPLACE TRIGGER patients_on_insert
    BEFORE INSERT ON patients
    FOR EACH ROW
BEGIN
    SELECT patient_sequence.nextval
    INTO :new.id
    FROM dual;
END;