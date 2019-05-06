package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.eyslce.wx.mp.service.IFansService;
import com.eyslce.wx.mp.util.WxMpUtil;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    private WxMpUtil wxMpUtil;

    @RequestMapping("index")
    public String index() {
        return "admin/fans/index";
    }

    @RequestMapping("info")
    public String info() {
        return "admin/fans/info";
    }

    @RequestMapping("msglist")
    public String msglist() {
        return "admin/fans/msglist";
    }

    @RequestMapping("chose")
    public String chose() {
        return "admin/fans/chose";
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
        //获取最后openID
        //一次最多同步一万用户，后续处理这里TODO
        String nextOpenid = null;
        WxMpUserList wxUserList = wxMpService.getUserService().userList(nextOpenid);

        //获取每个用户的详细信息
        List<AccountFans> fansList = new ArrayList<AccountFans>();
        List<String> openIds = wxUserList.getOpenids();
        if (CollectionUtils.isEmpty(openIds)) {
            return success();
        }
        for (String openId : openIds) {
            AccountFans fans = wxMpUtil.getFansInfo(openId);
            if (fans == null) {
                continue;
            }
            AccountFans oldFans = fansService.getByOpenId(fans.getOpenId());
            if (oldFans != null) {
                fansService.update(fans);
            } else {
                fansList.add(fans);
            }
        }
        if (CollectionUtils.isEmpty(fansList)) {
            return success();
        }
        fansService.addList(fansList);
        return success();
    }

    @RequestMapping(value = "/syncFans")
    @ResponseBody
    public HttpResult syncAccountFans(String openId) throws WxErrorException {
        AccountFans fans = wxMpUtil.getFansInfo(openId);
        if (fans == null) {
            return error("没有找到该用户信息");
        }
        fansService.update(fans);
        return success(fans, Constant.SUCCESS_MSG);
    }
}
