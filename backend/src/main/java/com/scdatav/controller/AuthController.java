package com.scdatav.controller;

import com.scdatav.common.Result;
import com.scdatav.entity.SysUser;
import com.scdatav.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "认证接口", description = "用户登录、注册、信息查询")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "用户登录",
            description = "使用用户名和密码登录，返回 JWT Token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "登录成功"),
                    @ApiResponse(responseCode = "500", description = "用户名或密码错误")
            }
    )
    @PostMapping("/login")
    public Result<Map<String, Object>> login(
            @Parameter(description = "登录参数", required = true)
            @RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        return Result.ok(authService.login(username, password));
    }

    @Operation(
            summary = "用户注册",
            description = "注册新用户，默认角色为 USER",
            responses = {
                    @ApiResponse(responseCode = "200", description = "注册成功"),
                    @ApiResponse(responseCode = "500", description = "用户名已存在")
            }
    )
    @PostMapping("/register")
    public Result<Void> register(
            @Parameter(description = "注册参数", required = true)
            @RequestBody Map<String, String> params) {
        authService.register(
                params.get("username"),
                params.get("password"),
                params.get("nickname"));
        return Result.ok();
    }

    @Operation(
            summary = "获取当前用户信息",
            description = "根据 JWT Token 获取当前登录用户的信息",
            responses = {
                    @ApiResponse(responseCode = "200", description = "成功"),
                    @ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @GetMapping("/info")
    public Result<SysUser> info(Authentication authentication) {
        return Result.ok(authService.getUserInfo(authentication.getName()));
    }
}
