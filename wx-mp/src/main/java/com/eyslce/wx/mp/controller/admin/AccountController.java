package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.mp.domain.Account;
import com.eyslce.wx.mp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        return "admin/account_add";
    }

    @RequestMapping("url")
    public String url() {
        return "admin/account_url";
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
        //todo 写入缓存
        return success(account, "操作成功");
    }

    @RequestMapping(value = "/listForPage")
    @ResponseBody
    public HttpResult listForPage(Account searchEntity) {

        List<Account> list = accountService.listForPage(searchEntity);
        if (CollectionUtils.isEmpty(list)) {
            return AjaxResult.success();
        }

        return AjaxResult.success(WxUtil.getAccount(list, account.getName()));
    }
}
