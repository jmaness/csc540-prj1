CREATE SEQUENCE assessment_rule_sequence START WITH 1000;

CREATE OR REPLACE TRIGGER assessment_rules_on_insert
    BEFORE INSERT ON assessment_rules
    FOR EACH ROW
BEGIN
    SELECT COALESCE(:new.id, assessment_rule_sequence.nextval)
    INTO :new.id
    FROM dual;
END;