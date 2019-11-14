CREATE SEQUENCE scale_values_sequence START WITH 1001;

CREATE OR REPLACE TRIGGER scale_values_on_insert
    BEFORE INSERT ON severity_scale_values
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, scale_values_sequence.nextval)
    INTO :new.id
    FROM dual;
END;