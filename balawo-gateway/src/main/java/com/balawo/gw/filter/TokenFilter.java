package com.balawo.gw.filter;

import com.balawo.gw.config.AuthorizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
        logger.warn("网关过滤 TokenFilter......");
        Logger logger = LoggerFactory.getLogger(AuthorizationManager.class);
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
				logger.warn(" 后置 : " + exchange.getResponse().getStatusCode() + "\t"+ exchange.getRequest().getURI().toString());
            }));
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
