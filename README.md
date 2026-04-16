# FRAS-lab Website (Java + Vue)

FRAS-lab 官网项目，采用前后端分离架构，覆盖实验室公开信息展示与后台内容管理能力。

- 后端：Spring Boot 3（`backend`）
- 前端：Vue 3 + TypeScript + vue-i18n（`frontend`）

## 项目简介

本项目用于支撑实验室官方网站建设，当前版本已提供：

- 前台公开展示：实验室简介、研究方向、导师、成员、科研成果、新闻、活动
- 后台管理维护：管理员登录、实验室资料维护、各模块内容增删改查
- 数据层能力：MySQL + Flyway 数据迁移与初始化

详细版本说明见：`docs/CURRENT-VERSION.md`

## 已完成（Step 1）

- 初始化后端 Spring Boot 工程与基础 API：
  - `GET /api/v1/public/profile`
  - `GET /api/v1/public/research-areas`
- 初始化前端 Vue 工程并完成中英双语切换。
- 实现首页导航、Banner、研究方向、联系方式展示。
- 前后端本地构建验证通过。

## 本地运行

### 1) 启动后端

```bash
cd backend
mvn spring-boot:run
```

默认地址：`http://localhost:8080`

说明：项目已在 `backend/.mvn/maven.config` 固化本地仓库到 `D:/maven/repo`，用于规避 Windows 中文用户名路径下 `spring-boot:run` 的类路径问题。

### 2) 启动前端

```bash
cd frontend
npm install
npm run dev
```

默认地址：`http://localhost:5173`

## 目录结构

```text
backend/   Spring Boot API
frontend/  Vue3 网站前台 + i18n
docs/      过程文档与后续设计
```

## Step 2 已完成（MySQL + JWT + 管理端）

- 后端新增：
  - MySQL + Flyway 自动建表（`db/migration/V1__init_schema.sql`）
  - JWT 登录接口：`POST /api/v1/auth/login`
  - 管理接口：
    - `GET/PUT /api/v1/admin/lab-profile`
    - `GET/POST/PUT/DELETE /api/v1/admin/research-areas`
    - `GET/POST/PUT/DELETE /api/v1/admin/news`
    - `GET/POST/PUT/DELETE /api/v1/admin/advisors`
    - `GET/POST/PUT/DELETE /api/v1/admin/members`
    - `GET/POST/PUT/DELETE /api/v1/admin/publications`
    - `GET/POST/PUT/DELETE /api/v1/admin/activities`
  - 前台接口新增：`GET /api/v1/public/profile`（改为数据库读取）
  - 前台接口新增：`GET /api/v1/public/news`
  - 前台接口新增：`GET /api/v1/public/advisors`、`GET /api/v1/public/members`
  - 前台接口新增：`GET /api/v1/public/publications`
  - 前台接口新增：`GET /api/v1/public/activities`
- 前端新增：
  - 路由：`/`、`/admin/login`、`/admin`
  - 管理端登录页与基础管理页（研究方向/成果/新闻/活动/导师/成员）

### 默认管理员账号

- 用户名：`admin`
- 密码：`Admin@123456`

说明：该默认密码来自 Flyway 初始化数据，仅用于首期部署验证，上线后请立即修改。
