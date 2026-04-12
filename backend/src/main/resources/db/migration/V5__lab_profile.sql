CREATE TABLE lab_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name_zh VARCHAR(200) NOT NULL,
    name_en VARCHAR(200) NOT NULL,
    slogan_zh VARCHAR(500) NOT NULL,
    slogan_en VARCHAR(500) NOT NULL,
    email VARCHAR(120) NOT NULL,
    phone VARCHAR(64) NOT NULL,
    address_zh VARCHAR(500) NOT NULL,
    address_en VARCHAR(500) NOT NULL,
    map_link VARCHAR(512) NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO lab_profile (
    id, name_zh, name_en, slogan_zh, slogan_en, email, phone, address_zh, address_en, map_link
) VALUES (
    1,
    'FRAS-lab 智能与先进控制实验室',
    'FRAS-lab for Robotics and Automation Systems',
    '面向机器人与智能系统的先进控制研究',
    'Advanced control research for robotic and intelligent systems',
    'contact@fras-lab.example',
    '+86-000-00000000',
    '某某市某某大学某某楼',
    'Building XX, Example University, Example City',
    'https://maps.google.com'
);
