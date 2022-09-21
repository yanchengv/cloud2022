package com.balawo.oauth;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author yan
 * @date 2022-09-17
 */
@SpringBootTest
public class OauthApplicationTest {

    @Test
    public void contextLoads(){
        PasswordEncoder p = new BCryptPasswordEncoder();
        String p1 = p.encode("123");
        System.out.println(p1);
        System.out.println(p.matches("1233","$2a$10$391zbf4v2TPk7tB5tWHRz.pPAd5660hCN4/accsMVs..eFfhwTome"));
    }
}
