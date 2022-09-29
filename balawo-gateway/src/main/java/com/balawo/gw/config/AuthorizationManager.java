package com.balawo.gw.config;

import com.balawo.gw.exception.auth.RequiredAuthenticationException;
import com.balawo.gw.exception.auth.RequiredPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * @author yan
 * @date 2022-09-01
 * 自定义授权管理器，判断用户是否有权限访问
 *
 * 此处我们简单判断
 * 1、判断token令牌是否有效
 * 2、判断某个请求(url)用户是否有权限访问。
 * 3、所有不存在的请求(url)直接无权限访问。
 * ReactiveAuthorizationManager<AuthorizationContext>
 */

public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final String RESOURCE_ID = "admin";
    TokenStore tokenStore;
    private List<String> scopes = new ArrayList<>();


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
        //获取前端传来的token
        token = token.replace("Bearer","").trim();
        logger.info("访问的token:{}",token);
        //根据token查询tokenStore（redis存储）中对应的权限
        OAuth2Authentication auth = tokenStore.readAuthentication(token);
//        Object auth = tokenStore.readAuthentication(token);

        logger.info("访问的token:{},拥有的scope权限:{}",token,auth.getOAuth2Request().getScope());
        String path = request.getURI().getPath();
        logger.info("访问路径:{},所需要的scope权限是:{}", path, this.scopes);

        logger.info("当前登录的用户:{}", auth.getPrincipal());
        logger.info("当前用户的authorities:{}", auth.getAuthorities());

        if(auth == null){
            //没有权限，则拒绝访问
//            throw new RequiredPermissionException("没有权限。。。");
            return Mono.just(new AuthorizationDecision(false));
        }

        Collection<String> resourceIds = auth.getOAuth2Request().getResourceIds();
        if(resourceIds != null && !resourceIds.isEmpty() && !resourceIds.contains(RESOURCE_ID)){
            //没有admin的权限，则拒绝访问
            throw new RequiredAuthenticationException(msgBadCredentials);
        }

        //获取当前用户拥有的权限
        Set<String> clientScopes = auth.getOAuth2Request().getScope();
        for(String scope : this.scopes){
            if(clientScopes.contains(scope)){
                //有此权限 可以访问
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        return Mono.just(new AuthorizationDecision(false));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return null;
    }
}
