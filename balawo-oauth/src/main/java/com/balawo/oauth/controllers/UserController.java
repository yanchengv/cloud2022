package com.balawo.oauth.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 * @date 2022-09-07
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/index")
    public String index (){
        return "demo2 oauth2 users index";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public String admin(){
        return "admin role";
    }
}
