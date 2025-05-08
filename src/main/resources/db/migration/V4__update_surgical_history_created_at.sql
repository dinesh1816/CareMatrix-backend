-- First, update existing records to have a default creation date
UPDATE surgical_history SET created_at = NOW() WHERE created_at IS NULL;

-- Then, make the column non-nullable
ALTER TABLE surgical_history MODIFY COLUMN created_at datetime(6) NOT NULL; 