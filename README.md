<div align="center">
  <h1>甘肃省数据可视化大屏</h1>
  <p>前后端分离 · 3D 地图可视化 · Spring Boot + React</p>
  <p>基于 Three.js + ECharts 的 3D 地图可视化大屏，支持用户认证、数据管理、多图表联动</p>

<p>
    <a href="https://spring.io/projects/spring-boot">
      <img src="https://img.shields.io/badge/Spring%20Boot-3.4.5-6db33f?style=flat-square&logo=spring-boot" alt="Spring Boot">
    </a>
    <a href="https://react.dev/">
      <img src="https://img.shields.io/badge/React-19.1.1-61dafb?style=flat-square&logo=react" alt="React">
    </a>
    <a href="https://threejs.org/">
      <img src="https://img.shields.io/badge/Three.js-0.183.2-black?style=flat-square&logo=three.js" alt="Three.js">
    </a>
    <a href="https://www.typescriptlang.org/">
      <img src="https://img.shields.io/badge/TypeScript-5.9.3-3178c6?style=flat-square&logo=typescript" alt="TypeScript">
    </a>
    <a href="https://www.mysql.com/">
      <img src="https://img.shields.io/badge/MySQL-8.0-4479a1?style=flat-square&logo=mysql" alt="MySQL">
    </a>
  </p>
</div>

---

## 预览

