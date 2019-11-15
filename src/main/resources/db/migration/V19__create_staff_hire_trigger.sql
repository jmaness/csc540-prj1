CREATE OR REPLACE TRIGGER staff_hire_date_check
  BEFORE INSERT OR UPDATE ON staff
  FOR EACH ROW
BEGIN
  IF( :new.hire_date >= CURRENT_DATE )
  THEN
    RAISE_APPLICATION_ERROR( -20001,
          'Invalid date' );
  END IF;
END;