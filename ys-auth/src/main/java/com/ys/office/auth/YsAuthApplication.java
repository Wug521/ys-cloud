package com.ys.office.auth;

import com.ys.office.common.annotation.EnableLettuceRedis;
import com.ys.office.common.annotation.YsCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@YsCloudApplication
@EnableLettuceRedis
@MapperScan("com.ys.office.auth.mapper")
public class YsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(YsAuthApplication.class, args);
    }

}
