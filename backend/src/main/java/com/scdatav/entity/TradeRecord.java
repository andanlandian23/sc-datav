package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("trade_record")
public class TradeRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String typeName;

    private BigDecimal quantity;

    private BigDecimal tradeValue;

    private LocalDate dataDate;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
