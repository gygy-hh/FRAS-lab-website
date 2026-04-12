INSERT INTO advisors (advisor_code, email, phone, active)
VALUES ('li_chen_fu', 'lcfu@ncu.edu.tw', '+886-2-2362-2209', TRUE)
ON DUPLICATE KEY UPDATE email = VALUES(email), phone = VALUES(phone), active = VALUES(active);

INSERT INTO advisor_i18n (advisor_id, lang_code, full_name, title, research_summary)
SELECT id, 'zh-CN', '傅立成', '教授', '嵌入式控制、适应性控制、机器视觉、机电整合与智慧系统。'
FROM advisors WHERE advisor_code = 'li_chen_fu'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), title = VALUES(title), research_summary = VALUES(research_summary);

INSERT INTO advisor_i18n (advisor_id, lang_code, full_name, title, research_summary)
SELECT id, 'en', 'Li-Chen Fu', 'Professor', 'Embedded control, adaptive control, computer vision and intelligent mechatronic systems.'
FROM advisors WHERE advisor_code = 'li_chen_fu'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), title = VALUES(title), research_summary = VALUES(research_summary);

INSERT INTO members (member_code, role_type, email, enrollment_year, active)
VALUES ('zhang_san', 'PhD', 'zhangsan@fras-lab.example', 2024, TRUE),
       ('li_si', 'Master', 'lisi@fras-lab.example', 2025, TRUE)
ON DUPLICATE KEY UPDATE role_type = VALUES(role_type), email = VALUES(email), enrollment_year = VALUES(enrollment_year), active = VALUES(active);

INSERT INTO member_i18n (member_id, lang_code, full_name, bio)
SELECT id, 'zh-CN', '张三', '研究方向：多智能体协同控制与强化学习。'
FROM members WHERE member_code = 'zhang_san'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), bio = VALUES(bio);

INSERT INTO member_i18n (member_id, lang_code, full_name, bio)
SELECT id, 'en', 'Zhang San', 'Research focus: multi-agent cooperative control and reinforcement learning.'
FROM members WHERE member_code = 'zhang_san'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), bio = VALUES(bio);

INSERT INTO member_i18n (member_id, lang_code, full_name, bio)
SELECT id, 'zh-CN', '李四', '研究方向：机器人视觉感知与轨迹规划。'
FROM members WHERE member_code = 'li_si'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), bio = VALUES(bio);

INSERT INTO member_i18n (member_id, lang_code, full_name, bio)
SELECT id, 'en', 'Li Si', 'Research focus: robotic visual perception and trajectory planning.'
FROM members WHERE member_code = 'li_si'
ON DUPLICATE KEY UPDATE full_name = VALUES(full_name), bio = VALUES(bio);
