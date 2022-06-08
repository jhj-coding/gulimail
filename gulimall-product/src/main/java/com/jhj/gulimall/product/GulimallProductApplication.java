package com.jhj.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 1. 整合Mybatis-plus
 *  1)导入依赖
 *  <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.3.1</version>
 *         </dependency>
 *  2）配置
 *      1、配置数据源
 *          导入数据库驱动 驱动直接写入pom文件
 *          在application.yml配置数据源信息
 *      2、配置mybatis-plus
 *          使用@MapperScan 配置 mapper接口
 *          配置sql映射文件 主键自增
 */
@EnableFeignClients(basePackages = "com.jhj.gulimall.product.fegin")
@EnableDiscoveryClient
@MapperScan("com.jhj.gulimall.product.dao")
@SpringBootApplication()
public class GulimallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }

}
