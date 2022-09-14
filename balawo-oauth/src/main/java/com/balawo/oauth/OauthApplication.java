package com.balawo.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 负责认证授权颁发令牌token的微服务
 * @author yan
 * @date 2022-09-07
 */

@SpringBootApplication
public class OauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthApplication.class,args);
    }

}
