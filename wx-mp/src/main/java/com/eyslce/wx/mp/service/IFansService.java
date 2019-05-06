package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IFansService {

    PageInfo<AccountFans> getList(FansQuery query);

    AccountFans getById(String id);

    AccountFans getByOpenId(String openId);

    Integer getTotalItemsCount(AccountFans searchEntity);

    List<AccountFans> getFansListByPage(FansQuery query);

    AccountFans getLastOpenId();

    void add(AccountFans entity);

    void addList(List<AccountFans> list);

    void update(AccountFans entity);

    void delete(AccountFans entity);

    void deleteByOpenId(String openId);

    List<AccountFans> getFansByOpenIdList(List<AccountFans> openIds);
}
