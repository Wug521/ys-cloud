package com.ys.office.common.configure;

import com.ys.office.common.entity.Constant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

public class OAuth2FeignConfigure {


    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return requestTemplate -> {
            // 添加 Zuul Token
            String zuulToken = new String(Base64Utils.encode(Constant.ZUUL_TOKEN_VALUE.getBytes()));
            requestTemplate.header(Constant.ZUUL_TOKEN_HEADER, zuulToken);

            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails){
                String token = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + token);
            }
        };
    }
}

