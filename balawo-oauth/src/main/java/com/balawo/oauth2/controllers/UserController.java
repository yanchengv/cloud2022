package com.balawo.oauth2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
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
}
