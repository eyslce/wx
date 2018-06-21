package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.mp.query.FansQuery;
import com.eyslce.wx.mp.service.IFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 粉丝管理
 */
@Controller
@RequestMapping("admin/fans")
public class FansController extends BaseController {
    @Autowired
    private IFansService fansService;

    @RequestMapping("index")
    public String index() {
        return "admin/fans_index";
    }

    @RequestMapping("list")
    @ResponseBody
    public void list(FansQuery query) {

    }
}
