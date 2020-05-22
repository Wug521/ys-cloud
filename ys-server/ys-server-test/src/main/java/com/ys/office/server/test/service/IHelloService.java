package com.ys.office.server.test.service;

import com.ys.office.common.entity.ServerConstant;
import com.ys.office.server.test.service.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = ServerConstant.YS_SERVER_SYSTEM,contextId = "helloServiceClient",fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}
