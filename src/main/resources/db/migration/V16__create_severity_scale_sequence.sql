CREATE SEQUENCE severity_scales_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER severity_scales_on_insert
    BEFORE INSERT ON severity_scales
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, severity_scales_sequence.nextval)
    INTO :new.id
    FROM dual;
END;