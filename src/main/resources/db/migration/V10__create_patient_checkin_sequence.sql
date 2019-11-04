create sequence patient_checkin_sequence;

CREATE OR REPLACE TRIGGER patient_checkin_on_insert
    BEFORE INSERT ON PATIENT_CHECKIN
    FOR EACH ROW
BEGIN
    SELECT patient_checkin_sequence.nextval
    INTO :new.id
    FROM dual;
END;