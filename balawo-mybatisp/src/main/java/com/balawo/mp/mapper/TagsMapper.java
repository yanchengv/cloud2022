package com.balawo.mp.mapper;

import com.balawo.mp.entity.Tags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2022-07-01
 */

public interface TagsMapper extends BaseMapper<Tags> {

    IPage<Tags> getList(IPage<Tags> page);

}
