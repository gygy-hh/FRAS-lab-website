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

## 下一步（Step 2）

- 接入 MySQL 与 Flyway，建立核心数据表：
  - users / roles / permissions / user_roles
  - advisors / members / research_areas / news（含 i18n 子表）
- 实现后台登录（JWT）与 RBAC 权限控制。
