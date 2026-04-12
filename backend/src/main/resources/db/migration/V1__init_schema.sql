CREATE TABLE admin_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE research_areas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    area_key VARCHAR(64) NOT NULL UNIQUE,
    display_order INT NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE research_area_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    research_area_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    name VARCHAR(200) NOT NULL,
    summary VARCHAR(1000) NOT NULL,
    UNIQUE KEY uk_research_area_lang (research_area_id, lang_code),
    CONSTRAINT fk_research_area_i18n_area
        FOREIGN KEY (research_area_id) REFERENCES research_areas(id) ON DELETE CASCADE
);

CREATE TABLE news (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    slug VARCHAR(120) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    published_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE news_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    news_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    title VARCHAR(250) NOT NULL,
    content TEXT NOT NULL,
    UNIQUE KEY uk_news_lang (news_id, lang_code),
    CONSTRAINT fk_news_i18n_news
        FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE
);

CREATE TABLE advisors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    advisor_code VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(64) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE advisor_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    advisor_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    full_name VARCHAR(120) NOT NULL,
    title VARCHAR(120) NOT NULL,
    research_summary VARCHAR(1200) NOT NULL,
    UNIQUE KEY uk_advisor_lang (advisor_id, lang_code),
    CONSTRAINT fk_advisor_i18n_advisor
        FOREIGN KEY (advisor_id) REFERENCES advisors(id) ON DELETE CASCADE
);

CREATE TABLE members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_code VARCHAR(64) NOT NULL UNIQUE,
    role_type VARCHAR(64) NOT NULL,
    email VARCHAR(120) NULL,
    enrollment_year INT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE member_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    full_name VARCHAR(120) NOT NULL,
    bio VARCHAR(1200) NOT NULL,
    UNIQUE KEY uk_member_lang (member_id, lang_code),
    CONSTRAINT fk_member_i18n_member
        FOREIGN KEY (member_id) REFERENCES members(id) ON DELETE CASCADE
);

INSERT INTO admin_users (username, password, enabled)
VALUES ('admin', '{noop}Admin@123456', TRUE);

INSERT INTO research_areas (area_key, display_order, active)
VALUES ('vision', 1, TRUE),
       ('rehab_robotics', 2, TRUE),
       ('multi_uav', 3, TRUE);

INSERT INTO research_area_i18n (research_area_id, lang_code, name, summary)
VALUES
    (1, 'zh-CN', '影像与视觉', '面向机器人视觉感知、目标识别与追踪的核心算法研究。'),
    (1, 'en', 'Vision', 'Research on robot visual perception, object detection and tracking algorithms.'),
    (2, 'zh-CN', '复健机器人', '面向复健场景的人机协作、控制与系统优化研究。'),
    (2, 'en', 'Rehabilitation Robotics', 'Research on human-robot collaboration and control for rehabilitation scenarios.'),
    (3, 'zh-CN', '多无人机系统控制', '聚焦多无人机协同控制、任务分配与稳定性分析。'),
    (3, 'en', 'Multi-UAV Control', 'Focus on cooperative control, task allocation and stability analysis for multi-UAV systems.');
