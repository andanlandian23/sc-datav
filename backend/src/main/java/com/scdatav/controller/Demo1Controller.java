package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.service.Demo1Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Demo1-智慧城市数据大脑接口", description = "智慧城市大屏的数据接口")
@RestController
@RequestMapping("/api/demo1")
@RequiredArgsConstructor
public class Demo1Controller {

    private final Demo1Service demo1Service;

    @Operation(summary = "城市人口排名")
    @GetMapping("/population")
    public Result<List<Map<String, Object>>> getPopulation() {
        return Result.ok(demo1Service.getPopulation());
    }

    @Operation(summary = "同比对比数据")
    @GetMapping("/yoy")
    public Result<List<Map<String, Object>>> getYoy() {
        return Result.ok(demo1Service.getYoy());
    }

    @Operation(summary = "专利表格数据")
    @GetMapping("/patent-table")
    public Result<List<Map<String, Object>>> getPatentTable() {
        return Result.ok(demo1Service.getPatentTable());
    }

    @Operation(summary = "收益统计")
    @GetMapping("/revenue")
    public Result<Map<String, Object>> getRevenue() {
        return Result.ok(demo1Service.getRevenue());
    }

    @Operation(summary = "季度分布")
    @GetMapping("/distribution")
    public Result<List<Map<String, Object>>> getDistribution() {
        return Result.ok(demo1Service.getDistribution());
    }

    @Operation(summary = "企业规模分布")
    @GetMapping("/enterprise-size")
    public Result<List<Map<String, Object>>> getEnterpriseSize() {
        return Result.ok(demo1Service.getEnterpriseSize());
    }
}
