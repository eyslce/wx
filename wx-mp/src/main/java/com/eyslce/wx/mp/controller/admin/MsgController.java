package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.domain.MsgText;
import com.eyslce.wx.mp.service.IMsgTextService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/msg")
public class MsgController extends BaseController {
    @Autowired
    private IMsgTextService msgTextService;

    @RequestMapping("index")
    public String index() {
        return "admin/msg/index";
    }

    @RequestMapping("add")
    public String add() {
        return "admin/msg/add";
    }

    @RequestMapping("edit")
    public String edit() {
        return "admin/msg/edit";
    }

    @RequestMapping("mass")
    public String mass() {
        return "admin/msg/mass";
    }

    @ResponseBody
    @RequestMapping(value = "list")
    public HttpResult list(MsgText msgText) {
        PageInfo<MsgText> pageList = msgTextService.getMsgTextList(msgText);
        return success(pageList);
    }

    @RequestMapping(value = "updateText")
    @ResponseBody
    public HttpResult updateText(MsgText entity) {
        if (entity.getId() != null) {
            msgTextService.update(entity);
        } else {
            //添加成功
            msgTextService.add(entity);
        }
        return success();
    }

    @RequestMapping(value = "deleteById")
    @ResponseBody
    public HttpResult deleteById(String ids) {
        msgTextService.delete(ids);
        return success();
    }

    @ResponseBody
    @RequestMapping(value = "/getById")
    public HttpResult getById(String id) {
        MsgText text = msgTextService.getById(id);
        return success(text, Constant.SUCCESS_MSG);
    }
}
