package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.Account;

import java.util.List;

public interface IAccountService {
    Account getByAccount(String account);

    Account getByAppId(String appId);

    Account getSingleAccount();

    List<Account> listForPage(Account account);

    Account getById(Long id);

    void add(Account account);

    void update(Account account);

    void delete(Account account);

}
