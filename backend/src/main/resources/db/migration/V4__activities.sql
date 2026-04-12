CREATE TABLE activities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    slug VARCHAR(120) NOT NULL UNIQUE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NULL,
    location VARCHAR(200) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE activity_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    activity_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    title VARCHAR(300) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    UNIQUE KEY uk_activity_lang (activity_id, lang_code),
    CONSTRAINT fk_activity_i18n_activity
        FOREIGN KEY (activity_id) REFERENCES activities(id) ON DELETE CASCADE
);

INSERT INTO activities (slug, start_time, end_time, location, active)
VALUES ('spring-seminar-2026', '2026-05-15 14:00:00', '2026-05-15 17:00:00', 'FRAS-lab Seminar Room', TRUE),
       ('robot-demo-day-2026', '2026-06-20 10:00:00', '2026-06-20 16:00:00', 'Engineering Building Hall', TRUE);

INSERT INTO activity_i18n (activity_id, lang_code, title, description)
VALUES
    (1, 'zh-CN', '2026春季学术研讨会', '围绕机器人控制与智能系统前沿研究进行专题报告与讨论。'),
    (1, 'en', '2026 Spring Research Seminar', 'A focused seminar on robotic control and intelligent systems research.'),
    (2, 'zh-CN', '机器人系统开放日', '展示实验室多类机器人平台及其控制系统原型。'),
    (2, 'en', 'Robot Demo Day', 'Public demonstration of multiple robotic platforms and control prototypes.');
