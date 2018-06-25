package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import com.github.pagehelper.PageInfo;

public interface IFansService {
    PageInfo<AccountFans> getList(FansQuery query);
}
