package com.scdatav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scdatav.entity.GeoHeatmapPoint;
import com.scdatav.entity.GeoOutline;
import com.scdatav.entity.GeoRegion;
import com.scdatav.mapper.GeoHeatmapPointMapper;
import com.scdatav.mapper.GeoOutlineMapper;
import com.scdatav.mapper.GeoRegionMapper;
import com.scdatav.service.GeoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

    private final GeoRegionMapper geoRegionMapper;
    private final GeoOutlineMapper geoOutlineMapper;
    private final GeoHeatmapPointMapper heatmapPointMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<GeoRegion> getAllRegions() {
        return geoRegionMapper.selectList(
                new LambdaQueryWrapper<GeoRegion>().orderByAsc(GeoRegion::getSortOrder));
    }

    @Override
    public GeoRegion getRegionByAdcode(Integer adcode) {
        return geoRegionMapper.selectOne(
                new LambdaQueryWrapper<GeoRegion>().eq(GeoRegion::getAdcode, adcode));
    }

    @Override
    public Map<String, Object> getOutline() {
        GeoOutline outline = geoOutlineMapper.selectOne(
                new LambdaQueryWrapper<GeoOutline>().eq(GeoOutline::getAdcode, 620000));
        if (outline == null) {
            return Map.of("type", "FeatureCollection", "features", List.of());
        }
        try {
            return objectMapper.readValue(outline.getGeoJson(), new TypeReference<>() {});
        } catch (Exception e) {
            return Map.of("type", "FeatureCollection", "features", List.of());
        }
    }

    @Override
    public List<GeoHeatmapPoint> getHeatmapData() {
        return heatmapPointMapper.selectList(null);
    }
}
