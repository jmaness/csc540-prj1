create sequence facility_sequence START WITH 2000;

CREATE OR REPLACE TRIGGER facilities_on_insert
    BEFORE INSERT ON facilities
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, facility_sequence.nextval)
    INTO :new.id
    FROM dual;
END;