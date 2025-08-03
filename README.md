# Information 知识付费平台 (单体应用版)

## 项目简介

Information 是一个现代化的知识付费平台，提供专栏订阅、内容学习、支付结算等完整功能。本项目为单体应用版本，适合中小规模部署，资源占用更少，运维更简单。

## 技术栈

- **后端框架**: Spring Boot 3.2.5
- **数据库**: MySQL 8.0
- **缓存**: Redis 7.x
- **权限认证**: Spring Security + JWT
- **API文档**: SpringDoc OpenAPI 3
- **数据访问**: MyBatis Plus
- **连接池**: Druid
- **支付集成**: 支付宝、微信支付

## 功能特性

### 核心功能
- 🔐 **用户认证**: 注册、登录、JWT令牌、第三方登录
- 👤 **用户管理**: 个人资料、头像上传、密码修改
- 📚 **内容管理**: 专栏创建、章节管理、内容发布  
- 💰 **订阅付费**: 专栏购买、VIP会员、学习记录
- 💳 **支付集成**: 支付宝、微信支付、订单管理
- 📊 **数据统计**: 创作者收益、平台数据分析
- 💬 **互动功能**: 评论、点赞、收藏、分享

### 技术特性
- 🚀 **高性能**: Redis缓存、数据库优化、异步处理
- 🔒 **安全可靠**: Spring Security、参数校验、SQL注入防护
- 📱 **响应式**: 支持Web端、移动端API
- 📖 **API文档**: Swagger UI自动生成文档
- 🐳 **容器化**: Docker部署支持
- 📈 **可监控**: 健康检查、性能指标

## 快速开始

### 环境要求

- Java 17+
- MySQL 8.0+
- Redis 7.x
- Maven 3.6+

### 本地开发

1. **克隆项目**
```bash
git clone git@github.com:wscyg/online-information-single.git
cd online-information-single
```

2. **配置数据库**
```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE information_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入初始化脚本
mysql -u root -p information_platform < src/main/resources/database-init.sql
```

3. **配置Redis**
```bash
# 启动Redis服务
redis-server
```

4. **修改配置文件**
```yaml
# src/main/resources/application-dev.yml
spring:
  datasource:
    username: root
    password: 你的MySQL密码
  redis:
    password: 你的Redis密码(如果有)
```

5. **启动应用**
```bash
# 开发环境启动
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或者使用IDE运行 InformationApplication.java
```

6. **访问应用**
- API文档: http://localhost:8080/swagger-ui.html
- 数据库监控: http://localhost:8080/druid/
- 健康检查: http://localhost:8080/actuator/health

### 生产部署

#### 方式一：JAR包部署

1. **编译打包**
```bash
mvn clean package -DskipTests
```

2. **启动应用**
```bash
java -jar target/information-single-1.0.0.jar --spring.profiles.active=prod
```

#### 方式二：Docker部署

1. **构建镜像**
```bash
# 待添加Dockerfile
docker build -t information-platform .
```

2. **运行容器**
```bash
docker run -d -p 8080:8080 information-platform
```

## 配置说明

### 应用配置

- `application.yml`: 生产环境配置（腾讯云）
- `application-dev.yml`: 开发环境配置（本地）

### 环境切换

```bash
# 开发环境
java -jar app.jar --spring.profiles.active=dev

# 生产环境  
java -jar app.jar --spring.profiles.active=prod
```

## API接口

### 认证相关
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出
- `GET /api/auth/profile` - 获取用户信息

### 内容相关
- `GET /api/content/columns` - 获取专栏列表
- `GET /api/content/columns/{id}` - 获取专栏详情
- `GET /api/content/chapters/{id}` - 获取章节内容
- `POST /api/content/subscribe` - 订阅专栏

### 订单支付
- `POST /api/order/create` - 创建订单
- `GET /api/order/list` - 订单列表
- `POST /api/payment/alipay` - 支付宝支付
- `POST /api/payment/wechat` - 微信支付

