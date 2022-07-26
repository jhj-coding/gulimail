package com.jhj.gulimall.ssoclient.scheduled;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling //开启定时任务

@EnableAsync //开启异步
public class HelloSchedule {

    //与cron的区别
    //spring 只允许6位 不允许年
    // 在周几的位置 1-7表示周一到周日 ：MON-SUN

    /**
     *定时任务不应该阻塞 默认是阻塞的
     *以异步方式执行
     *支持定时任务线程池 配置 spring.task.scheduling.pool.size=5 不太好用
     * 让定时任务加入线程 在类上使用@EnableAsync 在方法上使用@Async
     **/
    @Async
    @Scheduled(cron = "* * * * * ?")
    public void Hello(){
        System.out.println("hello");
    }
}
