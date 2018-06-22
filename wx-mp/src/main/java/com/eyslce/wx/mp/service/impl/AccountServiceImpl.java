package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.AccountDao;
import com.eyslce.wx.mp.domain.Account;
import com.eyslce.wx.mp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getById(Long id) {
        return accountDao.getById(id);
    }

    @Override
    public void add(Account account) {
        accountDao.add(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }
}
