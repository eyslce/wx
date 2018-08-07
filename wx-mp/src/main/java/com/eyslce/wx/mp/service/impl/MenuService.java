package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.AccountMenuDao;
import com.eyslce.wx.mp.domain.AccountMenu;
import com.eyslce.wx.mp.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService {
    @Autowired
    private AccountMenuDao menuDao;

    @Override
    public List<AccountMenu> getMenuList(AccountMenu menu) {
        return menuDao.listWxMenus(menu);
    }
}
