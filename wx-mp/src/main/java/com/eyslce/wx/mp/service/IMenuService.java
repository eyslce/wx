package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.AccountMenu;

import java.util.List;

public interface IMenuService {
    List<AccountMenu> getMenuList(AccountMenu menu);
}
