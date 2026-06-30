package com.scdatav.service;

import java.util.List;
import java.util.Map;

public interface Demo2Service {

    /** 发电量统计 */
    Map<String, Object> getGeneration();

    /** 电力同比 */
    List<Map<String, Object>> getPowerYoy();

    /** 电力分布 */
    List<Map<String, Object>> getPowerRange();

    /** 电网设施 */
    List<Map<String, Object>> getInfrastructure();

    /** 城市雷达图 */
    List<Map<String, Object>> getCityRadar();

    /** 故障记录表格 */
    List<Map<String, Object>> getFaultTable();
}
