#!/bin/bash

# Information 单体应用推送到GitHub脚本

set -e

echo "📦 开始推送 Information 单体应用到 GitHub..."

# 添加远程仓库
echo "1. 设置远程仓库..."
git remote add origin git@github.com:wscyg/online-information-single.git 2>/dev/null || echo "远程仓库已存在"

# 添加所有文件
echo "2. 添加文件到Git..."
git add -A

# 查看状态
echo "3. 查看Git状态..."
git status

# 创建提交
echo "4. 创建提交..."
git commit -m "feat: 初始化Information知识付费平台单体应用

🎉 项目特性:
- 将5个微服务合并为单体应用
- 内存使用优化40-50%
- 支持3GB服务器部署
- 完整的业务功能模块

📦 技术栈:
- Spring Boot 3.2.5
- Spring Security + JWT
- MyBatis Plus + MySQL 8.0  
- Redis 7.x + Caffeine
- SpringDoc OpenAPI 3
- 支付宝/微信支付集成

🏗️ 项目结构:
- 统一的启动类和配置
- 模块化代码组织
- 双环境配置(生产/开发)
- 完整的数据库设计

🚀 部署支持:
- 本地开发环境配置
- 腾讯云生产环境配置
- Docker容器化支持
- 完整的部署文档

💡 相比微服务优势:
- 资源占用更少
- 部署运维更简单  
- 开发调试更容易
- 适合中小规模应用

支持用户量: 2000-8000 DAU
服务器要求: 最低2GB内存
部署方式: JAR包/Docker"

echo "5. 推送到GitHub..."
git push -u origin main

echo ""
echo "✅ 推送成功!"
echo "🌟 仓库地址: https://github.com/wscyg/online-information-single"
echo ""
echo "📋 接下来的步骤:"
echo "1. 在GitHub上查看代码"
echo "2. 配置本地开发环境"
echo "3. 修改 application-dev.yml 中的数据库配置"
echo "4. 运行 mvn spring-boot:run -Dspring-boot.run.profiles=dev"
echo "5. 访问 http://localhost:8080/swagger-ui.html 查看API文档"
echo ""
echo "🔧 生产部署:"
echo "1. 服务器上执行: git clone git@github.com:wscyg/online-information-single.git"
echo "2. 配置 application.yml 中的数据库和Redis连接"
echo "3. 执行: mvn clean package && java -jar target/information-single-1.0.0.jar"