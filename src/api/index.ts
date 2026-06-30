import request from './request'

// ========== 认证接口 ==========
export const authApi = {
  login: (username: string, password: string) =>
    request.post('/auth/login', { username, password }),

  register: (username: string, password: string, nickname?: string) =>
    request.post('/auth/register', { username, password, nickname }),

  getUserInfo: () => request.get('/auth/info'),
}

// ========== 地理数据接口 ==========
export const geoApi = {
  getRegions: () => request.get('/geo/regions'),
  getRegion: (adcode: number) => request.get(`/geo/regions/${adcode}`),
  getOutline: () => request.get('/geo/outline'),
  getHeatmap: () => request.get('/geo/heatmap'),
}

// ========== Demo0 - 经济运行监测 ==========
export const demo0Api = {
  getTrend: () => request.get('/demo0/trend'),
  getTrade: () => request.get('/demo0/trade'),
  getQuarterly: () => request.get('/demo0/quarterly'),
  getRecords: () => request.get('/demo0/records'),
}

// ========== Demo1 - 智慧城市数据大脑 ==========
export const demo1Api = {
  getPopulation: () => request.get('/demo1/population'),
  getYoy: () => request.get('/demo1/yoy'),
  getPatentTable: () => request.get('/demo1/patent-table'),
  getRevenue: () => request.get('/demo1/revenue'),
  getDistribution: () => request.get('/demo1/distribution'),
  getEnterpriseSize: () => request.get('/demo1/enterprise-size'),
}

// ========== Demo2 - 电网感知平台 ==========
export const demo2Api = {
  getGeneration: () => request.get('/demo2/generation'),
  getPowerYoy: () => request.get('/demo2/power-yoy'),
  getPowerRange: () => request.get('/demo2/power-range'),
  getInfrastructure: () => request.get('/demo2/infrastructure'),
  getCityRadar: () => request.get('/demo2/city-radar'),
  getFaultTable: () => request.get('/demo2/fault-table'),
}
