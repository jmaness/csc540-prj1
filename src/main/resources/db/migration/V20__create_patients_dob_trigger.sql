CREATE OR REPLACE TRIGGER patient_dob_check
  BEFORE INSERT OR UPDATE ON patients
  FOR EACH ROW
BEGIN
  IF( :new.dob >= CURRENT_DATE )
  THEN
    RAISE_APPLICATION_ERROR( -20001,
          'Invalid date' );
  END IF;
END;