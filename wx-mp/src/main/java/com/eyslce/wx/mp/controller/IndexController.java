package com.eyslce.wx.mp.controller;

import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.util.HttpClient;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.entity.DeliveryType;
import com.eyslce.wx.mp.entity.DeliveryTypeAuto;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    WxConfigurationProperties wxConfigurationProperties;

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello Wx Mp";
    }

    /**
     * 快递查询页面
     *
     * @return
     */
    @RequestMapping(value = "/delivery", method = RequestMethod.GET)
    public ModelAndView delivery() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("delivery");
        return modelAndView;
    }

    /**
     * 查询快递详情
     */
    @RequestMapping(value = "/delivery/search")
    @ResponseBody
    public String deliverySearch(@RequestParam(name = "postid") String postid) {
        String deliveryTypeApi = wxConfigurationProperties.getMp().getDeliveryTypeApi();
        String deliveryApi = wxConfigurationProperties.getMp().getDeliveryApi();
        List<NameValuePair> typeParams = new ArrayList<>();
        typeParams.add(new BasicNameValuePair("text", postid));
        String result = "", type = "";
        int noCount = 0;
        try {
            String typeResult = HttpClient.post(deliveryTypeApi, typeParams);
            DeliveryType deliveryType = JSON.parseObject(typeResult, DeliveryType.class);
            if (null != deliveryType && !deliveryType.getAuto().isEmpty()) {
                for (DeliveryTypeAuto deliveryTypeAuto : deliveryType.getAuto()) {
                    if (noCount < deliveryTypeAuto.getNoCount()) {
                        noCount = deliveryTypeAuto.getNoCount();
                        type = deliveryTypeAuto.getComCode();
                    }
                }
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("postid", postid));
                params.add(new BasicNameValuePair("type", type));
                result = HttpClient.post(deliveryApi, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/images/{fileName}.{ext}")
    public void image(@PathVariable("fileName") String fileName, @PathVariable("ext") String ext, HttpServletResponse response) {
        try {
            String file = wxConfigurationProperties.getUpload_dir() + fileName + "." + ext;
            File imageFile = FileUtils.getFile(file);
            if (!imageFile.isFile()) {
                response.sendRedirect("/images/common/login-bg.jpg");
                return;
            }
            byte[] content = FileUtils.readFileToByteArray(imageFile);
            response.getOutputStream().write(content);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.error("image not found", e);
        }
    }

}
