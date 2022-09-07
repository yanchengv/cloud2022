package com.balawo.gw.exception.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**用户没有权限的处理
 * @author yan
 * @date 2022-09-01
 * 无权限访问异常
 * 自定义返回结果：没有权限访问时
 */


@Component
public class RestfulAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        return null;
    }
}





