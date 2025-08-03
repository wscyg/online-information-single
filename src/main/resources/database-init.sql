-- Information 知识付费平台数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS information_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE information_platform;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别 0:未知 1:男 2:女',
    birthday DATE COMMENT '生日',
    status TINYINT DEFAULT 1 COMMENT '状态 0:禁用 1:正常',
    last_login_time DATETIME COMMENT '最后登录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是'
) COMMENT '用户表';

-- 用户OAuth表
CREATE TABLE IF NOT EXISTS user_oauth (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    provider VARCHAR(20) NOT NULL COMMENT '第三方平台',
    open_id VARCHAR(100) NOT NULL COMMENT '第三方用户ID',
    union_id VARCHAR(100) COMMENT '第三方联合ID',
    access_token TEXT COMMENT '访问令牌',
    refresh_token TEXT COMMENT '刷新令牌',
    expires_in INT COMMENT '过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_provider_openid (provider, open_id),
    KEY idx_user_id (user_id)
) COMMENT '用户OAuth表';

-- 分类表
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    icon VARCHAR(255) COMMENT '图标',
    description VARCHAR(255) COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态 0:禁用 1:启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是',
    KEY idx_parent_id (parent_id)
) COMMENT '分类表';

-- 创作者表
CREATE TABLE IF NOT EXISTS creators (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '创作者ID',
    user_id BIGINT UNIQUE NOT NULL COMMENT '用户ID',
    name VARCHAR(50) NOT NULL COMMENT '创作者名称',
    avatar VARCHAR(255) COMMENT '头像',
    description TEXT COMMENT '个人简介',
    specialties VARCHAR(255) COMMENT '专业领域',
    followers_count INT DEFAULT 0 COMMENT '粉丝数',
    total_income DECIMAL(10,2) DEFAULT 0.00 COMMENT '总收入',
    status TINYINT DEFAULT 1 COMMENT '状态 0:审核中 1:正常 2:封禁',
    apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    approve_time DATETIME COMMENT '审核时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是',
    KEY idx_user_id (user_id),
    KEY idx_status (status)
) COMMENT '创作者表';

-- 专栏表
CREATE TABLE IF NOT EXISTS columns_info (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '专栏ID',
    creator_id BIGINT NOT NULL COMMENT '创作者ID',
    title VARCHAR(100) NOT NULL COMMENT '专栏标题',
    subtitle VARCHAR(200) COMMENT '副标题',
    cover_image VARCHAR(255) COMMENT '封面图片',
    description TEXT COMMENT '专栏描述',
    category_id BIGINT COMMENT '分类ID',
    price DECIMAL(8,2) DEFAULT 0.00 COMMENT '价格',
    original_price DECIMAL(8,2) COMMENT '原价',
    type TINYINT DEFAULT 1 COMMENT '类型 1:付费 2:免费',
    status TINYINT DEFAULT 1 COMMENT '状态 0:草稿 1:发布 2:下架',
    view_count INT DEFAULT 0 COMMENT '查看数',
    subscriber_count INT DEFAULT 0 COMMENT '订阅数',
    chapter_count INT DEFAULT 0 COMMENT '章节数',
    total_duration INT DEFAULT 0 COMMENT '总时长(秒)',
    tags VARCHAR(255) COMMENT '标签',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是',
    KEY idx_creator_id (creator_id),
    KEY idx_category_id (category_id),
    KEY idx_status (status)
) COMMENT '专栏表';

-- 内容表
CREATE TABLE IF NOT EXISTS contents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内容ID',
    column_id BIGINT NOT NULL COMMENT '专栏ID',
    title VARCHAR(100) NOT NULL COMMENT '内容标题',
    content TEXT COMMENT '内容正文',
    content_type TINYINT DEFAULT 1 COMMENT '内容类型 1:图文 2:视频 3:音频',
    media_url VARCHAR(255) COMMENT '媒体文件URL',
    duration INT DEFAULT 0 COMMENT '时长(秒)',
    chapter_order INT NOT NULL COMMENT '章节顺序',
    is_free TINYINT DEFAULT 0 COMMENT '是否免费 0:付费 1:免费',
    view_count INT DEFAULT 0 COMMENT '查看数',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    status TINYINT DEFAULT 1 COMMENT '状态 0:草稿 1:发布',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是',
    KEY idx_column_id (column_id),
    KEY idx_chapter_order (chapter_order),
    KEY idx_status (status)
) COMMENT '内容表';

