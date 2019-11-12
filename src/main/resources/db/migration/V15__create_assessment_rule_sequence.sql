create sequence assessment_rule_sequence;

CREATE OR REPLACE TRIGGER assessment_rules_on_insert
    BEFORE INSERT ON assessment_rules
    FOR EACH ROW
BEGIN
    SELECT assessment_rule_sequence.nextval
    INTO :new.id
    FROM dual;
END;