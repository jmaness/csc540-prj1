create sequence symptom_sequence;

CREATE OR REPLACE TRIGGER symptoms_on_insert
    BEFORE INSERT ON symptoms
    FOR EACH ROW
BEGIN
    SELECT CONCAT('SYM' || LPAD(symptom_sequence.nextval, 3, '0'))
    INTO :new.code
    FROM dual;
END;