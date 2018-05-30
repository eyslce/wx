package com.eyslce.wx.mp.test;


import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.result.TulingApiResult;
import com.eyslce.wx.commons.util.FileOperation;
import com.eyslce.wx.commons.util.TuLingApi;
import com.eyslce.wx.mp.WxMpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WxMpApplication.class)
public class TuLingApiTest {

    @Test
    public void send() {
        TulingApiResult tulingApiResult = TuLingApi.call("查询快递");
        String params = JSON.toJSONString(tulingApiResult);
        System.out.println(params);
        TulingApiResult result = TuLingApi.call("889362541469824436");
        String p = JSON.toJSONString(result);
        System.out.println(p);
    }

    @Test
    public void test() {
        FileOperation.downBingImg();
    }
}
