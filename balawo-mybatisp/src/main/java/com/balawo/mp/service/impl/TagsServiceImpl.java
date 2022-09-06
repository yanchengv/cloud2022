package com.balawo.mp.service.impl;

import com.balawo.mp.entity.Tags;
import com.balawo.mp.mapper.TagsMapper;
import com.balawo.mp.service.TagsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {


    @Override
    public IPage<Tags> getTagList(Integer currentPage) {
        Page page = new Page(currentPage,2);
       IPage<Tags> list =  this.baseMapper.getList(page);
        return list;
    }
}
