package com.jhj.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

/**
 * @Description CorsConfig
 * @Author jhj
 * @Date 2022/5/2415:59
 **/
@Configuration
public class GulimallCorsConfiguration {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //配置跨域
        //允许哪些头
        corsConfiguration.addAllowedHeader("*");
        //允许哪些请求方式
        corsConfiguration.addAllowedMethod("*");
        //允许哪些请求来源
        corsConfiguration.addAllowedOrigin("*");
        //允许携带cook
        corsConfiguration.setAllowCredentials(true);

        //哪些路径配置跨域
        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsWebFilter(source);
    }
}
