
package com.eyslce.wx.mp.domain;

import com.eyslce.wx.mp.query.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class MsgBase extends PageQuery {
	private Long id;
	private String msgType;//消息类型;
	private String inputCode;//关注者发送的消息
	@JsonIgnore
	private String rule;//规则，目前是 “相等”
	@JsonIgnore
	private Integer enable;//是否可用
	@JsonIgnore
	private Integer readCount;//消息阅读数
	@JsonIgnore
	private Integer favourCount;//消息点赞数
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm",iso= DateTimeFormat.ISO.DATE_TIME)
	private Date createTime;//创建时间
}
