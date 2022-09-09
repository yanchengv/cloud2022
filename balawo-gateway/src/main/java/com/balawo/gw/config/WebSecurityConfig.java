package com.balawo.gw.config;

import com.balawo.gw.exception.auth.RequiredPermissionException;
import com.balawo.gw.exception.auth.RestAuthenticationEntryPoint;
import com.balawo.gw.exception.auth.RestfulAccessDeniedHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 资源服务器配置
 * @author yan
 * @date 2022-09-02
 */

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
@Slf4j
public class WebSecurityConfig {

    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final TokenStore tokenStore; //令牌存储，放在redis中


    /**
     * 安全验证机制
     * @param http
     * @throws Exception
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
        logger.info("网关 WebSecurityConfig......");
        http

                .authorizeExchange()
                //获取令牌token的服务接口全部放行
                .pathMatchers("/demo2*/**").permitAll()
                // 所有以 /auth/** 开头的请求全部放行
//                .pathMatchers("/auth/**", "/favicon.ico").permitAll()
                // 权限认证：不同接口访问权限不同
                //使用AuthorizationManager方法校验当前的用户token是否合法并且是否有ALL_ADMIN权限，如果有才能访问/demo1/mp/users/*
                .pathMatchers("/demo1/mp/users/*").access(new AuthorizationManager(tokenStore, "ALL_ADMIN"))
                //.pathMatchers("/demo1/mp/tags/*").access(new AuthorizationManager(tokenStore, "API_CUSTOMER"))
                .anyExchange().permitAll()
                .and().exceptionHandling()
                //没有权限和token未认证的处理
                .accessDeniedHandler(restfulAccessDeniedHandler) //处理未授权
                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
                .and().csrf().disable()
                ;
        return http.build();
    }

}