| [Demo0 - 经济运行监测](https://andanlandian23.github.io/sc-datav/#/demo0) | [Demo1 - 智慧城市数据大脑](https://andanlandian23.github.io/sc-datav/#/demo1) |
| ------------------------------------------------------- | ------------------------------------------------------- |
| ![demo0](./public/demo_0.jpg)                           | ![demo1](./public/demo_1.jpg)                           |

| [Demo2 - 电力全景感知平台](https://andanlandian23.github.io/sc-datav/#/demo2) | [Demo3 - 风力发电机模型](https://andanlandian23.github.io/sc-datav/#/demo3) |
| ------------------------------------------------------- | ------------------------------------------------------- |
| ![demo2](./public/demo_2.jpg)                           | ![demo3](./public/demo_3.jpg)                           |

## 项目架构

```
sc-datav/
├── backend/                    # Spring Boot 后端
│   ├── pom.xml
│   └── src/main/java/com/scdatav/
│       ├── common/             # 统一返回、JWT 工具
│       ├── config/             # 跨域、MyBatis、Security、Swagger
│       ├── entity/             # 实体类（11 个）
│       ├── mapper/             # MyBatis-Plus Mapper
│       ├── security/           # JWT 过滤器、UserDetails
│       ├── service/            # 业务逻辑层
│       └── controller/         # REST API 接口
│           └── admin/          # 管理后台接口
├── database/
│   └── init.sql                # 建表脚本 + 初始数据
├── src/                        # React 前端
│   ├── api/                    # Axios 封装 + API 函数
│   ├── store/auth.ts           # Zustand 认证状态
│   ├── pages/Login/            # 登录/注册页
│   ├── pages/Demo0~3/          # 4 个可视化 Demo
│   └── ...
└── vite.config.ts              # Vite 配置（含 API 代理）
```

## 技术栈

| 层级 | 技术 | 版本 |
|---|---|---|
| **后端框架** | Spring Boot | 3.4.5 |
| **ORM** | MyBatis-Plus | 3.5.12 |
| **数据库** | MySQL | 8.0+ |
| **认证** | Spring Security + JWT (jjwt) | |
| **接口文档** | Knife4j (Swagger) | 4.5.0 |
| **前端框架** | React + TypeScript | 19.1.1 / 5.9.3 |
| **构建工具** | Vite | 8.0.0 |
| **3D 渲染** | Three.js + @react-three/fiber | 0.183.2 |
| **图表** | ECharts | 6.0.0 |
| **动画** | GSAP | 3.13.0 |
| **状态管理** | Zustand | 5.0.8 |
| **HTTP 客户端** | Axios | 1.x |

## 快速启动

### 环境要求

- **Java** 17+
- **Maven** 3.6+
- **MySQL** 8.0+
- **Node.js** 18+

### 1. 初始化数据库

```bash
mysql -u root -p < database/init.sql
```

默认创建数据库 `sc_datav`，包含 11 张表和初始数据。

### 2. 启动后端

```bash
cd backend

# 修改数据库连接（src/main/resources/application.yml）
# spring.datasource.password: 你的密码

mvn spring-boot:run
```

后端启动在 http://localhost:8080

### 3. 启动前端

```bash
# 安装依赖
npm install

# 开发模式
npm run dev
```

前端启动在 http://localhost:5173，API 请求自动代理到后端。

### 4. 登录

打开浏览器访问 http://localhost:5173/#/login

| 账号 | 密码 | 角色 |
|---|---|---|
| `admin` | `admin123` | 管理员 |

## API 接口

启动后端后访问 Swagger 文档：http://localhost:8080/doc.html

### 接口概览

| 分组 | 路径前缀 | 说明 |
|---|---|---|
| 认证 | `/api/auth` | 登录、注册、用户信息 |
| 地理数据 | `/api/geo` | 地区、轮廓、热力图 |
| 经济监测 | `/api/demo0` | 趋势、贸易、季度、记录 |
| 智慧城市 | `/api/demo1` | 人口、同比、专利、收益、规模 |
| 电网感知 | `/api/demo2` | 发电量、电力分布、设施、故障 |
| 管理后台 | `/api/admin` | 用户管理、数据 CRUD、日志 |

### 认证方式

所有接口（除登录注册外）需要在请求头携带 JWT：

```
Authorization: Bearer <token>
```

### 响应格式

```json
{
  "code": 200,
  "msg": "success",
  "data": { ... }
}
```

## 数据库设计

| 表名 | 说明 | 记录数 |
|---|---|---|
| `sys_user` | 用户表 | 1（管理员） |
| `sys_log` | 操作日志 | - |
| `geo_region` | 地区表（GeoJSON） | 14（甘肃各市） |
| `geo_outline` | 省界轮廓 | 1 |
| `geo_heatmap_point` | 热力图数据点 | - |
| `city_stat` | 城市统计 | 14 |
| `econ_indicator` | 经济指标 | - |
| `trade_record` | 贸易记录 | - |
| `enterprise_stat` | 企业统计 | - |
| `power_stat` | 电网数据 | - |
| `power_fault` | 故障记录 | - |

## 页面说明

### Demo0 — 经济运行监测（平面地图 + 飞线）

- 甘肃省 14 个地级市的 3D 平面地图，带卫星贴图和法线贴图
- 沿省界轮廓流动的飞线动画，使用自定义着色器实现彗星尾效果
- 省界侧面的竖向扫描光带动画
- 支持切换纹理/位移贴图两种地图风格，支持纯净模式

### Demo1 — 智慧城市数据大脑（立体地图 + 柱状图）

- 各城市区域拉伸为 3D 立体块，带 GSAP 入场动画
- 从各城市中心升起的发光柱体，带底部旋转光环和侧面辉光
- 热力图叠加、云层效果、底部扫描波纹动画
- 6 个数据图表面板 + 底部虚拟滚动实时数据表

### Demo2 — 电力全景感知平台（暗黑科幻风）

- 城市区域可交互悬停，自定义扫描着色器（ShiftMaterial）
- 光束灯、轨迹飞线、边界光晕、镜面反射等科幻效果
- 用电大市 TOP5 雷达图（嘉峪关、白银、兰州、酒泉、天水）

### Demo3 — 风力发电机 3D 模型

- 风力发电机 GLB 模型展示，带 Bloom 后处理发光效果
- HDR 环境贴图、自动旋转、拆解/还原交互

## 甘肃省行政区划

| 序号 | 名称 | adcode |
|------|------|--------|
| 1 | 兰州市 | 620100 |
| 2 | 嘉峪关市 | 620200 |
| 3 | 金昌市 | 620300 |
| 4 | 白银市 | 620400 |
| 5 | 天水市 | 620500 |
| 6 | 武威市 | 620600 |
| 7 | 张掖市 | 620700 |
| 8 | 平凉市 | 620800 |
| 9 | 酒泉市 | 620900 |
| 10 | 庆阳市 | 621000 |
| 11 | 定西市 | 621100 |
| 12 | 陇南市 | 621200 |
| 13 | 临夏回族自治州 | 622900 |
| 14 | 甘南藏族自治州 | 623000 |

## 数据来源

- 地理数据：[阿里云 DataV.GeoAtlas](https://datav.aliyun.com/portal/school/atlas/area_selector)
- 卫星贴图：可通过 [sat-hunter](https://github.com/knight-L/sat-hunter) 工具下载

## 致谢

基于 [knight-L/sc-datav](https://github.com/knight-L/sc-datav) 项目改造，特此感谢原作者。

## 许可证

[Apache License 2.0](./LICENSE)
