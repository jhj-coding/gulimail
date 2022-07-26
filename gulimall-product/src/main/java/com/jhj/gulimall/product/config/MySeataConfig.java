package com.jhj.gulimall.product.config;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class MySeataConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties){
        HikariDataSource build = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(dataSourceProperties.getName())){
            build.setPoolName(dataSourceProperties.getName());
        }
        return new DataSourceProxy(build);
    }
}
