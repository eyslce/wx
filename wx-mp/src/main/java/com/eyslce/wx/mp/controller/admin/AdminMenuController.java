package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.domain.AccountMenu;
import com.eyslce.wx.mp.service.IMenuService;
import com.eyslce.wx.mp.util.WxMpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/menu")
public class AdminMenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @RequestMapping("index")
    public String index() {
        return "admin/menu/index";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public HttpResult list(AccountMenu accountMenu) {
        List<AccountMenu> menus = menuService.getMenuList(accountMenu);
        return success(new WxMpUtil().prepareMenus(menus), Constant.SUCCESS_MSG);
    }
}
