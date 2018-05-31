package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SysConfigDao {
    List<SysConfig> getConfigList();

    public Map<String, String> getSysConfigToMap();

    public String getValue(String key);

    public void update(Map<String, String> params, HttpServletRequest request);

    public String getMysqlVsesion();
}
