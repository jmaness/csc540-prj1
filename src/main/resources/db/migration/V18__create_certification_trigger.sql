CREATE OR REPLACE TRIGGER certification_date_check
  BEFORE INSERT OR UPDATE ON facility_certifications
  FOR EACH ROW
BEGIN
  IF( :new.certification_date >= CURRENT_DATE )
  THEN
    RAISE_APPLICATION_ERROR( -20001,
          'Invalid date' );
  END IF;
END;