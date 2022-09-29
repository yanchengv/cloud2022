package com.balawo.oauth2;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
class Oauth2ApplicationTests {


    @Test
    public void testCreateJwtToken(){
        String encodedString = Base64.getEncoder().encodeToString("xxx".getBytes());

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("1000")
                .setSubject("yan")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,encodedString);


        //获取jwt的token
        String token = jwtBuilder.compact();
        System.out.println(token);
        System.out.println("=====================");
        String[] str = token.split("\\.");
        System.out.println(Base64Codec.BASE64.decodeToString(str[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(str[1]));
        System.out.println(Base64Codec.BASE64.decodeToString(str[2]));
    }

    //解析token
    @Test
    public void testParseToken(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMDAwIiwic3ViIjoieWFuIn0.ZfisVIXC2z0G8tkzl2WJotdDI-ATfwMlAUxFcIQw7tI";
        Claims claims = Jwts.parser()
                .setSigningKey("xxx".getBytes())
                .parseClaimsJws(token)
                .getBody();

        System.out.println("============");
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
    }

    @Test
    public void testArrayList() throws UnsupportedEncodingException {
//        System.out.println(Arrays.asList("admin","admin2"));
//        System.out.println(Arrays.asList("admin,admin2"));
//        System.out.println(AuthorityUtils.commaSeparatedStringToAuthorityList("admin,1,admin2"));
        String salt = "2VXr8jXSv1Fq5j3eQJ3ZEUm8rxmONfsB";
        String pwd = "Wangxia1234";

        String str = String.valueOf(salt.charAt(0)) + String.valueOf(salt.charAt(3)) + pwd + String.valueOf(salt.charAt(5)) + salt;
        String mdsStr = DigestUtils.md5DigestAsHex(str.getBytes("utf-8")).toUpperCase();
        System.out.println(mdsStr);
    }


}
