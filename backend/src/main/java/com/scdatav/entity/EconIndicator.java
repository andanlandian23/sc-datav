package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("econ_indicator")
public class EconIndicator {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long regionId;

    private String indicator;

    private LocalDate dataDate;

    private BigDecimal value;

    private BigDecimal value2;

    private Integer year;

    private Integer quarter;

    private String label;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
