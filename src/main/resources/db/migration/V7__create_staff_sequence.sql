CREATE SEQUENCE staff_sequence START WITH 100000;

CREATE OR REPLACE TRIGGER staff_on_insert
    BEFORE INSERT ON staff
    FOR EACH ROW
BEGIN
	SELECT COALESCE(:new.id, staff_sequence.nextval)
    INTO :new.id
    FROM dual;
END;