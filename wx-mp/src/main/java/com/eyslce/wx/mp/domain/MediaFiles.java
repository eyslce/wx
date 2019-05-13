
package com.eyslce.wx.mp.domain;

import com.eyslce.wx.mp.query.PageQuery;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class MediaFiles extends PageQuery {

	private Long id;
   	private String mediaType;//素材类型
   	private String title;//视频标题
   	private String introduction;//视频描述
   	private String logicClass;//标签_逻辑分类
   	private String mediaId;//素材media_id
   	private String uploadUrl;//项目中上传路径
   	private String rmk;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
   	private Date createTime;
   	private Date updateTime;
   	private Long baseId;//消息主表id


	private String start;

	private String end;

	private String url;//文件绝对路径
}
