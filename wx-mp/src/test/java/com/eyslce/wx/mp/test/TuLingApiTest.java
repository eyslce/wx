package com.eyslce.wx.mp.test;


import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.result.TulingApiResult;
import com.eyslce.wx.commons.util.FileOperation;
import com.eyslce.wx.commons.util.TuLingApi;
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
   private FileOperation fileOperation;

    @Test
    public void test() {
        fileOperation.downBingImg();
    }
}
