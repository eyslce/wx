package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.dao.MsgTextDao;
import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.domain.MsgText;
import com.eyslce.wx.mp.service.IMsgTextService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MsgTextServiceImpl implements IMsgTextService {

    @Autowired
    private MsgTextDao msgTextDao;
    @Autowired
    private MsgBaseDao msgBaseDao;

    @Override
    public PageInfo<MsgText> getMsgTextList(MsgText msgText) {
        PageHelper.startPage(msgText.getPage(), msgText.getPageSize());
        List<MsgText> list = msgTextDao.getMsgTextList(msgText);
        PageInfo<MsgText> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public MsgText getById(String id) {
        return msgTextDao.getById(id);
    }

    @Override
    public void add(MsgText msgText) {
        MsgBase base = new MsgBase();
        base.setInputCode(msgText.getInputCode());
        base.setCreateTime(new Date());
        base.setMsgType(Constant.MsgType.Text.toString());
        msgBaseDao.add(base);

        msgText.setBaseId(base.getId());
        msgTextDao.add(msgText);
    }

    @Override
    public void update(MsgText msgText) {
        MsgBase base = msgBaseDao.getById(msgText.getBaseId().toString());
        base.setInputCode(msgText.getInputCode());
        msgBaseDao.updateInputCode(base);
        msgTextDao.update(msgText);
    }

    @Override
    public void delete(MsgText msgText) {
        MsgBase base = new MsgBase();
        base.setId(msgText.getBaseId());
        msgBaseDao.delete(base);
        msgTextDao.delete(msgText);
    }
}
