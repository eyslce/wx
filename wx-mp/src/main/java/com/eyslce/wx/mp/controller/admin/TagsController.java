package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.domain.UserTag;
import com.eyslce.wx.mp.service.IFansService;
import com.eyslce.wx.mp.service.IUserTagService;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.tag.WxTagListUser;
import me.chanjar.weixin.mp.bean.tag.WxUserTag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户标签管理
 */
@Controller
@RequestMapping("admin/tags")
public class TagsController extends BaseController {
    @Autowired
    private IUserTagService userTagService;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IFansService fansService;

    @RequestMapping("index")
    public String index() {
        return "admin/tags/index";
    }

    @RequestMapping("add")
    public String add() {
        return "admin/tags/add";
    }

    @RequestMapping("userlist")
    public String userList() {
        return "admin/tags/userlist";
    }

    @RequestMapping(value = "/listForPage")
    @ResponseBody
    public HttpResult listForPage(UserTag searchEntity) {
        PageInfo<UserTag> list = userTagService.listForPage(searchEntity);
        return success(list);
    }

    @RequestMapping(value = "/syncUserTagList")
    @ResponseBody
    public HttpResult syncUserTagList() throws WxErrorException {
        List<WxUserTag> wxUserTags = wxMpService.getUserTagService().tagGet();
        if (CollectionUtils.isEmpty(wxUserTags)) {
            return success();
        }
        Integer maxIdInDb = userTagService.getMaxId() == null ? 0 : userTagService.getMaxId();
        for (WxUserTag userTag : wxUserTags) {
            if (userTag.getId() > maxIdInDb) {
                UserTag tag = new UserTag();
                tag.setId(userTag.getId());
                tag.setCount(userTag.getCount());
                tag.setName(userTag.getName());
                tag.setCreateTime(new Date());
                userTagService.add(tag);
            }
        }
        return success();
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public HttpResult update(UserTag entity) throws WxErrorException {
        if (entity.getId() != null) {
            Boolean result = wxMpService.getUserTagService().tagUpdate(entity.getId(), entity.getName());
            if (!result) {
                return error(Constant.ERROR_MSG);
            }
            userTagService.update(entity);
            //更新成功
            return success();
        }
        //添加分两步
        //1. 调用微信API添加
        WxUserTag wxUserTag = wxMpService.getUserTagService().tagCreate(entity.getName());
        //2.添加到本地库
        if (wxUserTag != null) {
            UserTag tag = new UserTag();
            tag.setId(wxUserTag.getId());
            tag.setCount(wxUserTag.getCount());
            tag.setName(wxUserTag.getName());
            tag.setCreateTime(new Date());
            userTagService.add(tag);
            return success();
        }
        return error(Constant.ERROR_MSG);

    }

    @RequestMapping(value = "deleteBatchIds")
    @ResponseBody
    public HttpResult deleteBatchIds(String[] ids) throws WxErrorException {
        if (null != ids && ids.length > 0) {
            int nums = 0;
            for (String id : ids) {
                if (wxMpService.getUserTagService().tagDelete(Long.parseLong(id))) {
                    nums++;
                }
            }
            if (nums == ids.length) {
                userTagService.deleteBatchIds(ids);
            }
            return success();
        } else {
            return error("用户标签批量删除失败");
        }
    }

    @RequestMapping(value = "getUserListByTag")
    @ResponseBody
    public HttpResult getUserListByTag(Long id) throws WxErrorException {

        WxTagListUser wxTagListUser = wxMpService.getUserTagService().tagListUser(id, "");
        if (wxTagListUser == null) {
            return error("没有数据");
        }
        WxTagListUser.WxTagListUserData wxTagListUserData = wxTagListUser.getData();

        if (wxTagListUserData != null) {
            List<String> list = wxTagListUserData.getOpenidList();
            List<AccountFans> fansList = new ArrayList<AccountFans>();
            for (String openId : list) {
                AccountFans fans = new AccountFans();
                fans.setOpenId(openId);
                fansList.add(fans);
            }
            fansList = fansService.getFansByOpenIdList(fansList);
            return success(fansList, Constant.SUCCESS_MSG);
        }
        return error("没有数据");
    }

}
