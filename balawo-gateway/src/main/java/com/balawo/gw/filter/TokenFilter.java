package com.balawo.gw.filter;

import com.alibaba.fastjson.JSON;
import com.balawo.gw.config.AuthorizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 将token信息传递到下游服务器中
 * @author yan
 * @date 2022-09-01
 */

@Component
public class TokenFilter implements GlobalFilter, Ordered {
    @Autowired
    TokenStore tokenStore;

    Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("网关过滤 TokenFilter......前置: " +exchange.getRequest().getBody() + exchange.getResponse().getStatusCode() + "\t" + exchange.getRequest().getURI().toString());
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        String token = null;
        if (authHeader != null){
            String tokenValue = authHeader.get(0);
            tokenValue = tokenValue.replace("Bearer","").trim();
            token = buildToken(tokenValue);
            logger.info("token==={}",token);
//            OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(token);
//            logger.info("网关过滤 TokenFilter......前置auth2Authentication: " + auth2Authentication);
//            if (auth2Authentication == null) {
//                return null;
//            }
//            String clientId = auth2Authentication.getOAuth2Request().getClientId();
        }
        if (token != null) {
            // 定义新的消息头
//            ServerHttpRequest request = exchange.getRequest();
//            request = request.mutate()
//                    .header("json-token", token)
//                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.put("json-token", Collections.singletonList(token));
            logger.info("Collections.singletonList(token)========{}",Collections.singletonList(token));
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove("Authorization");
            ServerHttpRequest host = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            };
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);

        }else {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

//            //重定向(redirect)到登录页面
//            ServerHttpResponse response = exchange.getResponse();
//            String url = "http://www.baidu.com";
//            //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            response.getHeaders().set(HttpHeaders.LOCATION, url);
//            return chain.filter(exchange);
//            return response.setComplete();
				logger.warn(" 后置 : " + exchange.getResponse().getStatusCode() + "\t"+ exchange.getRequest().getURI().toString());
            }));

        }

    }

    @Override
    public int getOrder() {
        return 0;
    }



    /**
     * redis token 转发明文给微服务
     *
     * @return
     */
    private String buildToken(String tokenValue) {

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(tokenValue);
        if (auth2Authentication == null) {
            return null;
        }
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        String userInfoStr = clientId;
        List<String> authorities = new ArrayList<>();
        if (userAuthentication != null) {
            User loginUser = (User) userAuthentication.getPrincipal();
            userInfoStr = JSON.toJSONString(loginUser);
            // 组装明文token，转发给微服务，放入header，名称为json-token
            userAuthentication.getAuthorities().stream().forEach(
                    s -> authorities.add(((GrantedAuthority) s).getAuthority())
            );
        }

        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String, String> requestParams = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParams);
        jsonToken.put("principal", userInfoStr);
        jsonToken.put("authorities", authorities);
        return Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes(StandardCharsets.UTF_8));
    }

}
