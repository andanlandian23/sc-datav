package com.scdatav.service;

import java.util.List;
import java.util.Map;

public interface Demo0Service {

    /** 趋势数据（全省+兰州） */
    List<Map<String, Object>> getTrend();

    /** 进出口数据 */
    List<Map<String, Object>> getTrade();

    /** 季度数据 */
    List<Map<String, Object>> getQuarterly();

    /** 贸易记录表格 */
    List<Map<String, Object>> getRecords();
}
