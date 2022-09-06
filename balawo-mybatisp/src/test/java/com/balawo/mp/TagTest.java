package com.balawo.mp;

import com.balawo.mp.entity.Tags;
import com.balawo.mp.service.impl.TagsServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yan
 * @date 2022-07-06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagTest {

    @Autowired
    TagsServiceImpl tagsService;

    @Test
    public  void TagList(){
        IPage<Tags> tags = tagsService.getTagList(2);
        System.out.println(1111111111);
        System.out.println(tags.getRecords());
    }

    //软删除
    @Test
    public void LogicDestroy(){
        tagsService.removeById(74);
    }
}
