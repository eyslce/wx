package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.SysConfigDao;
import com.eyslce.wx.mp.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    public String getMysqlVsesion() {
        return sysConfigDao.getMysqlVsesion();
    }
}
