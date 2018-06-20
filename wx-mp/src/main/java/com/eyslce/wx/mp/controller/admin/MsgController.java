package com.eyslce.wx.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/msg")
public class MsgController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/msg_index";
    }
}
