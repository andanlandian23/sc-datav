package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.entity.GeoHeatmapPoint;
import com.scdatav.entity.GeoRegion;
import com.scdatav.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "地理数据接口")
@RestController
@RequestMapping("/api/geo")
@RequiredArgsConstructor
public class GeoController {

    private final GeoService geoService;

    @Operation(summary = "获取所有地区（含GeoJSON）")
    @GetMapping("/regions")
    public Result<List<GeoRegion>> getAllRegions() {
        return Result.ok(geoService.getAllRegions());
    }

    @Operation(summary = "获取单个地区")
    @GetMapping("/regions/{adcode}")
    public Result<GeoRegion> getRegion(@PathVariable Integer adcode) {
        return Result.ok(geoService.getRegionByAdcode(adcode));
    }

    @Operation(summary = "获取省界轮廓 GeoJSON")
    @GetMapping("/outline")
    public Result<Map<String, Object>> getOutline() {
        return Result.ok(geoService.getOutline());
    }

    @Operation(summary = "获取热力图数据")
    @GetMapping("/heatmap")
    public Result<List<GeoHeatmapPoint>> getHeatmap() {
        return Result.ok(geoService.getHeatmapData());
    }
}
