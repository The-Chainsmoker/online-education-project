package com.atguigu.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

//访问地址:http://localhost:8001/
@SpringBootApplication
@EnableDiscoveryClient//开启nacos注册
@EnableFeignClients//开启调用nacos服务(调用service-vod服务)
@ComponentScan(basePackages = {"com.atguigu"})//模糊扫描所有的com.atguigu包
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }
}

//引入别的项目的文件(首先在pom文件引入别的项目的依赖)
