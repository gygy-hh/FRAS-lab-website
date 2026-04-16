CREATE TABLE carousel_images (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_url VARCHAR(512) NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO carousel_images (image_url, display_order, active)
VALUES
    ('https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=1400&q=80', 1, TRUE),
    ('https://images.unsplash.com/photo-1581092160607-ee22731f1f65?auto=format&fit=crop&w=1400&q=80', 2, TRUE);
