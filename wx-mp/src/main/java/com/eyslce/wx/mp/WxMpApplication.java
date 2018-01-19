package com.eyslce.wx.mp;

import com.eyslce.wx.commons.util.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(value = Configuration.class)
public class WxMpApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WxMpApplication.class);
    }

    public static void main(String[] args){
        SpringApplication.run(WxMpApplication.class,args);
    }
}
