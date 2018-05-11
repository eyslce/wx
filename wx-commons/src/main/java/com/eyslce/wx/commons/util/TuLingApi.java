package com.eyslce.wx.commons.util;

import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.entity.*;
import com.eyslce.wx.commons.query.TuLingApiQuery;
import com.eyslce.wx.commons.result.TulingApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TuLingApi {
    @Autowired
    public WxConfigurationProperties properties;

    private Logger logger = LoggerFactory.getLogger(TuLingApi.class);

    public static TuLingApi tuLingApi;

    @PostConstruct
    public void init() {
        tuLingApi = this;
    }

    /**
     * 请求图灵API
     * 直接构造完整请求内容
     *
     * @param query
     * @return
     */
    public static TulingApiResult call(TuLingApiQuery query) {
        TulingApiResult tulingApiResult = new TulingApiResult();
        String params = JSON.toJSONString(query);
        try {
            String res = HttpClient.post(tuLingApi.properties.getTulingApiUrl(), params);
            tulingApiResult = JSON.parseObject(res, TulingApiResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            tuLingApi.logger.error("" + e.getMessage());
        }
        return tulingApiResult;
    }

    /**
     * 请求图灵API
     *
     * @param perception
     * @return
     */
    public static TulingApiResult call(Perception perception) {
        int reqType = 0;
        if (perception.getInputMedia() != null) {
            reqType = 2;
        } else if (perception.getInputImage() != null) {
            reqType = 1;
        }
        TuLingApiQuery query = TuLingApiQuery.builder()
                .perception(perception)
                .userInfo(UserInfo.builder()
                        .apiKey(tuLingApi.properties.getTulingApiKey())
                        .userId(tuLingApi.properties.getTulingUserId())
                        .build())
                .reqType(reqType)
                .build();
        return call(query);
    }

    /**
     * @param text
     * @return
     */
    public static TulingApiResult call(String text) {
        Perception perception = Perception.builder()
                .inputText(InputText.builder()
                        .text(text)
                        .build())
                .build();
        return call(perception);
    }

    /**
     *
     * @param inputMedia
     * @return
     */
    public static TulingApiResult call(InputMedia inputMedia) {
        Perception perception = Perception.builder()
                .inputMedia(inputMedia)
                .build();
        return call(perception);
    }

    /**
     *
     * @param inputImage
     * @return
     */
    public static TulingApiResult call(InputImage inputImage) {
        Perception perception = Perception.builder()
                .inputImage(inputImage)
                .build();
        return call(perception);
    }

    /**
     *
     * @param inputText
     * @return
     */
    public static TulingApiResult call(InputText inputText) {
        Perception perception = Perception.builder()
                .inputText(inputText)
                .build();
        return call(perception);
    }
}
