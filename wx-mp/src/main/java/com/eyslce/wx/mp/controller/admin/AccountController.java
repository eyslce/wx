package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.StringUtils;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.Account;
import com.eyslce.wx.mp.service.IAccountService;
import com.eyslce.wx.mp.util.WxMpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 公众号管理
 */
@Controller
@RequestMapping("admin/account")
public class AccountController extends BaseController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private WxMpUtil wxMpUtil;

    @RequestMapping("add")
    public String add() {
        return "admin/account/add";
    }

    @RequestMapping("index")
    public String index() {
        return "admin/account/index";
    }

    @RequestMapping("form")
    public String form() {
        return "admin/account/form";
    }

    @RequestMapping("getUrl")
    @ResponseBody
    public HttpResult getUrl(Account account) {
        String url = "/wxapi/" + account.getAccount() + "/message";
        if (account.getId() == null) {//新增
            account.setUrl(url);
            account.setToken(StringUtils.token());
            account.setAeskey(StringUtils.wxAesKey());
            account.setCreatetime(new Date());
            accountService.add(account);
        } else {//更新
            Account tmpAccount = accountService.getById(account.getId());
            tmpAccount.setUrl(url);
            tmpAccount.setAccount(account.getAccount());
            tmpAccount.setAppid(account.getAppid());
            tmpAccount.setAppsecret(account.getAppsecret());
            tmpAccount.setMsgcount(account.getMsgcount());
            tmpAccount.setName(account.getName());
            accountService.update(tmpAccount);
        }
        return success(account, "操作成功");
    }

    @RequestMapping(value = "/urltoken")
    @ResponseBody
    public HttpResult urltoken() {
        Account account = wxMpUtil.getCurrentAccount();
        List<String> msgCountList = new ArrayList<String>();
        int len = account.getMsgcount() + 5;
        int start = account.getMsgcount() - 5 > 0 ? account.getMsgcount() - 5 : 1;
        for (int i = start; i < len; i++) {
            msgCountList.add(String.valueOf(i));
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("account", account);
        data.put("msgCountList", msgCountList);
        return success(data, Constant.SUCCESS_MSG);
    }

    @RequestMapping(value = "/listForPage")
    @ResponseBody
    public HttpResult listForPage(Account searchEntity) {
        List<Account> list = accountService.listForPage(searchEntity);
        if (list.isEmpty()) {
            return success();
        }
        Account account;
        if (null != searchEntity && null != searchEntity.getId()) {
            account = accountService.getById(searchEntity.getId());
            wxMpUtil.changeCurrentAccount(account);
        } else {
            account = wxMpUtil.getCurrentAccount();
        }
        return success(wxMpUtil.getAccount(list, account.getName()), Constant.SUCCESS_MSG);
    }
}