-- 专栏订阅表
CREATE TABLE IF NOT EXISTS column_subscriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订阅ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    column_id BIGINT NOT NULL COMMENT '专栏ID',
    order_id BIGINT COMMENT '订单ID',
    price DECIMAL(8,2) NOT NULL COMMENT '订阅价格',
    status TINYINT DEFAULT 1 COMMENT '状态 0:已取消 1:有效',
    subscribe_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '订阅时间',
    expire_time DATETIME COMMENT '过期时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_column (user_id, column_id),
    KEY idx_user_id (user_id),
    KEY idx_column_id (column_id),
    KEY idx_order_id (order_id)
) COMMENT '专栏订阅表';

-- 学习记录表
CREATE TABLE IF NOT EXISTS study_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    progress INT DEFAULT 0 COMMENT '学习进度(百分比)',
    study_time INT DEFAULT 0 COMMENT '学习时长(秒)',
    last_position INT DEFAULT 0 COMMENT '最后播放位置(秒)',
    is_completed TINYINT DEFAULT 0 COMMENT '是否完成 0:未完成 1:完成',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_content (user_id, content_id),
    KEY idx_user_id (user_id),
    KEY idx_content_id (content_id)
) COMMENT '学习记录表';

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_no VARCHAR(32) UNIQUE NOT NULL COMMENT '订单号',
    product_type TINYINT NOT NULL COMMENT '产品类型 1:专栏 2:VIP',
    product_id BIGINT COMMENT '产品ID',
    product_name VARCHAR(100) NOT NULL COMMENT '产品名称',
    original_price DECIMAL(8,2) NOT NULL COMMENT '原价',
    discount_price DECIMAL(8,2) DEFAULT 0.00 COMMENT '优惠金额',
    final_price DECIMAL(8,2) NOT NULL COMMENT '实付金额',
    payment_method TINYINT COMMENT '支付方式 1:支付宝 2:微信 3:余额',
    status TINYINT DEFAULT 0 COMMENT '订单状态 0:待支付 1:已支付 2:已取消 3:已退款',
    pay_time DATETIME COMMENT '支付时间',
    cancel_time DATETIME COMMENT '取消时间',
    refund_time DATETIME COMMENT '退款时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_user_id (user_id),
    KEY idx_order_no (order_no),
    KEY idx_status (status)
) COMMENT '订单表';

-- 支付记录表
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '支付ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    transaction_id VARCHAR(64) COMMENT '第三方交易号',
    payment_method TINYINT NOT NULL COMMENT '支付方式 1:支付宝 2:微信 3:余额',
    amount DECIMAL(8,2) NOT NULL COMMENT '支付金额',
    status TINYINT DEFAULT 0 COMMENT '支付状态 0:待支付 1:支付成功 2:支付失败',
    notify_data TEXT COMMENT '回调数据',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_order_id (order_id),
    KEY idx_transaction_id (transaction_id)
) COMMENT '支付记录表';

-- 评论表
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    content TEXT NOT NULL COMMENT '评论内容',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    reply_count INT DEFAULT 0 COMMENT '回复数',
    status TINYINT DEFAULT 1 COMMENT '状态 0:待审核 1:正常 2:已删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除 0:否 1:是',
    KEY idx_user_id (user_id),
    KEY idx_content_id (content_id),
    KEY idx_parent_id (parent_id)
) COMMENT '评论表';

-- 用户行为表
CREATE TABLE IF NOT EXISTS user_actions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '行为ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    action_type TINYINT NOT NULL COMMENT '行为类型 1:查看 2:点赞 3:收藏 4:分享',
    target_type TINYINT NOT NULL COMMENT '目标类型 1:专栏 2:内容',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_user_id (user_id),
    KEY idx_target (target_type, target_id),
    KEY idx_action_type (action_type)
) COMMENT '用户行为表';

-- 插入默认数据

-- 插入管理员用户
INSERT INTO users (username, password, email, nickname, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEIlW', 'admin@information-platform.com', '系统管理员', 1);

-- 插入默认分类
INSERT INTO categories (name, parent_id, sort_order, description, status) VALUES 
('编程技术', 0, 1, '编程开发相关课程', 1),
('人工智能', 0, 2, 'AI、机器学习相关课程', 1),
('数据科学', 0, 3, '数据分析、大数据相关课程', 1),
('产品设计', 0, 4, '产品经理、UI/UX设计课程', 1),
('商业管理', 0, 5, '管理、营销、创业相关课程', 1);

-- 插入子分类
INSERT INTO categories (name, parent_id, sort_order, description, status) VALUES 
('Java开发', 1, 1, 'Java编程语言相关课程', 1),
('Python开发', 1, 2, 'Python编程语言相关课程', 1),
('前端开发', 1, 3, 'HTML、CSS、JavaScript等前端技术', 1),
('机器学习', 2, 1, '机器学习算法和应用', 1),
('深度学习', 2, 2, '神经网络、深度学习框架', 1),
('自然语言处理', 2, 3, 'NLP相关技术和应用', 1);