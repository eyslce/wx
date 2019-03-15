package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
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

    @RequestMapping("add")
    public String add() {
        return "admin/account/add";
    }

    @RequestMapping("url")
    public String url() {
        return "admin/account/url";
    }

    @RequestMapping("form")
    public String form() {
        return "admin/account/form";
    }

    @RequestMapping("getUrl")
    @ResponseBody
    public HttpResult getUrl(Account account) {
        String url = "/wxapi/" + account.getAccount() + "/message.html";
        if (account.getId() == null) {//新增
            account.setUrl(url);
            account.setToken(UUID.randomUUID().toString().replace("-", ""));
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
        //目前只支持单个
        Account account = accountService.getSingleAccount();
        List<String> msgCountList = new ArrayList<String>();
        for (int i = 1; i < 8; i++) {
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
        } else {
            account = accountService.getSingleAccount();
        }
        return success(WxMpUtil.getAccount(list, account.getName()), Constant.SUCCESS_MSG);
    }
}
