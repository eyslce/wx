package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.MsgText;
import com.github.pagehelper.PageInfo;

public interface IMsgTextService {
    PageInfo<MsgText> getMsgTextList(MsgText msgText);

    MsgText getById(String id);

    void add(MsgText msgText);

    void update(MsgText msgText);

    void delete(MsgText msgText);
}
