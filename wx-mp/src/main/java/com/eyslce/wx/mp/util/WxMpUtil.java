package com.eyslce.wx.mp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eyslce.wx.mp.domain.Account;
import com.eyslce.wx.mp.domain.AccountFans;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WxMpUtil {

    @Autowired
    private WxMpService wxMpService;

    public static JSONObject getAccount(List<Account> list, String curentName) {
        JSONObject obj = new JSONObject();
        obj.put("correct", curentName);
        JSONArray arr = new JSONArray();
        if (!list.isEmpty()) {
            for (Account account : list) {
                JSONObject objAccount = new JSONObject();
                objAccount.put("id", account.getId());
                objAccount.put("name", account.getName());
                arr.add(objAccount);
            }
        }
        obj.put("list", arr);
        return obj;
    }

    public AccountFans getFansInfo(String openId) throws WxErrorException {
        String lang = "zh_CN"; //语言
        WxMpUser user = wxMpService.getUserService().userInfo(openId, lang);
        if (user == null) {
            return null;
        }
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
        return fans;
    }
}
