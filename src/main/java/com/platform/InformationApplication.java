package com.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Information 知识付费平台单体应用启动类
 * 
 * @author Platform Team
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.platform.*.mapper")
public class InformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformationApplication.class, args);
        System.out.println("=================================================");
        System.out.println("    Information 知识付费平台启动成功!");
        System.out.println("    API文档地址: http://localhost:8080/swagger-ui.html");
        System.out.println("    管理后台: http://localhost:8080/admin");
        System.out.println("=================================================");
    }
}