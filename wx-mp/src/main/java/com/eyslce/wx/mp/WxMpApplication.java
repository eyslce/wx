package com.eyslce.wx.mp;

import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.controller.ErrorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.eyslce.wx.mp", "com.eyslce.wx.commons"}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ErrorController.class)
})
@EnableConfigurationProperties(value = WxConfigurationProperties.class)
public class WxMpApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WxMpApplication.class);
    }

    public static void main(String[] args){
        SpringApplication.run(WxMpApplication.class,args);
    }
}
