package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.dao.TplMsgTextDao;
import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.domain.TplMsgText;
import com.eyslce.wx.mp.service.ITplService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TplServiceImpl implements ITplService {

    @Autowired
    private TplMsgTextDao tplMsgTextDao;
    @Autowired
    private MsgBaseDao msgBaseDao;

    @Override
    public TplMsgText getById(String id) {
        return tplMsgTextDao.getById(id);
    }

    @Override
    public PageInfo<TplMsgText> getTplMsgTextByPage(TplMsgText tplMsgText) {
        PageHelper.startPage(tplMsgText.getPage(), tplMsgText.getPageSize());
        List<TplMsgText> list = tplMsgTextDao.getTplMsgTextByPage(tplMsgText);
        PageInfo<TplMsgText> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void add(TplMsgText tplMsgText) {
        MsgBase base = new MsgBase();
        base.setInputCode(tplMsgText.getInputCode());
        base.setCreateTime(new Date());
        base.setMsgType(Constant.MsgType.Text.toString());
        msgBaseDao.add(base);

        tplMsgText.setBaseId(base.getId());
        tplMsgTextDao.add(tplMsgText);
    }

    @Override
    public void update(TplMsgText tplMsgText) {
        MsgBase base = msgBaseDao.getById(tplMsgText.getBaseId().toString());
        base.setInputCode(tplMsgText.getInputCode());
        msgBaseDao.updateInputCode(base);
        tplMsgTextDao.update(tplMsgText);
    }

    @Override
    public void delete(String ids) {
        String[] idsArr = StringUtils.split(ids, ",");
        for (String id : idsArr) {
            TplMsgText tplMsgText = getById(id);
            if(tplMsgText == null){
                continue;
            }
            tplMsgTextDao.delete(tplMsgText);
            MsgBase base = new MsgBase();
            base.setId(tplMsgText.getBaseId());
            msgBaseDao.delete(base);
        }
    }
}
