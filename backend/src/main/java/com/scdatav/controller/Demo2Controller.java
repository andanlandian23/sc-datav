package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.service.Demo2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Demo2-电网感知平台接口", description = "电网感知大屏的数据接口")
@RestController
@RequestMapping("/api/demo2")
@RequiredArgsConstructor
public class Demo2Controller {

    private final Demo2Service demo2Service;

    @Operation(summary = "发电量统计")
    @GetMapping("/generation")
    public Result<Map<String, Object>> getGeneration() {
        return Result.ok(demo2Service.getGeneration());
    }

    @Operation(summary = "电力同比")
    @GetMapping("/power-yoy")
    public Result<List<Map<String, Object>>> getPowerYoy() {
        return Result.ok(demo2Service.getPowerYoy());
    }

    @Operation(summary = "电力分布")
    @GetMapping("/power-range")
    public Result<List<Map<String, Object>>> getPowerRange() {
        return Result.ok(demo2Service.getPowerRange());
    }

    @Operation(summary = "电网设施")
    @GetMapping("/infrastructure")
    public Result<List<Map<String, Object>>> getInfrastructure() {
        return Result.ok(demo2Service.getInfrastructure());
    }

    @Operation(summary = "城市雷达图")
    @GetMapping("/city-radar")
    public Result<List<Map<String, Object>>> getCityRadar() {
        return Result.ok(demo2Service.getCityRadar());
    }

    @Operation(summary = "故障记录表格")
    @GetMapping("/fault-table")
    public Result<List<Map<String, Object>>> getFaultTable() {
        return Result.ok(demo2Service.getFaultTable());
    }
}
