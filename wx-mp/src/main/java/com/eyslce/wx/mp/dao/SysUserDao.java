package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysUserDao {
	SysUser getSysUser(SysUser sysUser);

	SysUser getSysUserById(String userId);

	void updateLoginPwd(SysUser sysUser);
}
