package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.eyslce.wx.mp.service.IFansService;
import com.github.pagehelper.PageInfo;
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
    public HttpResult list(FansQuery query) {
        PageInfo<AccountFans> fansList = fansService.getList(query);
        return success(fansList);
    }
}
