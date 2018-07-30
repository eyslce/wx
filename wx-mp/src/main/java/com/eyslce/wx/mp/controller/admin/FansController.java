package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.mp.dao.AccountFansDao;
import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.eyslce.wx.mp.service.IFansService;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 粉丝管理
 */
@Controller
@RequestMapping("admin/fans")
public class FansController extends BaseController {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IFansService fansService;
    @Autowired
    private AccountFansDao fansDao;

    @RequestMapping("index")
    public String index() {
        return "admin/fans/index";
    }

    @RequestMapping("list")
    @ResponseBody
    public HttpResult list(FansQuery query) {
        PageInfo<AccountFans> fansList = fansService.getList(query);
        return success(fansList);
    }

    //同步粉丝列表
    @RequestMapping(value = "/syncFansList")
    @ResponseBody
    public HttpResult syncAccountFansList() throws WxErrorException {
        //todo
        String nextOpenid = null;
        WxMpUserList wxUserList = wxMpService.getUserService().userList(nextOpenid);
        //获取每个用户的详细信息
        List<AccountFans> fansList = new ArrayList<AccountFans>();
        List<String> openIds = wxUserList.getOpenids();
        for (String openId : openIds) {
            String lang = "zh_CN"; //语言
            WxMpUser user = wxMpService.getUserService().userInfo(openId, lang);
            AccountFans fans = new AccountFans();
            fans.setOpenId(openId);
            if (user.getSubscribe()) {
                fans.setSubscribeStatus(1);
                fans.setSubscribeTime(new Date(user.getSubscribeTime()));
            } else {
                fans.setSubscribeStatus(0);
            }
            fans.setNicknameStr(user.getNickname());
            fans.setGender(user.getSexId());
            fans.setLanguage(user.getLanguage());
            fans.setCountry(user.getCountry());
            fans.setCity(user.getCity());
            fans.setHeadimgurl(user.getHeadImgUrl());
            fans.setRemark(user.getRemark());
            fans.setStatus(1);
            fansList.add(fans);
        }
        fansDao.addList(fansList);
        return success();
    }
}
