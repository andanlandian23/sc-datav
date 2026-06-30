package com.scdatav.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scdatav.common.PageResult;
import com.scdatav.common.Result;
import com.scdatav.entity.SysLog;
import com.scdatav.mapper.SysLogMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin-操作日志")
@RestController
@RequestMapping("/api/admin/logs")
@RequiredArgsConstructor
public class AdminLogController {

    private final SysLogMapper sysLogMapper;

    @Operation(summary = "日志列表")
    @GetMapping
    public Result<PageResult<SysLog>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String username) {

        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            wrapper.like(SysLog::getUsername, username);
        }
        wrapper.orderByDesc(SysLog::getCreateTime);

        Page<SysLog> p = sysLogMapper.selectPage(new Page<>(page, size), wrapper);
        return Result.ok(new PageResult<>(p.getRecords(), p.getTotal(), p.getCurrent(), p.getSize()));
    }
}
