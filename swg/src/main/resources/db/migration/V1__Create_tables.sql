-- Create tables

CREATE TABLE projects (
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_name        VARCHAR(255) NOT NULL
);

CREATE TABLE languages(
  id                  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  language_code       VARCHAR(32) NOT NULL UNIQUE,
  language_name       VARCHAR(255) NOT NULL
);

CREATE TABLE project_languages(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT,
  language_id         BIGINT,
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
  FOREIGN KEY (language_id) REFERENCES languages(id) ON DELETE CASCADE
);

CREATE VIEW project_languages_view AS
SELECT 
  project_languages.id AS id,
  project_id,
  language_id,
  language_code,
  language_name
FROM project_languages, languages
WHERE  project_languages.language_id = languages.id;

CREATE TABLE siteinfo(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT,
  project_language_id BIGINT,
  title               VARCHAR(255),
  menutitle           VARCHAR(255),
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  FOREIGN KEY (project_language_id) REFERENCES project_languages(id) ON DELETE CASCADE,
  UNIQUE(project_id, project_language_id)
);

CREATE VIEW siteinfo_view AS
SELECT 
  siteinfo.id AS id,
  siteinfo.project_id AS project_id,
  project_language_id,
  title,
  menutitle,
  project_name,
  language_code,
  language_name
FROM siteinfo, projects, project_languages_view
WHERE projects.id=siteinfo.project_id AND project_language_id=project_languages_view.id;

CREATE TABLE translations(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT,
  project_language_id BIGINT,
  translation_name    VARCHAR(255) NOT NULL,
  translation         VARCHAR(255),
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  FOREIGN KEY (project_language_id) REFERENCES project_languages(id) ON DELETE CASCADE,
  UNIQUE(project_id, project_language_id, translation_name)
);

CREATE VIEW translations_view AS
SELECT 
  translations.id AS id,
  translations.project_id AS project_id,
  project_language_id,
  translation_name,
  translation,
  language_code,
  language_name
FROM translations, project_languages_view
WHERE project_language_id=project_languages_view.id;

CREATE TABLE articles(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT,
  project_language_id BIGINT,
  article_name        VARCHAR(255) NOT NULL,
  article_title       VARCHAR(255),
  article_text        CLOB,
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  FOREIGN KEY (project_language_id) REFERENCES project_languages(id) ON DELETE CASCADE,
  UNIQUE(project_id, project_language_id, article_name)
);

CREATE VIEW articles_view AS
SELECT 
  articles.id AS id,
  articles.project_id AS project_id,
  project_language_id,
  article_name,
  article_title,
  article_text,
  language_code,
  language_name
FROM articles, project_languages_view
WHERE project_language_id=project_languages_view.id;

CREATE TABLE images(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT,
  image_id            INT,
  image_filename      VARCHAR(255),
  original_image      BLOB,
  big_image           BLOB,
  thumbnail_image     BLOB,  
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  UNIQUE(project_id, image_id)
);

-- Create default project settings
INSERT INTO projects (project_name) VALUES ('default'); 
INSERT INTO languages (language_code,language_name) VALUES ('en','English'); 
INSERT INTO languages (language_code,language_name) VALUES ('sk','Slovensky'); 
INSERT INTO languages (language_code,language_name) VALUES ('de','Deutsch');
INSERT INTO project_languages (project_id,language_id) VALUES (1,1);
INSERT INTO siteinfo (project_id,project_language_id,title,menutitle) VALUES (1,1,'Sample Project','Menu');
