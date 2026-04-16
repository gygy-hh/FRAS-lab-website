ALTER TABLE advisors ADD COLUMN cv_pdf_url VARCHAR(512) NULL AFTER phone;
ALTER TABLE news ADD COLUMN image_url VARCHAR(512) NULL AFTER published_at;
ALTER TABLE activities ADD COLUMN image_url VARCHAR(512) NULL AFTER location;
ALTER TABLE publications ADD COLUMN image_url VARCHAR(512) NULL AFTER external_url;
