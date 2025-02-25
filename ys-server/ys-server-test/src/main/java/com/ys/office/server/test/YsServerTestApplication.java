package com.ys.office.server.test;

import com.ys.office.common.annotation.YsCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@YsCloudApplication
public class YsServerTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(YsServerTestApplication.class, args);
    }

}
