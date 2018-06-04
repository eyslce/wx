
package com.eyslce.wx.mp.domain;

import lombok.Data;


@Data
public class TplMsgText extends MsgBase{
	private String title;//消息标题
	private String content;//消息内容
	private Long baseId;//消息主表id
	private String tplId;//消息主表id
	private String wxTpl;//微信模板
}
