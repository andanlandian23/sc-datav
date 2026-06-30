-- =============================================
-- sc-datav 数据库初始化脚本
-- 数据库: sc_datav
-- =============================================

CREATE DATABASE IF NOT EXISTS sc_datav DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sc_datav;

SET NAMES utf8mb4;
SET CHARACTER_SET_CLIENT = utf8mb4;
SET CHARACTER_SET_CONNECTION = utf8mb4;

-- =============================================
-- 1. 用户表
-- =============================================
CREATE TABLE IF NOT EXISTS sys_user (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password    VARCHAR(255) NOT NULL COMMENT '密码（BCrypt）',
    nickname    VARCHAR(50) COMMENT '昵称',
    avatar      VARCHAR(255) COMMENT '头像URL',
    role        VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色: ADMIN/USER',
    status      TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB COMMENT='用户表';

-- =============================================
-- 2. 操作日志表
-- =============================================
CREATE TABLE IF NOT EXISTS sys_log (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id     BIGINT COMMENT '用户ID',
    username    VARCHAR(50) COMMENT '用户名',
    operation   VARCHAR(100) COMMENT '操作描述',
    method      VARCHAR(200) COMMENT '请求方法',
    params      TEXT COMMENT '请求参数',
    ip          VARCHAR(50) COMMENT 'IP地址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB COMMENT='操作日志表';

-- =============================================
-- 3. 地区表（GeoJSON）
-- =============================================
CREATE TABLE IF NOT EXISTS geo_region (
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    adcode       INT NOT NULL UNIQUE COMMENT '行政区划编码',
    name         VARCHAR(50) NOT NULL COMMENT '地区名称',
    level        VARCHAR(20) COMMENT '级别: province/city',
    parent_code  INT COMMENT '父级编码',
    center_lng   DOUBLE COMMENT '中心经度',
    center_lat   DOUBLE COMMENT '中心纬度',
    centroid_lng DOUBLE COMMENT '质心经度',
    centroid_lat DOUBLE COMMENT '质心纬度',
    children_num INT DEFAULT 0 COMMENT '下级区划数',
    geo_json     JSON NOT NULL COMMENT '完整GeoJSON Feature',
    sort_order   INT DEFAULT 0 COMMENT '排序',
    create_time  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_parent (parent_code)
) ENGINE=InnoDB COMMENT='地区表';

-- =============================================
-- 4. 省界轮廓 GeoJSON
-- =============================================
CREATE TABLE IF NOT EXISTS geo_outline (
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    adcode   INT NOT NULL UNIQUE COMMENT '行政区划编码',
    name     VARCHAR(50) NOT NULL COMMENT '地区名称',
    geo_json JSON NOT NULL COMMENT '完整GeoJSON FeatureCollection'
) ENGINE=InnoDB COMMENT='省界轮廓表';

-- =============================================
-- 5. 热力图数据点
-- =============================================
CREATE TABLE IF NOT EXISTS geo_heatmap_point (
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_id BIGINT COMMENT '所属地区ID',
    lng       DOUBLE NOT NULL COMMENT '经度',
    lat       DOUBLE NOT NULL COMMENT '纬度',
    value     DOUBLE NOT NULL COMMENT '热力值',
    data_date DATE COMMENT '数据日期',
    INDEX idx_region (region_id)
) ENGINE=InnoDB COMMENT='热力图数据点';

-- =============================================
-- 6. 城市统计（人口/GDP/面积）
-- =============================================
CREATE TABLE IF NOT EXISTS city_stat (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_id  BIGINT NOT NULL COMMENT '地区ID',
    year       INT NOT NULL COMMENT '年份',
    population INT COMMENT '人口（万人）',
    gdp        DECIMAL(12,2) COMMENT 'GDP（亿元）',
    area       DECIMAL(10,2) COMMENT '面积（平方公里）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_region_year (region_id, year)
) ENGINE=InnoDB COMMENT='城市统计表';

-- =============================================
-- 7. 经济指标（Demo0 图表数据）
-- =============================================
CREATE TABLE IF NOT EXISTS econ_indicator (
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_id  BIGINT COMMENT '地区ID（NULL=全省）',
    indicator  VARCHAR(50) NOT NULL COMMENT '指标名: trend/import_export/quarterly',
    data_date  DATE COMMENT '数据日期',
    value      DECIMAL(12,2) COMMENT '数值',
    value2     DECIMAL(12,2) COMMENT '第二数值（如对比值）',
    year       INT COMMENT '年份',
    quarter    TINYINT COMMENT '季度: 1-4',
    label      VARCHAR(50) COMMENT '标签（如类型名）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_indicator (indicator),
    INDEX idx_date (data_date)
) ENGINE=InnoDB COMMENT='经济指标表';

-- =============================================
-- 8. 贸易记录（Demo0 表格）
-- =============================================
CREATE TABLE IF NOT EXISTS trade_record (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    type_name   VARCHAR(50) COMMENT '贸易类型',
    quantity    DECIMAL(10,2) COMMENT '数量（万）',
    trade_value DECIMAL(12,2) COMMENT '贸易值（万元）',
    data_date   DATE COMMENT '数据日期',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_date (data_date)
) ENGINE=InnoDB COMMENT='贸易记录表';

-- =============================================
-- 9. 企业统计（Demo1 图表数据）
-- =============================================
CREATE TABLE IF NOT EXISTS enterprise_stat (
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_id        BIGINT COMMENT '地区ID',
    year             INT COMMENT '年份',
    size_range       VARCHAR(20) COMMENT '规模区间',
    count            INT COMMENT '企业数量',
    revenue_total    DECIMAL(14,2) COMMENT '收益总计（亿元）',
    enterprise_count INT COMMENT '企业总数',
    quarter          TINYINT COMMENT '季度',
    quarter_value    DECIMAL(5,2) COMMENT '季度占比',
    yoy_current      DECIMAL(12,2) COMMENT '今年同期值',
    yoy_last         DECIMAL(12,2) COMMENT '去年同期值',
    yoy_date         DATE COMMENT '同比日期',
    patent_no        VARCHAR(50) COMMENT '专利编号',
    penalty_amount   DECIMAL(12,2) COMMENT '处罚金额',
    yoy_percent      DECIMAL(5,2) COMMENT '同比百分比',
    create_time      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_region (region_id),
    INDEX idx_year (year)
) ENGINE=InnoDB COMMENT='企业统计表';

-- =============================================
-- 10. 电网数据（Demo2 图表数据）
-- =============================================
CREATE TABLE IF NOT EXISTS power_stat (
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    region_id        BIGINT COMMENT '地区ID',
    year             INT COMMENT '年份',
    total_generation DECIMAL(12,2) COMMENT '总发电量（亿千瓦时）',
    cum_growth_rate  DECIMAL(5,2) COMMENT '累计增长率%',
    yoy_growth_rate  DECIMAL(5,2) COMMENT '同比增长率%',
    data_date        DATE COMMENT '数据日期',
    yoy_current      DECIMAL(12,2) COMMENT '今年值',
    yoy_last         DECIMAL(12,2) COMMENT '去年值',
    range_label      VARCHAR(20) COMMENT '容量区间标签',
    range_count      INT COMMENT '区间数量',
    infra_type       VARCHAR(20) COMMENT '设施类型: line/substation/cable/converter',
    infra_count      INT COMMENT '设施数量',
    infra_length     DECIMAL(10,2) COMMENT '长度(KM)',
    infra_power      DECIMAL(10,2) COMMENT '功率(MVA)',
    radar_value      DECIMAL(10,2) COMMENT '雷达图数值',
    create_time      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_region (region_id),
    INDEX idx_year (year)
) ENGINE=InnoDB COMMENT='电网数据表';

-- =============================================
-- 11. 电网故障记录（Demo2 表格）
-- =============================================
CREATE TABLE IF NOT EXISTS power_fault (
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    fault_name    VARCHAR(100) COMMENT '故障事件名称',
    anomaly_count INT COMMENT '异常次数',
    alarm_count   INT COMMENT '报警次数',
    status        VARCHAR(20) DEFAULT '处理中' COMMENT '状态: 处理中/已处理',
    create_time   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_status (status)
) ENGINE=InnoDB COMMENT='电网故障记录表';

-- =============================================
-- 初始数据
-- =============================================

-- 默认管理员账号 (密码: admin123, BCrypt加密)
INSERT INTO sys_user (username, password, nickname, role, status)
VALUES ('admin', '$2a$10$mu85yMbY3sGAP0l6LeidUOmSRM4iJDqqryHQUpp9OtSjOqZptCqVO', '管理员', 'ADMIN', 1);

-- 甘肃省14个地级市/自治州地区数据
-- 注意: geo_json 字段需要通过应用层导入完整 GeoJSON
-- 这里先插入基本属性数据
INSERT INTO geo_region (adcode, name, level, parent_code, center_lng, center_lat, centroid_lng, centroid_lat, children_num, geo_json, sort_order) VALUES
(620100, '兰州市', 'city', 620000, 103.8343, 36.0611, 103.8343, 36.0611, 0, '{}', 1),
(620200, '嘉峪关市', 'city', 620000, 98.2773, 39.7865, 98.2773, 39.7865, 0, '{}', 2),
(620300, '金昌市', 'city', 620000, 102.1879, 38.5201, 102.1879, 38.5201, 0, '{}', 3),
(620400, '白银市', 'city', 620000, 104.1386, 36.5448, 104.1386, 36.5448, 0, '{}', 4),
(620500, '天水市', 'city', 620000, 105.7250, 34.5785, 105.7250, 34.5785, 0, '{}', 5),
(620600, '武威市', 'city', 620000, 102.6346, 37.9297, 102.6346, 37.9297, 0, '{}', 6),
(620700, '张掖市', 'city', 620000, 100.4527, 38.9319, 100.4527, 38.9319, 0, '{}', 7),
(620800, '平凉市', 'city', 620000, 106.6847, 35.5428, 106.6847, 35.5428, 0, '{}', 8),
(620900, '酒泉市', 'city', 620000, 98.5108, 39.7440, 98.5108, 39.7440, 0, '{}', 9),
(621000, '庆阳市', 'city', 620000, 107.6384, 35.7342, 107.6384, 35.7342, 0, '{}', 10),
(621100, '定西市', 'city', 620000, 104.5923, 35.6070, 104.5923, 35.6070, 0, '{}', 11),
(621200, '陇南市', 'city', 620000, 104.9217, 33.3886, 104.9217, 33.3886, 0, '{}', 12),
(622900, '临夏回族自治州', 'city', 620000, 103.2120, 35.5961, 103.2120, 35.5961, 0, '{}', 13),
(623000, '甘南藏族自治州', 'city', 620000, 102.9115, 34.9833, 102.9115, 34.9833, 0, '{}', 14);

-- 城市统计数据（2024年）
INSERT INTO city_stat (region_id, year, population, gdp, area) VALUES
((SELECT id FROM geo_region WHERE adcode=620100), 2024, 438, 3400.00, 13100.00),
((SELECT id FROM geo_region WHERE adcode=620200), 2024, 31, 350.00, 2935.00),
((SELECT id FROM geo_region WHERE adcode=620300), 2024, 47, 600.00, 8896.00),
((SELECT id FROM geo_region WHERE adcode=620400), 2024, 151, 640.00, 21209.00),
((SELECT id FROM geo_region WHERE adcode=620500), 2024, 335, 830.00, 14325.00),
((SELECT id FROM geo_region WHERE adcode=620600), 2024, 182, 620.00, 33249.00),
((SELECT id FROM geo_region WHERE adcode=620700), 2024, 130, 580.00, 38600.00),
((SELECT id FROM geo_region WHERE adcode=620800), 2024, 184, 530.00, 11325.00),
((SELECT id FROM geo_region WHERE adcode=620900), 2024, 113, 850.00, 192000.00),
((SELECT id FROM geo_region WHERE adcode=621000), 2024, 217, 900.00, 27119.00),
((SELECT id FROM geo_region WHERE adcode=621100), 2024, 250, 420.00, 19609.00),
((SELECT id FROM geo_region WHERE adcode=621200), 2024, 256, 560.00, 27923.00),
((SELECT id FROM geo_region WHERE adcode=622900), 2024, 210, 300.00, 8169.00),
((SELECT id FROM geo_region WHERE adcode=623000), 2024, 73, 250.00, 38521.00);
