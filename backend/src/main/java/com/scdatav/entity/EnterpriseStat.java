package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("enterprise_stat")
public class EnterpriseStat {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long regionId;

    private Integer year;

    private String sizeRange;

    private Integer count;

    private BigDecimal revenueTotal;

    private Integer enterpriseCount;

    private Integer quarter;

    private BigDecimal quarterValue;

    private BigDecimal yoyCurrent;

    private BigDecimal yoyLast;

    private LocalDate yoyDate;

    private String patentNo;

    private BigDecimal penaltyAmount;

    private BigDecimal yoyPercent;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
