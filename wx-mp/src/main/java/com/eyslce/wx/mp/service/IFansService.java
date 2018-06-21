package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;

import java.util.List;

public interface IFansService {
    List<AccountFans> getList(FansQuery query);
}
