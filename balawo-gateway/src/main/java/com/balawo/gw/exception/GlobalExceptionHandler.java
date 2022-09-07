package com.balawo.gw.exception;
import com.balawo.gw.exception.auth.RequiredPermissionException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yan
 * @date 2022-09-07
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RequiredPermissionException.class)
    public Map<String,Object> handleException(){
        Map<String, Object> result = new HashMap<>();
        result.put("code",444);
        result.put("msg","错误呜呜呜");
        return result;
    }
}
