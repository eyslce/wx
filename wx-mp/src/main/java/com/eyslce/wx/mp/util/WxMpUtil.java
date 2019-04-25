package com.eyslce.wx.mp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.DateTimeUtils;
import com.eyslce.wx.mp.dao.MsgNewsDao;
import com.eyslce.wx.mp.dao.MsgTextDao;
import com.eyslce.wx.mp.domain.*;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class WxMpUtil {
    Logger logger = LoggerFactory.getLogger(WxMpUtil.class);
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private MsgTextDao msgTextDao;
    @Autowired
    private MsgNewsDao msgNewsDao;

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

    /**
     * 获取单个粉丝详情
     *
     * @param openId
     * @return
     * @throws WxErrorException
     */
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
            fans.setSubscribeTime(DateTimeUtils.formatSecondUnitToDate(user.getSubscribeTime()));
        } else {
            fans.setSubscribeStatus(0);
        }
        fans.setNickname(user.getNickname().getBytes(StandardCharsets.UTF_8));
        fans.setGender(user.getSexId());
        fans.setLanguage(user.getLanguage());
        fans.setCountry(user.getCountry());
        fans.setProvince(user.getProvince());
        fans.setCity(user.getCity());
        fans.setHeadimgurl(user.getHeadImgUrl());
        fans.setRemark(user.getRemark());
        fans.setStatus(1);
        fans.setCreateTime(new Date());
        return fans;
    }

    public JSONObject prepareMenus(List<AccountMenu> menus) {
        JSONObject root = new JSONObject();
        if (!CollectionUtils.isEmpty(menus)) {
            List<AccountMenu> parentAM = new ArrayList<AccountMenu>();
            Map<Long, List<JSONObject>> subAm = new HashMap<Long, List<JSONObject>>();
            for (AccountMenu m : menus) {
                if (m.getParentId().intValue() == 0) {// 一级菜单
                    parentAM.add(m);
                } else {// 二级菜单
                    if (subAm.get(m.getParentId()) == null) {
                        subAm.put(m.getParentId(), new ArrayList<JSONObject>());
                    }
                    List<JSONObject> tmpMenus = subAm.get(m.getParentId());
                    tmpMenus.add(getMenuJSONObj(m));
                    subAm.put(m.getParentId(), tmpMenus);
                }
            }
            JSONArray arr = new JSONArray();
            for (AccountMenu m : parentAM) {
                if (subAm.get(m.getId()) != null) {// 有子菜单
                    arr.add(getParentMenuJSONObj(m, subAm.get(m.getId())));
                } else {// 没有子菜单
                    arr.add(getMenuJSONObj(m));
                }
            }
            root.put("button", arr);

        }
        // 添加消息id
        root.put("msgs", getMsg());
        return root;
    }

    public JSONObject getMenuJSONObj(AccountMenu menu) {
        JSONObject obj = new JSONObject();
        obj.put("name", menu.getName());
        obj.put("type", menu.getMtype());
        if (Constant.MENU_NEED_KEY.contains(menu.getMtype())) {//事件菜单
            if ("fix".equals(menu.getEventType())) {//fix 消息
                obj.put("key", "_fix_" + menu.getMsgId());//以 _fix_ 开头
            } else {
                if (StringUtils.isEmpty(menu.getInputCode())) {//如果inputcode 为空，默认设置为 subscribe，以免创建菜单失败
                    obj.put("key", "subscribe");
                } else {
                    obj.put("key", menu.getInputCode());
                }
            }
            //存msgtype id
            obj.put("msgType", menu.getMsgType());
            obj.put("msgId", menu.getMsgId());//
        } else {//链接菜单-view
            obj.put("url", menu.getUrl());
        }
        return obj;
    }

    public JSONObject getParentMenuJSONObj(AccountMenu menu, List<JSONObject> subMenu) {
        JSONObject obj = new JSONObject();
        obj.put("name", menu.getName());
        obj.put("sub_button", subMenu);
        return obj;
    }

    /**
     * 获取消息
     *
     * @return
     */
    public JSONArray getMsg() {
        JSONArray arr = new JSONArray();
        List<MsgText> msgTextList = msgTextDao.getMsgTextList(new MsgText());
        List<MsgNews> msgNews = msgNewsDao.listForPage(new MsgNews());
        if (CollectionUtils.isNotEmpty(msgTextList)) {
            for (MsgText text : msgTextList) {
                JSONObject obj = new JSONObject();
                obj.put("id", text.getBaseId());
                obj.put("title", text.getTitle());
                obj.put("type", Constant.MsgType.Text.toString());
                arr.add(obj);
            }
        }
        if (CollectionUtils.isNotEmpty(msgNews)) {
            for (MsgNews news : msgNews) {
                JSONObject obj = new JSONObject();
                obj.put("id", news.getBaseId());
                obj.put("title", news.getTitle());
                obj.put("type", Constant.MsgType.News.toString());
                arr.add(obj);
            }
        }
        return arr;
    }
}
