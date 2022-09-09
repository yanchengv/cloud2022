package com.balawo.gw.exception;
import com.balawo.gw.exception.auth.RequiredPermissionException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yan
 * @date 2022-09-07
 */

@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        log.debug("Request URI: {}", request.uri());
        // 添加自己处理异常的逻辑
        String msg = error.getMessage();
        log.error(msg);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> errorMap = new HashMap<>();
//        if (error instanceof RequiredAuthenticationException) {
//            errorMap.put("code", LeKaiErrorCode.ERROR_10007.getCode());
//            errorMap.put("message", LeKaiErrorCode.ERROR_10007.getMessage());
//            result.put("error", errorMap);
//        } else if (error instanceof RequiredPermissionException) {
//            errorMap.put("code", LeKaiErrorCode.ERROR_10003.getCode());
//            errorMap.put("message", LeKaiErrorCode.ERROR_10003.getMessage());
//            result.put("error", errorMap);
//        } else {
//            errorMap.put("code", LeKaiErrorCode.ERROR_11001.getCode());
//            errorMap.put("message", LeKaiErrorCode.ERROR_11001.getMessage());
//            result.put("error", errorMap);
//        }

        errorMap.put("code", 500);
        errorMap.put("message", "哈哈哈错误");
        result.put("error", errorMap);
        result.put("data", null);
        result.put("success", false);

        return result;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return 200;
    }
}
