
package com.eyslce.wx.mp.domain;


import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class ImgResource {
    private String  id;
	/**
	 * 图片原名称
	 */
	private String  trueName;
	
	/**
	 * 微信返回的mediaId
	 */
	private String mediaId;
	
	/**
	 * 图片尾缀名类型
	 */
	private String  type;
	
	/**
	 * 图片存储名称
	 */
	private String  name;
	
	/**
	 * 图片路径
	 */
	private String  url;
	
	/**
	 * 图片http访问路径
	 */
	private String  httpUrl;	
	
	/**
	 * 图片大小byte
	 */
	private Long size;
	
	/**
	 * 创建时间
	 */
	private Date  createTime;
	
	/**
	 * 修改时间
	 */
	private Date  updateTime;

	/**
	 * 图片状态字段：0.未引用 ，1.已被引用
	 */
	private Integer flag;


	private String start;

	private String end;

}
