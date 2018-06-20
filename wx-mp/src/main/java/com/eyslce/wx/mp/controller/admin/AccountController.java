package com.eyslce.wx.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/account")
public class AccountController extends BaseController {

    @RequestMapping("add")
    public String add() {
        return "admin/account_add";
    }

    @RequestMapping("url")
    public String url() {
        return "admin/account_url";
    }
}
