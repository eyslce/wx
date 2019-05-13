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

    Map<String, String> getSysConfigToMap();

    String getValue(String key);

    void update(Map<String, String> params, HttpServletRequest request);

    String getMysqlVsesion();
}
