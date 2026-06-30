package com.scdatav.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.scdatav.entity.SysUser;
import com.scdatav.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // 确保 admin 密码是正确的 BCrypt 哈希
        SysUser admin = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, "admin"));

        if (admin != null) {
            String currentHash = admin.getPassword();
            // 验证当前哈希是否有效
            try {
                if (!passwordEncoder.matches("admin123", currentHash)) {
                    log.info("更新 admin 密码哈希...");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    sysUserMapper.updateById(admin);
                    log.info("admin 密码已更新");
                } else {
                    log.info("admin 密码哈希有效");
                }
            } catch (Exception e) {
                log.info("密码哈希无效，重新生成...");
                admin.setPassword(passwordEncoder.encode("admin123"));
                sysUserMapper.updateById(admin);
                log.info("admin 密码已更新");
            }
        }
    }
}
