package com.ys.office.server.test.service.fallback;

import com.ys.office.server.test.service.IHelloService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloServiceFallback implements FallbackFactory<IHelloService> {


    @Override
    public IHelloService create(Throwable throwable) {
        return name -> {
            log.error("调用ys-server-system服务出错", throwable);
            return "调用出错";
        };
    }
}
