package com.eyslce.wx.mp.controller.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.AccountMenu;
import com.eyslce.wx.mp.entity.MatchRule;
import com.eyslce.wx.mp.service.IMenuService;
import com.eyslce.wx.mp.util.WxMpUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin/menu")
public class AdminMenuController extends BaseController {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private WxMpUtil wxMpUtil;

    @Autowired
    private WxMpService wxMpService;

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
        MatchRule matchrule = new MatchRule();
        return success(wxMpUtil.prepareMenus(menus, matchrule), Constant.SUCCESS_MSG);
    }

    @PostMapping("save")
    @ResponseBody
    public HttpResult save(String menus) {
        JSONArray jsons = JSONArray.parseArray(menus);
        //每次先行删除公众号所有菜单
        menuService.delete(new AccountMenu());
        if (CollectionUtils.isNotEmpty(jsons)) {
            for (int i = 0; i < jsons.size(); i++) {
                JSONObject json = jsons.getJSONObject(i);
                if (null != json) {
                    AccountMenu accountMenu = new AccountMenu();
//					String pid = CommonUtil.getUID();
//					accountMenu.setId(pid);
                    accountMenu.setName(json.getString("name"));
                    accountMenu.setSort(i + 1);
                    accountMenu.setParentId((long) 0);
                    if (json.containsKey("type")) {
                        accountMenu.setMtype(json.getString("type"));
                        //判断是否设置key
                        if (Constant.MENU_NEED_KEY.contains(json.getString("type"))) {
                            accountMenu.setEventType("fix");
                            accountMenu.setMsgType(json.getString("msgType"));
                            accountMenu.setMsgId(json.getString("msgId"));
                        }
                    }
                    if (json.containsKey("url")) {
                        accountMenu.setUrl(json.getString("url"));
                    }
                    if (json.containsKey("media_id")) {
                        accountMenu.setMsgId(json.getString("media_id"));
                    }
                    accountMenu.setCreateTime(new Date());
                    //保存
                    menuService.add(accountMenu);
                    //判断是否有subbutton
                    if (json.containsKey("sub_button")) {
                        JSONArray buttons = json.getJSONArray("sub_button");
                        if (CollectionUtils.isNotEmpty(buttons)) {
                            long pid = accountMenu.getId();
                            for (int j = 0; j < buttons.size(); j++) {
                                json = buttons.getJSONObject(j);
                                accountMenu = new AccountMenu();
                                accountMenu.setParentId(pid);
                                accountMenu.setName(json.getString("name"));
                                accountMenu.setSort(j + 1);
                                if (json.containsKey("type")) {
                                    accountMenu.setMtype(json.getString("type"));
                                    //判断是否设置key
                                    if (Constant.MENU_NEED_KEY.contains(json.getString("type"))) {
                                        accountMenu.setEventType("fix");
                                        accountMenu.setMsgType(json.getString("msgType"));
                                        accountMenu.setMsgId(json.getString("msgId"));
                                    }
                                }
                                if (json.containsKey("url")) {
                                    accountMenu.setUrl(json.getString("url"));
                                }
                                if (json.containsKey("media_id")) {
                                    accountMenu.setMsgId(json.getString("media_id"));
                                }
                                accountMenu.setCreateTime(new Date());
                                menuService.add(accountMenu);
                            }
                        }
                    }
                }
            }
        }
        return success();
    }

    @PostMapping("publish")
    @ResponseBody
    public HttpResult publish() throws WxErrorException {
        List<AccountMenu> menus = menuService.listWxMenus(new AccountMenu());
        MatchRule matchrule = new MatchRule();
        String menuJson = JSONObject.toJSONString(wxMpUtil.prepareMenus(menus, matchrule));
        String menuid = wxMpService.getMenuService().menuCreate(menuJson);
        return success(menuid);
    }
}
