create table addresses (
    id number not null,
    street_number number not null,
    street varchar2(255) not null,
    city varchar2(255) not null,
    state varchar2(2) not null,
    country varchar2(255) not null,
    primary key (id)
);

create sequence address_sequence;

CREATE OR REPLACE TRIGGER addresses_on_insert
    BEFORE INSERT ON addresses
    FOR EACH ROW
BEGIN
    SELECT address_sequence.nextval
    INTO :new.id
    FROM dual;
END;
