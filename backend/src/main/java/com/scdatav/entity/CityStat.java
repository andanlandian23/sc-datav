package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("city_stat")
public class CityStat {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long regionId;

    private Integer year;

    private Integer population;

    private BigDecimal gdp;

    private BigDecimal area;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
