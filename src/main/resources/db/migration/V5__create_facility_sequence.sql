create sequence facility_sequence;

CREATE OR REPLACE TRIGGER facilities_on_insert
    BEFORE INSERT ON facilities
    FOR EACH ROW
BEGIN
    SELECT facility_sequence.nextval
    INTO :new.id
    FROM dual;
END;