package com.eyslce.wx.mp.controller;

import com.eyslce.wx.commons.util.Configuration;
import com.eyslce.wx.commons.util.HttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    Configuration configuration;

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
     *
     */
    @RequestMapping(value = "/delivery/search")
    @ResponseBody
    public void deliverySearch(@RequestParam(name = "postid") String postid){
        String deliveryTypeApi = configuration.getMp().getDeliveryTypeApi();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("text",postid));
        try {
            String result = HttpClient.post(deliveryTypeApi,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
