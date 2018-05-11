package com.eyslce.wx.mp.test;


import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.entity.InputText;
import com.eyslce.wx.commons.entity.Perception;
import com.eyslce.wx.commons.entity.UserInfo;
import com.eyslce.wx.commons.query.TuLingApiQuery;
import com.eyslce.wx.commons.util.HttpClient;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.WxMpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxMpApplication.class)
public class TuLingApiTest {
    @Autowired
    private WxConfigurationProperties properties;

    private TuLingApiQuery getTuLingApiQuery(String text) {
        return TuLingApiQuery.builder()
                .userInfo(UserInfo.builder()
                        .apiKey(properties.getTulingApiKey())
                        .userId(properties.getTulingUserId())
                        .build())
                .perception(Perception.builder()
                        .inputText(InputText.builder()
                                .text(text)
                                .build())
                        .build())
                .build();
    }

    @Test
    public void send() {
        String params = JSON.toJSONString(getTuLingApiQuery("查询快递"));
        try {
            String res = HttpClient.post(properties.getTulingApiUrl(), params);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String p = JSON.toJSONString(getTuLingApiQuery("889362541469824436"));
        try {
            String res = HttpClient.post(properties.getTulingApiUrl(), p);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