完整API文档请访问: http://localhost:8080/swagger-ui.html

## 系统架构

```
┌─────────────────────────────────────┐
│        前端应用 (Vue.js)           │
└─────────────────┬───────────────────┘
                  │ HTTP/HTTPS
┌─────────────────▼───────────────────┐
│         Nginx 反向代理              │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│    Information Platform (Spring)    │
│  ┌─────────┬─────────┬─────────────┐ │
│  │  Auth   │ Content │    User     │ │
│  │ Module  │ Module  │   Module    │ │
│  ├─────────┼─────────┼─────────────┤ │
│  │  Order  │ Payment │   Admin     │ │
│  │ Module  │ Module  │   Module    │ │
│  └─────────┴─────────┴─────────────┘ │
└─────────────────┬───────────────────┘
                  │
    ┌─────────────▼─────────────┐
    │       MySQL 8.0           │
    │   (用户、内容、订单等)     │
    └───────────────────────────┘
    
    ┌─────────────▼─────────────┐
    │       Redis 7.x           │
    │   (缓存、会话、锁等)       │
    └───────────────────────────┘
```

## 性能优化

### 内存使用优化
- 单体应用相比微服务节省40-50%内存
- 推荐配置: 2GB内存可稳定运行
- JVM参数: `-Xms512m -Xmx1024m -XX:+UseG1GC`

### 数据库优化
- 连接池配置优化
- 索引设计优化
- SQL查询优化
- 分页查询优化

### 缓存策略
- Redis缓存热点数据
- 本地缓存Caffeine
- 查询结果缓存
- 会话状态缓存

## 开发指南

### 代码结构
```
src/main/java/com/platform/
├── InformationApplication.java    # 启动类
├── config/                        # 配置类
│   ├── SecurityConfig.java        # 安全配置
│   ├── MyBatisPlusConfig.java     # 数据库配置
│   └── RedisConfig.java          # 缓存配置
├── common/                        # 通用模块
│   ├── entity/                    # 通用实体
│   ├── result/                    # 返回结果
│   └── utils/                     # 工具类
├── auth/                          # 认证模块
├── user/                          # 用户模块
├── content/                       # 内容模块
├── order/                         # 订单模块
├── payment/                       # 支付模块
└── admin/                         # 管理模块
```

### 开发规范
- 使用统一的返回结果格式
- 统一异常处理
- 参数校验注解
- 日志记录规范
- 代码注释规范

## 部署运维

### 系统要求
- **最低配置**: 2核2GB内存，适合小型部署
- **推荐配置**: 4核4GB内存，适合中等规模
- **高性能配置**: 8核8GB内存，支持大并发

### 监控告警
- 应用健康检查: `/actuator/health`
- 性能指标监控: `/actuator/metrics`
- 数据库连接监控: `/druid/`
- 日志文件监控

### 数据备份
```bash
# 数据库备份
mysqldump -u root -p information_platform > backup.sql

# Redis备份
redis-cli --rdb backup.rdb
```

## 常见问题

### 启动问题
1. **端口占用**: 检查8080端口是否被占用
2. **数据库连接**: 确认MySQL服务启动和连接参数
3. **Redis连接**: 确认Redis服务启动和连接参数

### 性能问题
1. **内存不足**: 调整JVM内存参数
2. **数据库慢查询**: 检查索引和SQL优化
3. **缓存命中率低**: 调整缓存策略

### 功能问题
1. **支付异常**: 检查支付配置和证书
2. **文件上传失败**: 检查上传路径权限
3. **邮件发送失败**: 检查SMTP配置

## 贡献指南

1. Fork 本仓库  
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交改动 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系我们

- 项目主页: https://github.com/wscyg/online-information-single
- 问题反馈: https://github.com/wscyg/online-information-single/issues
- 邮箱: support@information-platform.com

---

**Information 知识付费平台** - 让知识更有价值 💡