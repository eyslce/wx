package com.eyslce.wx.commons.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.eyslce.wx")
@Data
public class WxConfigurationProperties {
    private mp mp;
    private String tulingApiUrl;
    private String tulingApiKey;
    private String tulingUserId;

    @Data
    public static class mp{
        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;
        private String deliveryApi;
        private String deliveryTypeApi;
        private String idApi;
    }

}
