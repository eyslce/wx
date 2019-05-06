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
    public PageInfo<AccountFans> getList(FansQuery query) {
        PageHelper.startPage(query.getPage(), query.getPageSize());
        List<AccountFans> list = accountFansDao.getFansListByPage(query);
        PageInfo<AccountFans> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public AccountFans getById(String id) {
        return accountFansDao.getById(id);
    }

    @Override
    public AccountFans getByOpenId(String openId) {
        return accountFansDao.getByOpenId(openId);
    }

    @Override
    public Integer getTotalItemsCount(AccountFans searchEntity) {
        return accountFansDao.getTotalItemsCount(searchEntity);
    }

    @Override
    public List<AccountFans> getFansListByPage(FansQuery query) {
        return accountFansDao.getFansListByPage(query);
    }

    @Override
    public AccountFans getLastOpenId() {
        return accountFansDao.getLastOpenId();
    }

    @Override
    public void add(AccountFans entity) {
        accountFansDao.add(entity);
    }

    @Override
    public void addList(List<AccountFans> list) {
        accountFansDao.addList(list);
    }

    @Override
    public void update(AccountFans entity) {
        accountFansDao.update(entity);
    }

    @Override
    public void delete(AccountFans entity) {
        accountFansDao.delete(entity);
    }

    @Override
    public void deleteByOpenId(String openId) {
        accountFansDao.deleteByOpenId(openId);
    }

    @Override
    public List<AccountFans> getFansByOpenIdList(List<AccountFans> openIds) {
        return accountFansDao.getFansByOpenIdList(openIds);
    }


}
