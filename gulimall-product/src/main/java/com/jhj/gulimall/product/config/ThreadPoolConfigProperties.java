package com.jhj.gulimall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//配置 绑定
@ConfigurationProperties(prefix = "gulimall.thread")
@Component//这里写了 component 则不用在使用的地方开启配置 即mythreadconfig @EnableConfigurationProperties(ThreadPoolConfigProperties.class)
@Data
public class ThreadPoolConfigProperties {
    private Integer coreSize;
    private Integer maxSize;
    private Integer keepAliveTime;
}
