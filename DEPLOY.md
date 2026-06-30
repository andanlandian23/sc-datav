# 部署文档

本文档说明如何将 sc-datav 项目部署到生产环境。

---

## 一、环境要求

| 组件 | 最低版本 | 推荐版本 |
|---|---|---|
| Java | 17 | 17 LTS |
| Maven | 3.6 | 3.9+ |
| MySQL | 8.0 | 8.4 |
| Node.js | 18 | 20 LTS |
| npm/pnpm | 8+ | 10+ |

---

## 二、后端部署

### 1. 配置数据库

```sql
-- 登录 MySQL
mysql -u root -p

-- 执行初始化脚本（会自动创建 sc_datav 数据库和 11 张表）
SOURCE /path/to/database/init.sql;
```

### 2. 修改配置

编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://你的数据库地址:3306/sc_datav?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: 你的用户名
    password: 你的密码

jwt:
  secret: 修改为一个256位以上的随机字符串
  expiration: 86400000  # token有效期（毫秒），默认24小时

server:
  port: 8080  # 可修改端口
```

### 3. 构建 JAR 包

```bash
cd backend
mvn clean package -DskipTests
```

生成 `target/sc-datav-backend-1.0.0.jar`

### 4. 运行

```bash
# 直接运行
java -jar target/sc-datav-backend-1.0.0.jar

# 指定配置文件运行
java -jar target/sc-datav-backend-1.0.0.jar --spring.profiles.active=prod

# 后台运行
nohup java -jar target/sc-datav-backend-1.0.0.jar > app.log 2>&1 &
```

### 5. 生产环境配置（可选）

创建 `application-prod.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://生产数据库地址:3306/sc_datav?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: prod_user
    password: 强密码

mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.nologging.NoLoggingImpl  # 关闭SQL日志

logging:
  level:
    com.scdatav: info
    root: warn

jwt:
  secret: 生产环境必须修改为随机强密码
```

启动时指定：

```bash
java -jar sc-datav-backend-1.0.0.jar --spring.profiles.active=prod
```

---

## 三、前端部署

### 1. 开发模式

```bash
npm install
npm run dev
```

### 2. 生产构建

```bash
# 修改 API 地址（如果后端不在 localhost:8080）
# 创建 .env.production 文件
echo "VITE_API_BASE_URL=http://你的后端地址/api" > .env.production

# 构建
npm run build
```

生成 `dist/` 目录。

### 3. 部署到 Nginx

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 前端静态文件
    location / {
        root /var/www/sc-datav/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    # API 代理到后端
    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # Swagger 文档代理
    location /doc.html {
        proxy_pass http://localhost:8080/doc.html;
    }
    location /webjars/ {
        proxy_pass http://localhost:8080/webjars/;
    }
    location /v3/api-docs {
        proxy_pass http://localhost:8080/v3/api-docs;
    }
}
```

### 4. 部署到 Vercel

前端部分可直接部署到 Vercel：

```bash
# 安装 Vercel CLI
npm i -g vercel

# 部署
vercel --prod
```

在 Vercel 项目设置中添加环境变量：
- `VITE_API_BASE_URL` = `http://你的后端地址/api`

> 注意：Vercel 只托管前端，后端需要单独部署到服务器。

---

## 四、Docker 部署（可选）

### docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name sc-datav-mysql
    environment:
      MYSQL_ROOT_PASSWORD: your_password
      MYSQL_DATABASE: sc_datav
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./database/init.sql:/docker-entrypoint-initdb.d/init.sql
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

  backend:
    build: ./backend
    container_name sc-datav-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/sc_datav?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: your_password
    depends_on:
      - mysql

  nginx:
    image: nginx:alpine
    container_name sc-datav-nginx
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/conf.d/default.conf
      - ./dist:/usr/share/nginx/html
    depends_on:
      - backend

volumes:
  mysql_data:
```

### 后端 Dockerfile（backend/Dockerfile）

```dockerfile
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 启动

```bash
docker-compose up -d
```

---

## 五、常见问题

### 1. 后端启动报错 "Access denied"

检查 MySQL 用户名密码是否正确，确保用户有 `sc_datav` 数据库的访问权限：

```sql
GRANT ALL PRIVILEGES ON sc_datav.* TO 'your_user'@'%';
FLUSH PRIVILEGES;
```

### 2. 前端请求 404

- 检查 `VITE_API_BASE_URL` 环境变量是否正确
- 检查 Nginx 的 `proxy_pass` 配置
- 确保后端已启动并监听正确端口

### 3. 前端请求 CORS 错误

后端已配置全局 CORS 允许，如仍有问题检查：
- Nginx 代理配置是否正确
- 后端 `CorsConfig.java` 中的 `allowedOriginPattern`

### 4. JWT Token 过期

默认 24 小时过期，修改 `application.yml` 中的 `jwt.expiration` 值（毫秒）。

### 5. 数据库中文乱码

确保 MySQL 使用 `utf8mb4` 字符集：

```sql
ALTER DATABASE sc_datav CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## 六、默认账号

| 账号 | 密码 | 角色 |
|---|---|---|
| admin | admin123 | 管理员（ADMIN） |

> 生产环境请立即修改默认密码！
