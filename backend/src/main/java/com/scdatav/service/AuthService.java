package com.scdatav.service;

import com.scdatav.entity.SysUser;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(String username, String password);

    void register(String username, String password, String nickname);

    SysUser getUserInfo(String username);
}
