package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("power_stat")
public class PowerStat {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long regionId;

    private Integer year;

    private BigDecimal totalGeneration;

    private BigDecimal cumGrowthRate;

    private BigDecimal yoyGrowthRate;

    private LocalDate dataDate;

    private BigDecimal yoyCurrent;

    private BigDecimal yoyLast;

    private String rangeLabel;

    private Integer rangeCount;

    private String infraType;

    private Integer infraCount;

    private BigDecimal infraLength;

    private BigDecimal infraPower;

    private BigDecimal radarValue;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
