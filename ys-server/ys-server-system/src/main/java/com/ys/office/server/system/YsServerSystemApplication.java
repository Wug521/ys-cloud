package com.ys.office.server.system;

import com.ys.office.common.annotation.YsCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableDiscoveryClient
@SpringBootApplication
//表示开启Spring Cloud Security权限注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
@YsCloudApplication
public class YsServerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(YsServerSystemApplication.class, args);
    }

}
