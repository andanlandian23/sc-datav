package com.scdatav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scdatav.entity.CityStat;
import com.scdatav.entity.EnterpriseStat;
import com.scdatav.entity.GeoRegion;
import com.scdatav.mapper.CityStatMapper;
import com.scdatav.mapper.EnterpriseStatMapper;
import com.scdatav.mapper.GeoRegionMapper;
import com.scdatav.service.Demo1Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Demo1ServiceImpl implements Demo1Service {

    private final CityStatMapper cityStatMapper;
    private final EnterpriseStatMapper enterpriseStatMapper;
    private final GeoRegionMapper geoRegionMapper;

    @Override
    public List<Map<String, Object>> getPopulation() {
        List<CityStat> stats = cityStatMapper.selectList(
                new LambdaQueryWrapper<CityStat>()
                        .eq(CityStat::getYear, 2024)
                        .orderByDesc(CityStat::getPopulation)
                        .last("LIMIT 5"));

        // 获取地区名称映射
        Map<Long, String> regionNames = getRegionNameMap();

        return stats.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("city", regionNames.getOrDefault(item.getRegionId(), ""));
            map.put("population", item.getPopulation());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getYoy() {
        List<EnterpriseStat> list = enterpriseStatMapper.selectList(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getYoyDate)
                        .orderByAsc(EnterpriseStat::getYoyDate));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", item.getYoyDate());
            map.put("current", item.getYoyCurrent());
            map.put("last", item.getYoyLast());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getPatentTable() {
        List<EnterpriseStat> list = enterpriseStatMapper.selectList(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getPatentNo));

        Map<Long, String> regionNames = getRegionNameMap();

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("city", regionNames.getOrDefault(item.getRegionId(), ""));
            map.put("patentNo", item.getPatentNo());
            map.put("penaltyAmount", item.getPenaltyAmount());
            map.put("yoyPercent", item.getYoyPercent());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getRevenue() {
        // 取最新的收益统计数据
        EnterpriseStat stat = enterpriseStatMapper.selectOne(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getRevenueTotal)
                        .orderByDesc(EnterpriseStat::getYear)
                        .last("LIMIT 1"));

        Map<String, Object> result = new HashMap<>();
        if (stat != null) {
            result.put("revenueTotal", stat.getRevenueTotal());
            result.put("enterpriseCount", stat.getEnterpriseCount());
        }

        // 趋势数据
        List<EnterpriseStat> trend = enterpriseStatMapper.selectList(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getYoyCurrent)
                        .orderByAsc(EnterpriseStat::getYoyDate)
                        .last("LIMIT 10"));

        result.put("trend", trend.stream()
                .map(EnterpriseStat::getYoyCurrent)
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public List<Map<String, Object>> getDistribution() {
        List<EnterpriseStat> list = enterpriseStatMapper.selectList(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getQuarter)
                        .isNotNull(EnterpriseStat::getQuarterValue)
                        .orderByAsc(EnterpriseStat::getQuarter));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("quarter", "Q" + item.getQuarter());
            map.put("value", item.getQuarterValue());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getEnterpriseSize() {
        List<EnterpriseStat> list = enterpriseStatMapper.selectList(
                new LambdaQueryWrapper<EnterpriseStat>()
                        .isNotNull(EnterpriseStat::getSizeRange)
                        .orderByAsc(EnterpriseStat::getId));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("range", item.getSizeRange());
            map.put("count", item.getCount());
            return map;
        }).collect(Collectors.toList());
    }

    private Map<Long, String> getRegionNameMap() {
        List<GeoRegion> regions = geoRegionMapper.selectList(null);
        return regions.stream().collect(Collectors.toMap(GeoRegion::getId, GeoRegion::getName));
    }
}
