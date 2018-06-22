package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.Account;

public interface IAccountService {
    Account getById(Long id);

    void add(Account account);

    void update(Account account);

}
