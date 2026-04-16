# FRAS-lab Website 当前版本说明

## 版本定位

当前版本已完成实验室官网的核心可用能力，包含：

- 前台公开展示（实验室简介、研究方向、导师、成员、成果、新闻、活动）
- 后台管理登录（JWT）
- 后台内容管理（实验室信息与各内容模块 CRUD）
- 数据库初始化与种子数据（Flyway）

## 技术栈

- 后端：Spring Boot 3、Spring Security、Spring Data JPA、Flyway、MySQL
- 前端：Vue 3、TypeScript、Vue Router、vue-i18n、Axios
- 鉴权：JWT

## 已实现功能

### 1. 公开接口（前台）

- `GET /api/v1/public/profile`
- `GET /api/v1/public/research-areas`
- `GET /api/v1/public/advisors`
- `GET /api/v1/public/members`
- `GET /api/v1/public/publications`
- `GET /api/v1/public/news`
- `GET /api/v1/public/activities`

### 2. 管理接口（后台）

- 登录接口：`POST /api/v1/auth/login`
- 实验室信息：`GET/PUT /api/v1/admin/lab-profile`
- 研究方向：`GET/POST/PUT/DELETE /api/v1/admin/research-areas`
- 导师管理：`GET/POST/PUT/DELETE /api/v1/admin/advisors`
- 成员管理：`GET/POST/PUT/DELETE /api/v1/admin/members`
- 科研成果：`GET/POST/PUT/DELETE /api/v1/admin/publications`
- 新闻管理：`GET/POST/PUT/DELETE /api/v1/admin/news`
- 活动管理：`GET/POST/PUT/DELETE /api/v1/admin/activities`

### 3. 前端页面与路由

- 前台首页：`/`
- 管理登录页：`/admin/login`
- 管理控制台：`/admin`

## 默认账号（仅开发环境）

- 用户名：`admin`
- 密码：`Admin@123456`

> 该账号仅用于初始化验证，部署生产环境前请务必更改默认密码。

## 数据库迁移

当前包含以下迁移脚本：

- `V1__init_schema.sql`
- `V2__seed_advisors_members.sql`
- `V3__publications.sql`
- `V4__activities.sql`
- `V5__lab_profile.sql`

## 本地运行

### 后端

```bash
cd backend
mvn spring-boot:run
```

默认地址：`http://localhost:8080`

### 前端

```bash
cd frontend
npm install
npm run dev
```

默认地址：`http://localhost:5173`

## 后续建议

- 增加上传附件与图片管理
- 增加管理端编辑器（Markdown 或富文本）
- 增加操作日志与审计
- 增加接口级自动化测试与 E2E 测试
