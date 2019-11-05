create sequence staff_sequence;

CREATE OR REPLACE TRIGGER staff_on_insert
    BEFORE INSERT ON staff
    FOR EACH ROW
BEGIN
    SELECT staff_sequence.nextval
    INTO :new.id
    FROM dual;
END;
