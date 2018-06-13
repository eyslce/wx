package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.SysUser;

public interface IUserService {
    public SysUser getSysUser(SysUser sysUser);

    public SysUser getSysUserById(String userId);

    public void updateLoginPwd(SysUser sysUser);
}
