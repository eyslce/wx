package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.domain.TplMsgText;
import com.eyslce.wx.mp.service.ITplService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("admin/tpl")
public class TplController extends BaseController {
    @Autowired
    private ITplService tplService;

    @RequestMapping("index")
    public String index() {
        return "admin/tpl/index";
    }

    @RequestMapping("add")
    public String add() {
        return "admin/tpl/add";
    }

    @RequestMapping("edit")
    public String edit() {
        return "admin/tpl/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/getById")
    public HttpResult getById(String id){
        TplMsgText text = tplService.getById(id);
        return success(text, Constant.SUCCESS_MSG);
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public HttpResult list(TplMsgText searchEntity) {
        PageInfo<TplMsgText> pageList = tplService.getTplMsgTextByPage(searchEntity);
        return success(pageList);
    }

    @RequestMapping(value = "/updateText")
    @ResponseBody
    public HttpResult updateText(TplMsgText entity){
        if (entity.getId() != null) {
            tplService.update(entity);
            //更新成功
        } else {
            //添加成功
            tplService.add(entity);
        }
        return success();
    }

    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public HttpResult deleteById(String ids) {
        tplService.delete(ids);
        return success();
    }
}
