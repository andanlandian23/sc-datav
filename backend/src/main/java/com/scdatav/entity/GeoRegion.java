package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("geo_region")
public class GeoRegion {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer adcode;

    private String name;

    private String level;

    private Integer parentCode;

    private Double centerLng;

    private Double centerLat;

    private Double centroidLng;

    private Double centroidLat;

    private Integer childrenNum;

    private String geoJson;

    private Integer sortOrder;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
