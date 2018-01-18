package com.eyslce.wx.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping(value = "/map",method = RequestMethod.GET)
    public ModelAndView map(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("map");
        return  modelAndView;
    }

    @RequestMapping(value = "/delivery",method = RequestMethod.GET)
    public ModelAndView delivery(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("delivery");
        return  modelAndView;
    }

    @RequestMapping(value = "/delivery/search")
    @ResponseBody
    public void deliverySearch(){

    }

}
