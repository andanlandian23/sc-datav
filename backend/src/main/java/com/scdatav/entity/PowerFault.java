package com.scdatav.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("power_fault")
public class PowerFault {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String faultName;

    private Integer anomalyCount;

    private Integer alarmCount;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
