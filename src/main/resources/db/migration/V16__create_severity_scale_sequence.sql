create sequence severity_scales_sequence start with 1000;

CREATE OR REPLACE TRIGGER severity_scales_on_insert
    BEFORE INSERT ON severity_scales
    FOR EACH ROW
BEGIN
    SELECT severity_scales_sequence.nextval
    INTO :new.id
    FROM dual;
END;