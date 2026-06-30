package com.scdatav.service;

import com.scdatav.entity.GeoHeatmapPoint;
import com.scdatav.entity.GeoRegion;

import java.util.List;
import java.util.Map;

public interface GeoService {

    /** 获取所有地区（含 GeoJSON） */
    List<GeoRegion> getAllRegions();

    /** 获取单个地区 */
    GeoRegion getRegionByAdcode(Integer adcode);

    /** 获取省界轮廓 GeoJSON */
    Map<String, Object> getOutline();

    /** 获取热力图数据 */
    List<GeoHeatmapPoint> getHeatmapData();
}
