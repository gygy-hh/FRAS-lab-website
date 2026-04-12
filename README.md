# FRAS-lab Website (Java + Vue)

这是 FRAS-lab 实验室官网的首期工程骨架，采用前后端分离架构：

- 后端：Spring Boot 3（`backend`）
- 前端：Vue 3 + TypeScript + vue-i18n（`frontend`）

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
    - `GET/POST/PUT/DELETE /api/v1/admin/research-areas`
    - `GET/POST/PUT/DELETE /api/v1/admin/news`
  - 前台接口新增：`GET /api/v1/public/news`
- 前端新增：
  - 路由：`/`、`/admin/login`、`/admin`
  - 管理端登录页与基础管理页（研究方向/新闻）

### 默认管理员账号

- 用户名：`admin`
- 密码：`Admin@123456`

说明：该默认密码来自 Flyway 初始化数据，仅用于首期部署验证，上线后请立即修改。
