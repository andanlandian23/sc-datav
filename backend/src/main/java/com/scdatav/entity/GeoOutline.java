package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("geo_outline")
public class GeoOutline {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer adcode;

    private String name;

    private String geoJson;
}
