# Blog Backend (Java)

个人博客 Java 后端服务，基于 Spring Boot、Java 和 MySQL 构建，是博客项目的 Java 后端实现。

## 技术栈

Java 17 / Spring Boot 4.1.1 / Maven / MyBatis / MySQL / Spring Security / JWT / WebSocket / 腾讯云 COS / Docker Compose

## 功能

- 博客文章、标签、分类管理
- JWT 认证、邮箱验证码和 GitHub OAuth 登录
- 微信小程序登录与接口
- WebSocket 状态推送
- 文件上传至腾讯云 COS
- DeepSeek AI 集成
- 邮件发送
- SVG 内容安全校验
- 管理端、网站前台和小程序接口

## 快速开始

### 环境要求

- JDK 17
- Maven
- MySQL

先准备数据库和表结构，再配置数据库连接及所需的第三方服务密钥。项目读取 `config/secrets-dev.yml` 作为开发环境的外部配置；可参考 `config/secrets-dev.yaml.example` 创建该文件，并填入本地配置。不要提交任何真实密码或密钥。

```bash
# 进入项目目录
cd blog-back-java

# 启动开发服务（默认使用 dev profile）
mvn spring-boot:run
```

服务默认监听 `3000` 端口，REST API 使用 `/api` 前缀。例如：

```text
GET http://localhost:3000/api/front/blog/getAllBlogs
WebSocket: ws://localhost:3000/ws
```

## 常用命令

```bash
mvn spring-boot:run                 # 启动开发服务
mvn test                            # 运行测试
mvn -DskipTests package             # 跳过测试并打包
java -jar target/blog-back-java-0.0.1-SNAPSHOT.jar  # 运行打包后的应用
```

API 响应格式为 `code`、`message`、`data`。

## 日志

应用日志输出到控制台，采用北京时间时间戳；API 和 WebSocket 请求会记录方法、路径、HTTP 状态码和耗时。请求日志不记录查询参数、请求头或请求体，避免将验证码、令牌等敏感内容写入日志。未处理异常和邮件发送失败会记录异常堆栈；验证码明文不会记录。

Docker Compose 为应用容器配置了 `json-file` 轮转，单个日志文件上限为 10 MB，最多保留 5 个文件。达到保留上限时 Docker 会清除较旧的轮转日志；这不是按天归档。

```bash
docker compose logs --tail=200 --timestamps app  # 查看最近日志
docker compose logs -f --tail=100 app            # 持续查看，Ctrl+C 退出查看
```

## Docker 生产部署

生产环境使用 Docker Compose 运行 Java 应用，并连接服务器上已安装的 MySQL。MySQL 不由本项目的 Compose 启动。

1. 在 `config/secrets-prod.yml` 配置数据库和第三方服务参数。生产 profile 会导入该文件；不要提交或公开它。
2. 确保容器可以访问宿主机 MySQL。容器中的 `127.0.0.1` 指向容器自身；当前 Compose 使用 `host.docker.internal` 指向宿主机。
3. 在项目目录执行：

```bash
docker compose config --quiet
docker compose up -d --build app
docker compose ps
docker compose logs -f --tail=100 app
```

代码有改动时，使用 `docker compose up -d --build app` 重建镜像并更新应用。只修改外部挂载的 `config/secrets-prod.yml` 时，通常重启应用即可：

```bash
docker compose restart app
```

Compose 默认将服务器本机的 `127.0.0.1:3000` 映射到容器 `3000` 端口，适合由 Nginx 反向代理。若需临时使用其他服务器端口，只改 `compose.yaml` 端口映射左侧，然后运行 `docker compose up -d app` 应用配置变更。

HTTPS 网站的 WebSocket 地址使用 `wss://你的域名/ws`。Nginx 需要为 `/ws` 转发 HTTP/1.1 Upgrade 请求到 Java 应用端口。浏览器来源域名也必须加入 `SecurityConfig` 的 CORS 允许来源列表；修改 Java 源码后需要重新构建镜像。

## 配置说明

- `src/main/resources/application.yml` — 应用通用设置，默认启用 `dev` profile
- `src/main/resources/application-dev.yml` — 开发环境设置
- `src/main/resources/application-prod.yml` — 生产环境 profile 设置及外部密钥文件导入
- `config/secrets-dev.yml` — 本地开发密钥和连接配置（不提交）
- `config/secrets-prod.yml` — 生产数据库及第三方服务配置（不提交）

生产配置还通过 Docker Compose 将 JVM 时区设为 `Asia/Shanghai`。请确保 Java 应用和 MySQL 的时间设置符合项目使用的时区。

## License

本项目为个人博客项目，仅供学习参考。
