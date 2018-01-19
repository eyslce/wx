package com.eyslce.wx.commons.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.eyslce.wx")
public class Configuration {
    @Getter
    @Setter
    private mp mp;

    @Data
    public static class mp{
        private String deliveryApi;
        private String deliveryTypeApi;
        private String idApi;
    }

}
