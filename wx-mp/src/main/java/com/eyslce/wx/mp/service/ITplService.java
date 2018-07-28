package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.TplMsgText;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ITplService {
    TplMsgText getById(String id);
    PageInfo<TplMsgText> getTplMsgTextByPage(TplMsgText searchEntity);
    void add(TplMsgText tplMsgText);
    void update(TplMsgText tplMsgText);
    void delete(String ids);
}
