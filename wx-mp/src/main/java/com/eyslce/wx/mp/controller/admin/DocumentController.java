package com.eyslce.wx.mp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/document")
public class DocumentController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/document_index";
    }
}
