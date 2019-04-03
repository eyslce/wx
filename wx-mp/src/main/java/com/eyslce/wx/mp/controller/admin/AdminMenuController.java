package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.AccountMenu;
import com.eyslce.wx.mp.service.IMenuService;
import com.eyslce.wx.mp.util.WxMpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/menu")
public class AdminMenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private WxMpUtil wxMpUtil;

    @RequestMapping("index")
    public String index() {
        return "admin/menu/index";
    }

    @RequestMapping("resources")
    public String resources() {
        return "admin/menu/resources";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public HttpResult list(AccountMenu accountMenu) {
        List<AccountMenu> menus = menuService.getMenuList(accountMenu);
        return success(wxMpUtil.prepareMenus(menus), Constant.SUCCESS_MSG);
    }

    @PostMapping("save")
    @ResponseBody
    public HttpResult save() {
        return success();
    }

    @PostMapping("publish")
    @ResponseBody
    public HttpResult publish() {
        //todo 同步菜单
        return success();
    }
}
