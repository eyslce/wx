package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.mp.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户标签管理
 */
@Controller
@RequestMapping("admin/tags")
public class TagsController extends BaseController {
    @RequestMapping("index")
    public String index() {
        return "admin/tags/index";
    }
}
