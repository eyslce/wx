package com.eyslce.wx.mp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "admin/index";
    }

    @GetMapping(value = {"/index-x"})
    public String indexx() {
        return "admin/index-x";
    }

    @GetMapping("log")
    public String log() {
        return "admin/log";
    }
}
