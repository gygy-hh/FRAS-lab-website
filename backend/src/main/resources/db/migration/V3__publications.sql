CREATE TABLE publications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    slug VARCHAR(120) NOT NULL UNIQUE,
    pub_year INT NOT NULL,
    pub_date DATE NULL,
    category VARCHAR(32) NOT NULL,
    external_url VARCHAR(512) NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE publication_i18n (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    publication_id BIGINT NOT NULL,
    lang_code VARCHAR(16) NOT NULL,
    title VARCHAR(300) NOT NULL,
    authors VARCHAR(2000) NOT NULL,
    abstract_text VARCHAR(3000) NOT NULL,
    UNIQUE KEY uk_publication_lang (publication_id, lang_code),
    CONSTRAINT fk_publication_i18n_publication
        FOREIGN KEY (publication_id) REFERENCES publications(id) ON DELETE CASCADE
);

INSERT INTO publications (slug, pub_year, pub_date, category, external_url, active)
VALUES ('adaptive-control-2025', 2025, '2025-11-01', 'Journal', 'https://example.org/papers/adaptive-control-2025', TRUE),
       ('multi-uav-2024', 2024, '2024-08-15', 'Conference', 'https://example.org/papers/multi-uav-2024', TRUE);

INSERT INTO publication_i18n (publication_id, lang_code, title, authors, abstract_text)
VALUES
    (1, 'zh-CN', '机器人系统自适应控制方法研究', '傅立成, 张三', '提出一种面向复杂机器人系统的自适应控制框架，并在多场景下验证稳定性与鲁棒性。'),
    (1, 'en', 'Adaptive Control for Robotic Systems', 'Li-Chen Fu, Zhang San', 'This work proposes an adaptive control framework for complex robotic systems with validated robustness and stability.'),
    (2, 'zh-CN', '多无人机协同控制与任务分配', '李四, 张三', '研究多无人机协同任务中的控制策略与资源分配机制，提升任务执行效率。'),
    (2, 'en', 'Cooperative Control and Task Allocation for Multi-UAV Systems', 'Li Si, Zhang San', 'This paper studies cooperative control and resource allocation in multi-UAV systems to improve mission efficiency.');
