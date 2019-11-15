CREATE SEQUENCE patient_checkin_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER patient_checkin_on_insert
    BEFORE INSERT ON patient_checkins
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, patient_checkin_sequence.nextval)
    INTO :new.id
    FROM dual;
END;