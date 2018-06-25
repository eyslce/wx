package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.AccountDao;
import com.eyslce.wx.mp.domain.Account;
import com.eyslce.wx.mp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getByAccount(String account) {
        return accountDao.getByAccount(account);
    }

    @Override
    public Account getSingleAccount() {
        return accountDao.getSingleAccount();
    }

    @Override
    public List<Account> listForPage(Account account) {
        return accountDao.listForPage(account);
    }

    @Override
    @Cacheable(value = "WxMpAccount")
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

    @Override
    @CacheEvict(value = "WxMpAccount", key = "#account.id")
    public void delete(Account account) {
        accountDao.delete(account);
    }
}
