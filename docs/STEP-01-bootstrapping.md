# Step 1 - 项目初始化与双语首页骨架

## 目标

搭建可运行的 FRAS-lab 官网前后端基础工程，并完成中英文首页基础展示。

## 交付内容

1. 后端 Spring Boot 工程初始化。
2. 对外公开接口：
   - `GET /api/v1/public/profile`
   - `GET /api/v1/public/research-areas`
3. 前端 Vue 工程初始化，接入 `vue-i18n` 与接口请求。
4. 首页基础版 UI（顶部导航、Banner、研究方向、联系方式）。

## 验证记录

- 后端：`mvn test` 通过。
- 前端：`npm run build` 通过。

## 备注

此步骤为首期骨架，后续将进入数据库建模、后台登录和权限体系开发。
