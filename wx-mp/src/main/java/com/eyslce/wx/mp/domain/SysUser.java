
package com.eyslce.wx.mp.domain;


import lombok.Data;


/**
 * 系统用户
 */
@Data
public class SysUser  {
    
	//主键id
	private String id;
	//用户名
	private String account;
	//密码
	private String pwd;
	//性别 0男 1女
	private String sex;
	//手机号
	private String phone;
	//姓名
	private String trueName;
	//创建时间
	private String createTime;
	//更新时间
	private String updateTime;
	//状态
	private String flag;
	
	//新登录密码
	private String newpwd;

	private String googleCode;
}
