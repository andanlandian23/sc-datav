package com.scdatav.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scdatav.common.PageResult;
import com.scdatav.common.Result;
import com.scdatav.entity.SysUser;
import com.scdatav.mapper.SysUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin-用户管理")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "用户列表")
    @GetMapping
    public Result<PageResult<SysUser>> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(SysUser::getUsername, keyword)
                    .or().like(SysUser::getNickname, keyword);
        }
        wrapper.orderByDesc(SysUser::getCreateTime);

        Page<SysUser> pageObj = sysUserMapper.selectPage(new Page<>(page, size), wrapper);
        pageObj.getRecords().forEach(u -> u.setPassword(null));

        return Result.ok(new PageResult<>(
                pageObj.getRecords(), pageObj.getTotal(), pageObj.getCurrent(), pageObj.getSize()));
    }

    @Operation(summary = "创建用户")
    @PostMapping
    public Result<Void> create(@RequestBody SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("USER");
        if (user.getStatus() == null) user.setStatus(1);
        sysUserMapper.insert(user);
        return Result.ok();
    }

    @Operation(summary = "更新用户")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SysUser user) {
        user.setId(id);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null); // 不修改密码
        }
        sysUserMapper.updateById(user);
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        sysUserMapper.deleteById(id);
        return Result.ok();
    }

    @Operation(summary = "切换用户状态")
    @PutMapping("/{id}/status")
    public Result<Void> toggleStatus(@PathVariable Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user != null) {
            user.setStatus(user.getStatus() == 1 ? 0 : 1);
            sysUserMapper.updateById(user);
        }
        return Result.ok();
    }
}
