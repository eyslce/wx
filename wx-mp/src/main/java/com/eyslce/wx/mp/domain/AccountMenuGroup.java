
package com.eyslce.wx.mp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class AccountMenuGroup  {
	private Long id;
	private String name;
	private Integer enable;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
	private Date createTime;//创建时间
}
