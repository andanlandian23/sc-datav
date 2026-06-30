package com.scdatav.service;

import java.util.List;
import java.util.Map;

public interface Demo1Service {

    /** 城市人口排名 */
    List<Map<String, Object>> getPopulation();

    /** 同比对比数据 */
    List<Map<String, Object>> getYoy();

    /** 专利表格数据 */
    List<Map<String, Object>> getPatentTable();

    /** 收益统计 */
    Map<String, Object> getRevenue();

    /** 季度分布 */
    List<Map<String, Object>> getDistribution();

    /** 企业规模分布 */
    List<Map<String, Object>> getEnterpriseSize();
}
