package com.jhj.gulimall.ware.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description MybatisConfig
 * @Author jhj
 * @Date 2022/6/116:22
 **/
//开启事务
@EnableTransactionManagement
@Configuration
@MapperScan("com.jhj.gulimall.*.mapper.*")
public class MybatisConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true);
        paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
