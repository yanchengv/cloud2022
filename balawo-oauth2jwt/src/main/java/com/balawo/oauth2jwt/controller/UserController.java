package com.balawo.oauth2jwt.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author yan
 * @date 2022-09-23
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/getCurrentUser")
    public Object index(Authentication authentication, HttpServletRequest request){
        String head = request.getHeader("Authorization");
        String token = head.substring(head.indexOf("bearer")+7);
        Object obj  =  Jwts.parser()
            .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return obj;
    }
}
