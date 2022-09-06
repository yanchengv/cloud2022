package com.balawo.mp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/mp/users")
public class UsersController {
    Logger logger = LoggerFactory.getLogger(UsersController.class);
    @GetMapping("/index")
    public String index (){
        logger.warn("UsersController index.....");

        return "users index";
    }

}
