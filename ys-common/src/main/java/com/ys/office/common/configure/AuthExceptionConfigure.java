package com.ys.office.common.configure;

import com.ys.office.common.handler.AccessDeniedHandler;
import com.ys.office.common.handler.AuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class AuthExceptionConfigure {


    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }


    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public AuthExceptionEntryPoint authExceptionEntryPoint() {
        return new AuthExceptionEntryPoint();
    }


}
