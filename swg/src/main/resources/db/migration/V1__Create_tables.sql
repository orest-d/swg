-- Create tables

CREATE TABLE projects (
  id    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(255) NOT NULL
);

-- Create default project
INSERT INTO projects SET name='default'; 
