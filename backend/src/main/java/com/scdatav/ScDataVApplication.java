package com.scdatav;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.scdatav.mapper")
public class ScDataVApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScDataVApplication.class, args);
    }
}
