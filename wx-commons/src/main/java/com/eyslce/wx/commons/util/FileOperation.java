package com.eyslce.wx.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@Component
public class FileOperation {

    private final static Logger logger = LoggerFactory.getLogger(FileOperation.class);
    private final static String bingUrl = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&nc=1501558320736&pid=hp";
    private final static String BING_BASE_URL = "http://cn.bing.com";
    public final static String BACKGROUND_IMAGE_NAME = "login-bg.jpg";

    @Autowired
    private WxConfigurationProperties wxConfig;

    /**
     * 下载必应图片
     */
    public void downBingImg() {
        try {
            String imgUrl = BING_BASE_URL + getBingImgUrl();
            URL url = new URL(imgUrl);
            File file = new File(wxConfig.getUpload_dir(), BACKGROUND_IMAGE_NAME);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            int b;
            while ((b = bis.read()) != -1) {
                bos.write(b);
            }
            bis.close();
            bos.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 获取必应背景图地址
     *
     * @return
     * @throws Exception
     */
    public static String getBingImgUrl() throws Exception {
        String response = HttpClient.get(bingUrl);
        //解析为json对象
        JSONObject jsonObject = JSON.parseObject(response);
        //获取图片数组
        JSONArray jsonArray = jsonObject.getJSONArray("images");
        //获取第一张图片
        JSONObject object = jsonArray.getJSONObject(0);
        //获取图片地址
        return object.getString("url");
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        String ext = fileName.substring(index + 1, fileName.length() - 1);
        return ext;
    }
}
