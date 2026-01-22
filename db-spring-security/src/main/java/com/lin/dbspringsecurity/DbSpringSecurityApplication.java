package com.lin.dbspringsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lin.dbspringsecurity.mapper")
public class DbSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbSpringSecurityApplication.class, args);
    }

}
