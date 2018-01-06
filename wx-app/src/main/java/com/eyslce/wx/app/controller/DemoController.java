package com.eyslce.wx.app.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class DemoController {

    @RequestMapping("/index")
    public String index(){
        return "Hello Wx Mini App";
    }
}
