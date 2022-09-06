package com.balawo.mp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author yan
 * @date 2022-07-04
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("===========start insert fill ....");
        Date date = new Date();
        log.info( LocalDateTime.now().toString());
        Timestamp time = new Timestamp(date.getTime());
        log.info(new Timestamp(date.getTime()).toString());
        log.info("===========end insert fill ....");

        this.strictInsertFill(metaObject, "createdAt", () -> time, Timestamp.class); // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "updatedAt", () -> time, Timestamp.class); // 起始版本 3.3.0(推荐)

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedAt", () -> LocalDateTime.now(), LocalDateTime.class); // 起始版本 3.3.3(推荐)

    }
}
