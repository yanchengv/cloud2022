package com.balawo.gw.filter;

import com.balawo.gw.config.AuthorizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 将token信息传递到下游服务器中
 * @author yan
 * @date 2022-09-01
 */

@Component
public class TokenFilter implements GlobalFilter, Ordered {
    Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("网关过滤 TokenFilter......前置: " +exchange.getRequest().getBody() + exchange.getResponse().getStatusCode() + "\t" + exchange.getRequest().getURI().toString());
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        String token = null;
        if (authHeader != null){
            String tokenValue = authHeader.get(0);
            token = tokenValue.replace("bearer","").trim();

        }
        if (token != null) {
            ServerHttpRequest request = exchange.getRequest();
            request = request.mutate()
                    .header("tokenInfo", token)
                    .build();
            //将现在的request 变成 change对象
            ServerWebExchange build = exchange.mutate().request(request).build();
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

}
