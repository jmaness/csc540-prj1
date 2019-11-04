create sequence address_sequence;

CREATE OR REPLACE TRIGGER addresses_on_insert
    BEFORE INSERT ON addresses
    FOR EACH ROW
BEGIN
    SELECT address_sequence.nextval
    INTO :new.id
    FROM dual;
END;