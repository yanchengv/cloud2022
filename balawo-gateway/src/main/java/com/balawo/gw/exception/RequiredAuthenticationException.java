package com.balawo.gw.exception;

/**
 * @author yan
 * @date 2022-09-05
 */
public class RequiredAuthenticationException extends RuntimeException {
    public RequiredAuthenticationException(String msg){
        super(msg);
    }
}
