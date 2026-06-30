package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.service.Demo0Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Tag(name = "Demo0-经济运行监测接口", description = "经济运行监测大屏的数据接口")
@RestController
@RequestMapping("/api/demo0")
@RequiredArgsConstructor
public class Demo0Controller {

    private final Demo0Service demo0Service;

    @Operation(summary = "趋势数据", description = "获取全省和兰州市的经济趋势时间序列数据")
    @GetMapping("/trend")
    public Result<List<Map<String, Object>>> getTrend() {
        return Result.ok(demo0Service.getTrend());
    }

    @Operation(summary = "进出口数据", description = "获取进口和出口的时间序列数据")
    @GetMapping("/trade")
    public Result<List<Map<String, Object>>> getTrade() {
        return Result.ok(demo0Service.getTrade());
    }

    @Operation(summary = "季度数据", description = "获取 Q1-Q4 季度经济数据")
    @GetMapping("/quarterly")
    public Result<List<Map<String, Object>>> getQuarterly() {
        return Result.ok(demo0Service.getQuarterly());
    }

    @Operation(summary = "贸易记录表格", description = "获取贸易记录列表（分页）")
    @GetMapping("/records")
    public Result<List<Map<String, Object>>> getRecords() {
        return Result.ok(demo0Service.getRecords());
    }
}
