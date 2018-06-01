package com.eyslce.wx.mp.controller;

import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.util.FileOperation;
import com.eyslce.wx.commons.util.HttpClient;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.entity.DeliveryType;
import com.eyslce.wx.mp.entity.DeliveryTypeAuto;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    WxConfigurationProperties wxConfigurationProperties;

    private final ResourceLoader resourceLoader;

    @Autowired
    public IndexController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 地图
     * @return
     */
    @RequestMapping(value = "/map",method = RequestMethod.GET)
    public ModelAndView map(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("map");
        return  modelAndView;
    }

    /**
     * 快递查询页面
     * @return
     */
    @RequestMapping(value = "/delivery",method = RequestMethod.GET)
    public ModelAndView delivery(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("delivery");
        return  modelAndView;
    }

    /**
     * 查询快递详情
     */
    @RequestMapping(value = "/delivery/search")
    @ResponseBody
    public String deliverySearch(@RequestParam(name = "postid") String postid){
        String deliveryTypeApi = wxConfigurationProperties.getMp().getDeliveryTypeApi();
        String deliveryApi = wxConfigurationProperties.getMp().getDeliveryApi();
        List<NameValuePair> typeParams = new ArrayList<>();
        typeParams.add(new BasicNameValuePair("text",postid));
        String result = "",type="";
        int noCount=0;
        try {
            String typeResult = HttpClient.post(deliveryTypeApi,typeParams);
            DeliveryType deliveryType = JSON.parseObject(typeResult,DeliveryType.class);
            if(null != deliveryType && !deliveryType.getAuto().isEmpty()){
                for(DeliveryTypeAuto deliveryTypeAuto:deliveryType.getAuto()){
                    if(noCount<deliveryTypeAuto.getNoCount()){
                        noCount = deliveryTypeAuto.getNoCount();
                        type = deliveryTypeAuto.getComCode();
                    }
                }
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("postid",postid));
                params.add(new BasicNameValuePair("type",type));
                result = HttpClient.post(deliveryApi,params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 天气
     * @return
     */
    @RequestMapping(value = "/weather")
    public ModelAndView weather(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("weather");
        return  modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename, HttpServletRequest request) {
        try {
            String path = request.getSession().getServletContext().getRealPath("/");
            Resource resource = resourceLoader.getResource("file:" + Paths.get(path, FileOperation.UPLOAD_DIR, filename).toString());
            return ResponseEntity.ok(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
