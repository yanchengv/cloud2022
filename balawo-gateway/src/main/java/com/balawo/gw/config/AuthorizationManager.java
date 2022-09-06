package com.balawo.gw.config;

import com.balawo.gw.exception.RequiredAuthenticationException;
import com.balawo.gw.exception.RequiredPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yan
 * @date 2022-09-01
 * 自定义授权管理器，判断用户是否有权限访问
 *
 * 此处我们简单判断
 * 1、放行所有的 OPTION 请求。
 * 2、判断某个请求(url)用户是否有权限访问。
 * 3、所有不存在的请求(url)直接无权限访问。
 * ReactiveAuthorizationManager<AuthorizationContext>
 */

public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final String RESOURCE_ID = "admin";

    TokenStore tokenStore;
    private List<String> scopes = new ArrayList<>();
    private static final Map<String, String> AUTH_MAP = new HashMap<>();


    public AuthorizationManager(TokenStore tokenStore, String scope, String... scopes) {
        this.tokenStore = tokenStore;
        this.scopes.add(scope);
        if(this.scopes != null) {
            for(String s : scopes) {
                this.scopes.add(s);
            }
        }
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);
        String msgBadCredentials = "认证异常，请重新登录......";
        String msgAccessDenied = "权限不足.....";
        ServerWebExchange exchange = authorizationContext.getExchange();
        ServerHttpRequest request =  exchange.getRequest();
        List<String> authHeader = request.getHeaders().get("Authorization");
        if(authHeader == null) {
            throw new RequiredAuthenticationException(msgBadCredentials);
        }
        String token = authHeader.get(0);
        if(StringUtils.isEmpty(token)){
            throw new RequiredAuthenticationException(msgBadCredentials);
        }
        token = token.replace("bearer","").trim();
        OAuth2Authentication auth = tokenStore.readAuthentication(token);
        logger.info("访问的token:[{}],拥有的权限:[{}]",token,auth);



        String path = request.getURI().getPath();
        //带通配符的可以使用这个进行匹配
        PathMatcher pathMatcher = new AntPathMatcher();
        String authorities = AUTH_MAP.get(path);
        logger.info("访问路径:[{}],所需要的权限是:[{}]", path, authorities);



        // option 请求，全部放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 不在权限范围内的url，全部拒绝
        if (!StringUtils.hasText(authorities)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        return Mono.just(new AuthorizationDecision(true));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }
}
