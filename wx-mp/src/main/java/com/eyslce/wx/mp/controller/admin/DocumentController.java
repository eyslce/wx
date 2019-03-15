package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.mp.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/document")
public class DocumentController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/document/index";
    }
}
