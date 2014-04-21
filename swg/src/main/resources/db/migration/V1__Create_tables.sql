-- Create tables
CREATE TABLE languages(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  language_code       VARCHAR(32) NOT NULL UNIQUE,
  language_name       VARCHAR(255) NOT NULL
);

CREATE TABLE projects (
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_name        VARCHAR(255) NOT NULL,
  thumbnail_width     INT,
  thumbnail_height    INT,
  image_width         INT,
  image_height        INT,
  default_language_id BIGINT,  
);

CREATE TABLE project_languages(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  language_id         BIGINT NOT NULL,
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
  project_id          BIGINT NOT NULL,
  project_language_id BIGINT NOT NULL,
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
  project_id          BIGINT NOT NULL,
  project_language_id BIGINT NOT NULL,
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

CREATE TABLE menu(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  menu_number         INT,
  menu_level          INT,
  article_number      INT,
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  UNIQUE(project_id, menu_number)
);

CREATE TABLE articles(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  article_number      INT NOT NULL,
  publish             BOOLEAN NOT NULL,
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  UNIQUE(project_id, article_number)
);

CREATE TABLE article_texts(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  project_language_id BIGINT NOT NULL,
  article_number      INT NOT NULL,
  article_link_title  VARCHAR(255),
  article_title       VARCHAR(255),
  article_text        CLOB,
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  FOREIGN KEY (project_language_id) REFERENCES project_languages(id) ON DELETE CASCADE,
  UNIQUE(project_id, project_language_id, article_number)
);

CREATE VIEW articles_view AS
SELECT 
  article_texts.id AS id,
  articles.id AS article_id,
  articles.project_id AS project_id,
  project_language_id,
  articles.article_number AS article_number,
  article_link_title,
  article_title,
  article_text,
  publish,
  language_code,
  language_name
FROM articles, article_texts, project_languages_view
WHERE (
  project_language_id=project_languages_view.id AND
  articles.project_id=article_texts.project_id AND
  articles.article_number=article_texts.article_number
);

CREATE TABLE techniques(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  technique_key       VARCHAR(255) NOT NULL,
  UNIQUE(technique_key)
);

CREATE TABLE technique_translations(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  language_id         BIGINT NOT NULL,
  technique_id        BIGINT NOT NULL,
  technique_name      VARCHAR(255),
  FOREIGN KEY (technique_id) REFERENCES techniques(id) ON DELETE CASCADE,  
  FOREIGN KEY (language_id) REFERENCES languages(id) ON DELETE CASCADE,
  UNIQUE(language_id,technique_id)
);

CREATE VIEW techniques_view AS
SELECT 
  technique_translations.id AS id,
  technique_translations.language_id AS language_id,
  technique_translations.technique_id AS technique_id,
  technique_translations.technique_name AS technique_name,
  technique_key,
  language_code,
  language_name
FROM technique_translations, languages, techniques
WHERE (technique_translations.language_id=languages.id AND techniques.id = technique_translations.technique_id);

CREATE TABLE images(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  image_number        INT NOT NULL,
  source_url          VARCHAR(255),
  relative_path       VARCHAR(255),
  technique_id        BIGINT,
  author              VARCHAR(255) NOT NULL DEFAULT '',
  inception           VARCHAR(255) NOT NULL DEFAULT '',
  width               REAL NOT NULL DEFAULT 0.0,
  height              REAL NOT NULL DEFAULT 0.0,
  original_image      BLOB,
  big_image           BLOB,
  big_image_format    VARCHAR(32),
  thumbnail_image     BLOB,  
  thumbnail_format    VARCHAR(32),
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  UNIQUE(project_id, image_number)
);

CREATE TABLE image_translations(
  id                  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  project_id          BIGINT NOT NULL,
  project_language_id BIGINT NOT NULL,
  image_id            BIGINT NOT NULL,
  image_title         VARCHAR(255),
  FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,  
  FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE CASCADE,  
  FOREIGN KEY (project_language_id) REFERENCES project_languages(id) ON DELETE CASCADE,
  UNIQUE(project_id, project_language_id, image_id)
);

CREATE VIEW images_view AS
SELECT 
  image_translations.id AS id,
  image_translations.project_id AS project_id,
  project_language_id,
  image_id,
  image_title,
  language_code,
  language_name
FROM image_translations, project_languages_view
WHERE (project_language_id=project_languages_view.id);

-- Create default project settings
INSERT INTO languages (id,language_code,language_name) VALUES (1,'en','English'); 
INSERT INTO languages (id,language_code,language_name) VALUES (2,'sk','Slovensky'); 
INSERT INTO languages (id,language_code,language_name) VALUES (3,'de','Deutsch');

INSERT INTO techniques (id,technique_key) VALUES (1,'computer graphics');
INSERT INTO techniques (id,technique_key) VALUES (2,'oil painting');
INSERT INTO techniques (id,technique_key) VALUES (3,'pastel');

INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (1,1, 'computer graphics');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (2,1, 'počítačová grafika');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (3,1, 'Computergrafik');

INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (1,2, 'oil painting');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (2,2, 'olejomaľba');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (3,2, 'Ölgemälde');

INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (1,3, 'pastel');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (2,3, 'pastel');
INSERT INTO technique_translations (language_id,technique_id,technique_name) VALUES (3,3, 'Pastell');

INSERT INTO projects (project_name,default_language_id,thumbnail_width,thumbnail_height,image_width,image_height)
  VALUES ('default',1,180,180,900,620); 
INSERT INTO project_languages (project_id,language_id) VALUES (1,1);
INSERT INTO siteinfo (project_id,project_language_id,title,menutitle) VALUES (1,1,'Sample Project','Menu');
