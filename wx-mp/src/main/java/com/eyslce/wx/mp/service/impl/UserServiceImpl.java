package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.SysUserDao;
import com.eyslce.wx.mp.domain.SysUser;
import com.eyslce.wx.mp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser getSysUser(SysUser sysUser) {
        return sysUserDao.getSysUser(sysUser);
    }

    @Override
    public SysUser getSysUserById(String userId) {
        return null;
    }

    @Override
    public void updateLoginPwd(SysUser sysUser) {
        sysUserDao.updateLoginPwd(sysUser);
    }
}
