package com.eyslce.wx.mp;

import com.eyslce.wx.commons.util.WxConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.eyslce.wx.mp", "com.eyslce.wx.commons"})
@EnableConfigurationProperties(value = WxConfigurationProperties.class)
@EnableCaching
public class WxMpApplication{

    public static void main(String[] args){
        SpringApplication.run(WxMpApplication.class,args);
    }
}
