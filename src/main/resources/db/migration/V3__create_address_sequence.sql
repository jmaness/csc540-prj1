create sequence address_sequence START WITH 100;

CREATE OR REPLACE TRIGGER addresses_on_insert
    BEFORE INSERT ON addresses
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, address_sequence.nextval)
    INTO :new.id
    FROM dual;
END;