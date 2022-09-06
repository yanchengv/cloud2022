package com.balawo.mp.service.impl;

import com.balawo.mp.entity.Articles;
import com.balawo.mp.mapper.ArticlesMapper;
import com.balawo.mp.service.ArticlesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesMapper, Articles> implements ArticlesService {

}
