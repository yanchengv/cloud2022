package com.balawo.gw;

import com.balawo.gw.config.AuthorizationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yan
 * @date 2022-08-31
 * 参考：https://segmentfault.com/a/1190000040574542
 */

@SpringBootApplication
@EnableFeignClients
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);

    }
}
