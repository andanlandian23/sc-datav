package com.scdatav.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scdatav.common.PageResult;
import com.scdatav.common.Result;
import com.scdatav.entity.*;
import com.scdatav.mapper.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin-数据管理", description = "管理员操作：业务数据增删改查")
@RestController
@RequestMapping("/api/admin/data")
@RequiredArgsConstructor
public class AdminDataController {

    private final GeoRegionMapper geoRegionMapper;
    private final CityStatMapper cityStatMapper;
    private final EconIndicatorMapper econIndicatorMapper;
    private final TradeRecordMapper tradeRecordMapper;
    private final EnterpriseStatMapper enterpriseStatMapper;
    private final PowerStatMapper powerStatMapper;
    private final PowerFaultMapper powerFaultMapper;
    private final GeoHeatmapPointMapper geoHeatmapPointMapper;

    // ========== 地区管理 ==========

    @Operation(summary = "地区列表")
    @GetMapping("/regions")
    public Result<PageResult<GeoRegion>> regionList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GeoRegion> p = geoRegionMapper.selectPage(new Page<>(page, size), null);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "更新地区")
    @PutMapping("/regions/{id}")
    public Result<Void> updateRegion(@PathVariable Long id, @RequestBody GeoRegion region) {
        region.setId(id);
        geoRegionMapper.updateById(region);
        return Result.ok();
    }

    // ========== 城市统计管理 ==========

    @Operation(summary = "城市统计列表")
    @GetMapping("/city-stats")
    public Result<PageResult<CityStat>> cityStatList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CityStat> p = cityStatMapper.selectPage(new Page<>(page, size),
                new LambdaQueryWrapper<CityStat>().orderByDesc(CityStat::getYear));
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建城市统计")
    @PostMapping("/city-stats")
    public Result<Void> createCityStat(@RequestBody CityStat stat) {
        cityStatMapper.insert(stat);
        return Result.ok();
    }

    @Operation(summary = "更新城市统计")
    @PutMapping("/city-stats/{id}")
    public Result<Void> updateCityStat(@PathVariable Long id, @RequestBody CityStat stat) {
        stat.setId(id);
        cityStatMapper.updateById(stat);
        return Result.ok();
    }

    @Operation(summary = "删除城市统计")
    @DeleteMapping("/city-stats/{id}")
    public Result<Void> deleteCityStat(@PathVariable Long id) {
        cityStatMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 经济指标管理 ==========

    @Operation(summary = "经济指标列表")
    @GetMapping("/econ-indicators")
    public Result<PageResult<EconIndicator>> econList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String indicator) {
        LambdaQueryWrapper<EconIndicator> wrapper = new LambdaQueryWrapper<>();
        if (indicator != null) wrapper.eq(EconIndicator::getIndicator, indicator);
        wrapper.orderByDesc(EconIndicator::getDataDate);
        Page<EconIndicator> p = econIndicatorMapper.selectPage(new Page<>(page, size), wrapper);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建经济指标")
    @PostMapping("/econ-indicators")
    public Result<Void> createEcon(@RequestBody EconIndicator indicator) {
        econIndicatorMapper.insert(indicator);
        return Result.ok();
    }

    @Operation(summary = "更新经济指标")
    @PutMapping("/econ-indicators/{id}")
    public Result<Void> updateEcon(@PathVariable Long id, @RequestBody EconIndicator indicator) {
        indicator.setId(id);
        econIndicatorMapper.updateById(indicator);
        return Result.ok();
    }

    @Operation(summary = "删除经济指标")
    @DeleteMapping("/econ-indicators/{id}")
    public Result<Void> deleteEcon(@PathVariable Long id) {
        econIndicatorMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 贸易记录管理 ==========

    @Operation(summary = "贸易记录列表")
    @GetMapping("/trade-records")
    public Result<PageResult<TradeRecord>> tradeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TradeRecord> p = tradeRecordMapper.selectPage(new Page<>(page, size),
                new LambdaQueryWrapper<TradeRecord>().orderByDesc(TradeRecord::getId));
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建贸易记录")
    @PostMapping("/trade-records")
    public Result<Void> createTrade(@RequestBody TradeRecord record) {
        tradeRecordMapper.insert(record);
        return Result.ok();
    }

    @Operation(summary = "更新贸易记录")
    @PutMapping("/trade-records/{id}")
    public Result<Void> updateTrade(@PathVariable Long id, @RequestBody TradeRecord record) {
        record.setId(id);
        tradeRecordMapper.updateById(record);
        return Result.ok();
    }

    @Operation(summary = "删除贸易记录")
    @DeleteMapping("/trade-records/{id}")
    public Result<Void> deleteTrade(@PathVariable Long id) {
        tradeRecordMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 企业统计管理 ==========

    @Operation(summary = "企业统计列表")
    @GetMapping("/enterprise-stats")
    public Result<PageResult<EnterpriseStat>> enterpriseList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<EnterpriseStat> p = enterpriseStatMapper.selectPage(new Page<>(page, size), null);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建企业统计")
    @PostMapping("/enterprise-stats")
    public Result<Void> createEnterprise(@RequestBody EnterpriseStat stat) {
        enterpriseStatMapper.insert(stat);
        return Result.ok();
    }

    @Operation(summary = "更新企业统计")
    @PutMapping("/enterprise-stats/{id}")
    public Result<Void> updateEnterprise(@PathVariable Long id, @RequestBody EnterpriseStat stat) {
        stat.setId(id);
        enterpriseStatMapper.updateById(stat);
        return Result.ok();
    }

    @Operation(summary = "删除企业统计")
    @DeleteMapping("/enterprise-stats/{id}")
    public Result<Void> deleteEnterprise(@PathVariable Long id) {
        enterpriseStatMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 电网数据管理 ==========

    @Operation(summary = "电网数据列表")
    @GetMapping("/power-stats")
    public Result<PageResult<PowerStat>> powerList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PowerStat> p = powerStatMapper.selectPage(new Page<>(page, size), null);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建电网数据")
    @PostMapping("/power-stats")
    public Result<Void> createPower(@RequestBody PowerStat stat) {
        powerStatMapper.insert(stat);
        return Result.ok();
    }

    @Operation(summary = "更新电网数据")
    @PutMapping("/power-stats/{id}")
    public Result<Void> updatePower(@PathVariable Long id, @RequestBody PowerStat stat) {
        stat.setId(id);
        powerStatMapper.updateById(stat);
        return Result.ok();
    }

    @Operation(summary = "删除电网数据")
    @DeleteMapping("/power-stats/{id}")
    public Result<Void> deletePower(@PathVariable Long id) {
        powerStatMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 故障记录管理 ==========

    @Operation(summary = "故障记录列表")
    @GetMapping("/power-faults")
    public Result<PageResult<PowerFault>> faultList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PowerFault> p = powerFaultMapper.selectPage(new Page<>(page, size),
                new LambdaQueryWrapper<PowerFault>().orderByDesc(PowerFault::getId));
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建故障记录")
    @PostMapping("/power-faults")
    public Result<Void> createFault(@RequestBody PowerFault fault) {
        powerFaultMapper.insert(fault);
        return Result.ok();
    }

    @Operation(summary = "更新故障记录")
    @PutMapping("/power-faults/{id}")
    public Result<Void> updateFault(@PathVariable Long id, @RequestBody PowerFault fault) {
        fault.setId(id);
        powerFaultMapper.updateById(fault);
        return Result.ok();
    }

    @Operation(summary = "删除故障记录")
    @DeleteMapping("/power-faults/{id}")
    public Result<Void> deleteFault(@PathVariable Long id) {
        powerFaultMapper.deleteById(id);
        return Result.ok();
    }

    // ========== 热力图数据管理 ==========

    @Operation(summary = "热力图数据列表")
    @GetMapping("/heatmap-points")
    public Result<PageResult<GeoHeatmapPoint>> heatmapList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<GeoHeatmapPoint> p = geoHeatmapPointMapper.selectPage(new Page<>(page, size), null);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }

    @Operation(summary = "创建热力图数据")
    @PostMapping("/heatmap-points")
    public Result<Void> createHeatmapPoint(@RequestBody GeoHeatmapPoint point) {
        geoHeatmapPointMapper.insert(point);
        return Result.ok();
    }

    @Operation(summary = "删除热力图数据")
    @DeleteMapping("/heatmap-points/{id}")
    public Result<Void> deleteHeatmapPoint(@PathVariable Long id) {
        geoHeatmapPointMapper.deleteById(id);
        return Result.ok();
    }
}
