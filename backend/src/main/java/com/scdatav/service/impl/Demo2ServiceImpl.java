package com.scdatav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scdatav.entity.GeoRegion;
import com.scdatav.entity.PowerFault;
import com.scdatav.entity.PowerStat;
import com.scdatav.mapper.GeoRegionMapper;
import com.scdatav.mapper.PowerFaultMapper;
import com.scdatav.mapper.PowerStatMapper;
import com.scdatav.service.Demo2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Demo2ServiceImpl implements Demo2Service {

    private final PowerStatMapper powerStatMapper;
    private final PowerFaultMapper powerFaultMapper;
    private final GeoRegionMapper geoRegionMapper;

    @Override
    public Map<String, Object> getGeneration() {
        PowerStat stat = powerStatMapper.selectOne(
                new LambdaQueryWrapper<PowerStat>()
                        .isNotNull(PowerStat::getTotalGeneration)
                        .orderByDesc(PowerStat::getYear)
                        .last("LIMIT 1"));

        Map<String, Object> result = new HashMap<>();
        if (stat != null) {
            result.put("totalGeneration", stat.getTotalGeneration());
            result.put("cumGrowthRate", stat.getCumGrowthRate());
            result.put("yoyGrowthRate", stat.getYoyGrowthRate());
        }

        result.put("quarterly", List.of());
        return result;
    }

    @Override
    public List<Map<String, Object>> getPowerYoy() {
        List<PowerStat> list = powerStatMapper.selectList(
                new LambdaQueryWrapper<PowerStat>()
                        .isNotNull(PowerStat::getYoyCurrent)
                        .isNotNull(PowerStat::getDataDate)
                        .orderByAsc(PowerStat::getDataDate));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("date", item.getDataDate());
            map.put("current", item.getYoyCurrent());
            map.put("last", item.getYoyLast());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getPowerRange() {
        List<PowerStat> list = powerStatMapper.selectList(
                new LambdaQueryWrapper<PowerStat>()
                        .isNotNull(PowerStat::getRangeLabel)
                        .isNotNull(PowerStat::getRangeCount)
                        .orderByAsc(PowerStat::getId));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("range", item.getRangeLabel());
            map.put("count", item.getRangeCount());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getInfrastructure() {
        List<PowerStat> list = powerStatMapper.selectList(
                new LambdaQueryWrapper<PowerStat>()
                        .isNotNull(PowerStat::getInfraType)
                        .orderByAsc(PowerStat::getId));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("type", item.getInfraType());
            map.put("count", item.getInfraCount());
            map.put("length", item.getInfraLength());
            map.put("power", item.getInfraPower());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getCityRadar() {
        List<PowerStat> list = powerStatMapper.selectList(
                new LambdaQueryWrapper<PowerStat>()
                        .isNotNull(PowerStat::getRadarValue)
                        .isNotNull(PowerStat::getRegionId)
                        .orderByDesc(PowerStat::getRadarValue)
                        .last("LIMIT 5"));

        Map<Long, String> regionNames = getRegionNameMap();

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("city", regionNames.getOrDefault(item.getRegionId(), ""));
            map.put("value", item.getRadarValue());
            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getFaultTable() {
        List<PowerFault> list = powerFaultMapper.selectList(
                new LambdaQueryWrapper<PowerFault>().orderByDesc(PowerFault::getId));

        return list.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("faultName", item.getFaultName());
            map.put("anomalyCount", item.getAnomalyCount());
            map.put("alarmCount", item.getAlarmCount());
            map.put("status", item.getStatus());
            return map;
        }).collect(Collectors.toList());
    }

    private Map<Long, String> getRegionNameMap() {
        List<GeoRegion> regions = geoRegionMapper.selectList(null);
        return regions.stream().collect(Collectors.toMap(GeoRegion::getId, GeoRegion::getName));
    }
}
