
package com.eyslce.wx.mp.domain;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Date;

;


@Data
public class AccountFans {
	private Long id;
	private String openId;//openId，每个用户都是唯一的
	private Integer subscribeStatus;//订阅状态
	private Date subscribeTime;//订阅时间
	private byte[] nickname;//昵称,二进制保存emoji表情
	private String nicknameStr;//昵称显示
	private String wxid;//微信号
	private Integer gender;//性别 0-女；1-男；2-未知
	private String language;//语言
	private String country;//国家
	private String province;//省
	private String city;//城市
	private String headimgurl;//头像
	private String remark;//备注
	private Integer status;//用户状态 1-可用；0-不可用
	private Date createTime;//创建时间
	private String account;

	public String getNicknameStr() {
		if (this.getNickname() != null) {
			this.nicknameStr = new String(this.getNickname(), StandardCharsets.UTF_8);
		}
		return nicknameStr;
	}
}
