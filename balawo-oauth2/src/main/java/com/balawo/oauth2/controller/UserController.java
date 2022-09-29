package com.balawo.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yan
 * @date 2022-09-21
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/getCurrentUser")
    @PreAuthorize("hasAnyAuthority('read_auth')")
    public Object getCurrentUser(Authentication authentication){
        return authentication.getPrincipal();
    }
}
