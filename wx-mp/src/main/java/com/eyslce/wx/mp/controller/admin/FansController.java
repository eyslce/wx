package com.eyslce.wx.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/fans")
public class FansController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/fans_index";
    }
}
