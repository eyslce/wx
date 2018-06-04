
package com.eyslce.wx.mp.domain;

import lombok.Data;


@Data
public class MsgText extends MsgBase{
	private String title;//消息标题
	private String content;//消息内容
	private Long baseId;//消息主表id

}
