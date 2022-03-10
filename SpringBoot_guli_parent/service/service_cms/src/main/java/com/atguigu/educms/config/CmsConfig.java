package com.atguigu.educms.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类，配置mapper扫描和其他
@Configuration
@MapperScan("com.atguigu.eduservice.mapper")
public class CmsConfig {

    //逻辑删除插件
    @Bean//在使用的是实体类里的属性加上@TableLogic
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    //分页插件
    @Bean//可以使用mybatis-puls的提供的分页方法
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
