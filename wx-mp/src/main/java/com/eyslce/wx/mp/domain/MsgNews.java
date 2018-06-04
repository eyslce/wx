
package com.eyslce.wx.mp.domain;

import lombok.Data;

import java.util.List;


@Data
public class MsgNews extends MsgBase{

	private Integer multType;//'1单图文2多图文类',
	private String title;//标题
	private String author;//作者
	private String brief;//简介
	private String description;//描述
	private String picpath;//封面图片
	private String picdir;//封面图片绝对目录
	private Integer showpic;//是否显示图片
	private String url;//图文消息原文链接
	private String fromurl;//外部链接
	private Long baseId;//消息主表id
	private String mediaId; //媒体id
	private String thumbMediaId;//封面图片id
	private Integer newsIndex;//多图文中的第几条
	
	private String start;
	private String end;
	//一对多
	private List<MsgArticle> articles;
}
