package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.AccountFansDao;
import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.eyslce.wx.mp.service.IFansService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FansServiceImpl implements IFansService {
    @Autowired
    private AccountFansDao accountFansDao;

    @Override
    public List<AccountFans> getList(FansQuery query) {
        PageHelper.startPage(query.getPage(), query.getPageSize());
        List<AccountFans> list = accountFansDao.getFansListByPage(query);
        PageInfo<AccountFans> pageInfo = new PageInfo(list);
        return list;
    }
}
