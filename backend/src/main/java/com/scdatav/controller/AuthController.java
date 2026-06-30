package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.entity.SysUser;
import com.scdatav.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "认证接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.ok(authService.login(username, password));
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody Map<String, String> params) {
        authService.register(
                params.get("username"),
                params.get("password"),
                params.get("nickname"));
        return Result.ok();
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<SysUser> info(Authentication authentication) {
        return Result.ok(authService.getUserInfo(authentication.getName()));
    }
}
