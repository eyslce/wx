package com.eyslce.wx.mp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SchedulingConfiguration {
    @Bean
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(10);
    }
}
