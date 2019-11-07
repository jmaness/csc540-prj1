create sequence symptom_sequence;

CREATE OR REPLACE TRIGGER symptoms_on_insert
    BEFORE INSERT ON symptoms
    FOR EACH ROW
BEGIN
    SELECT CONCAT('SYM', symptom_sequence.nextval)
    INTO :new.code
    FROM dual;
END;