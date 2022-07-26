package com.jhj.gulimall.product.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class GuliFeginConfig {

    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {

                //拿到刚进来的原请求信息
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request=requestAttributes.getRequest();
                //同步请求头数据  例如Cookie requestTemplate是新的要发送的请求
                String cookie = request.getHeader("Cookie");
                requestTemplate.header("Cookie",cookie);

            }
        };
    }
}
