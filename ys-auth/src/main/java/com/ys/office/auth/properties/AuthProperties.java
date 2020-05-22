package com.ys.office.auth.properties;


import com.ys.office.common.properties.ValidateCodeProperties;
import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:ys-auth.properties"})
@ConfigurationProperties(prefix = "ys.auth")
public class AuthProperties {

    private ClientsProperties[] clients = {};
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    //验证码配置类
    private ValidateCodeProperties code = new ValidateCodeProperties();


    // 免认证路径
    private String anonUrl;

}
