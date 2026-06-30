package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("geo_region")
@Schema(description = "地区实体（含GeoJSON）")
public class GeoRegion {

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "行政区划编码", example = "620100")
    private Integer adcode;

    @Schema(description = "地区名称", example = "兰州市")
    private String name;

    @Schema(description = "级别", example = "city", allowableValues = {"province", "city"})
    private String level;

    @Schema(description = "父级编码", example = "620000")
    private Integer parentCode;

    @Schema(description = "中心经度", example = "103.8343")
    private Double centerLng;

    @Schema(description = "中心纬度", example = "36.0611")
    private Double centerLat;

    @Schema(description = "质心经度")
    private Double centroidLng;

    @Schema(description = "质心纬度")
    private Double centroidLat;

    @Schema(description = "下级区划数")
    private Integer childrenNum;

    @Schema(description = "完整GeoJSON Feature")
    private String geoJson;

    @Schema(description = "排序")
    private Integer sortOrder;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
