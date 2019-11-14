CREATE SEQUENCE symptom_sequence START WITH 100;

CREATE OR REPLACE TRIGGER symptoms_on_insert
    BEFORE INSERT ON symptoms
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.code, 'SYM' || LPAD(symptom_sequence.nextval, 3, '0'))
    INTO :new.code
    FROM dual;
END;