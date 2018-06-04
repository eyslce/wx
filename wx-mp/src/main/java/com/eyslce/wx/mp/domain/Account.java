package com.eyslce.wx.mp.domain;

import lombok.Data;

import java.util.Date;


@Data
public class Account {
	private String name;//名称
	private Long id;
	private Date createtime = new Date();//创建时间

}
