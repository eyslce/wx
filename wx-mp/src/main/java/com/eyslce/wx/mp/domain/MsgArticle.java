
package com.eyslce.wx.mp.domain;


import lombok.Data;




@Data
public class MsgArticle   {

	private Integer arId;
	private String title;
	private String author;
	private String content;
	private String digest;
	private Integer showCoverPic;
	private String picUrl;
	private String url;
	private String thumbMediaId;
	private String contentSourceUrl;
	private String mediaId;
	private Integer newsIndex;
	private Integer newsId;
}
