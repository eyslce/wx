package com.eyslce.wx.commons.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.eyslce.wx")
@Data
public class WxConfigurationProperties {
    private mp mp;
    private String upload_dir;
    private String tulingApiUrl;
    private String tulingApiKey;
    private String tulingUserId;

    @Data
    public static class mp {
        private String deliveryApi;
        private String deliveryTypeApi;
        private String idApi;
    }

}
