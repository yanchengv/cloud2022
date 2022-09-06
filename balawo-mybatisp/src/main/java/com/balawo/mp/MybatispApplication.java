package com.balawo.mp;

import com.balawo.mp.service.TagsService;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yan
 * @date 2022-06-29
 */

@SpringBootApplication
// 扫描包mapper注解
@MapperScan("com.balawo.mp.mapper")
public class MybatispApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatispApplication.class,args);

    }
}
