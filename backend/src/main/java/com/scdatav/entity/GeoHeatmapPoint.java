package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("geo_heatmap_point")
public class GeoHeatmapPoint {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long regionId;

    private Double lng;

    private Double lat;

    private Double value;

    private LocalDate dataDate;
}
