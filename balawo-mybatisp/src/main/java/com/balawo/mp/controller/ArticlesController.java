package com.balawo.mp.controller;

import com.balawo.mp.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/mp/articles")
public class ArticlesController {
    @Autowired
    private TagsService tagsService;

    @GetMapping("/index")
    public Long index(){
        return tagsService.count();
    }

}
