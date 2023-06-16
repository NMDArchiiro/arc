package com.arc.app.scheduled;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * author: NMDuc
 **/
@Configuration
@EnableScheduling
//@ConditionalOnExpression("${schedule.enable}")
public class ScheduleConfig {
    @Bean(name = "scheduling")
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler= new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler-");
        return threadPoolTaskScheduler;
    }
}
