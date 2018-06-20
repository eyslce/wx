package com.eyslce.wx.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/menu")
public class AdminMenuController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/menu_index";
    }
}
