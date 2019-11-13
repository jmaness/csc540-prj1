create sequence scale_values_sequence start with 1000;

CREATE OR REPLACE TRIGGER scale_values_on_insert
    BEFORE INSERT ON severity_scale_values
    FOR EACH ROW
BEGIN
    SELECT scale_values_sequence.nextval
    INTO :new.id
    FROM dual;
END;