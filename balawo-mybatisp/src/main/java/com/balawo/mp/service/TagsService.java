package com.balawo.mp.service;

import com.balawo.mp.entity.Tags;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */
public interface TagsService extends IService<Tags> {

    IPage<Tags> getTagList(Integer currentPage);
}
