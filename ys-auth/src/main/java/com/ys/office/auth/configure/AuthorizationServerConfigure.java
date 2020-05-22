package com.ys.office.auth.configure;

import com.ys.office.auth.properties.AuthProperties;
import com.ys.office.auth.properties.ClientsProperties;
import com.ys.office.auth.service.UserDetailService;
import com.ys.office.auth.translator.WebResponseExceptionTranslator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private WebResponseExceptionTranslator exceptionTranslator;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        ClientsProperties[] clientsProperties = authProperties.getClients();
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(clientsProperties)) {
            for (ClientsProperties f : clientsProperties) {
                if (StringUtils.isBlank(f.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(f.getSecret())) {
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(f.getGrantType(), ",");
                builder.withClient(f.getClient()).
                        secret(passwordEncoder.encode(f.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(f.getScope())
                        .accessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
            }
        }

    }


    @Override
    @SuppressWarnings("all")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .exceptionTranslator(exceptionTranslator);
    }


    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


//    /**
//     * 令牌的基本配置
//     * 令牌有效时间为60 * 60 * 24秒
//     * 刷新令牌有效时间为60 * 60 * 24 * 7秒
//     * setSupportRefreshToken设置为true表示开启刷新令牌的支持
//     *
//     * @return
//     */
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices() {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(tokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setAccessTokenValiditySeconds(ysAuthProperties.getAccessTokenValiditySeconds());
//        tokenServices.setRefreshTokenValiditySeconds(ysAuthProperties.getRefreshTokenValiditySeconds());
//        return tokenServices;
//    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) accessTokenConverter.getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        accessTokenConverter.setSigningKey("ys");
        return accessTokenConverter;
    }

}
