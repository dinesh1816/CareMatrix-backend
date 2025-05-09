-- First, update existing records to have a default creation date
UPDATE insurance_information SET created_at = NOW() WHERE created_at IS NULL;
 
-- Then, make the column non-nullable
ALTER TABLE insurance_information MODIFY COLUMN created_at datetime(6) NOT NULL; 