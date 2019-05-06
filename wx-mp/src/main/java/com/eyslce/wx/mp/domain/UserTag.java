package com.eyslce.wx.mp.domain;

import com.eyslce.wx.mp.query.PageQuery;
import lombok.Data;

import java.util.Date;


@Data
public class UserTag extends PageQuery {

    private Long id;// 主键ID

    private String name;// 标签名称

    private Integer count = 0;// 该标签的粉丝数量

    private Date createTime = new Date();// 创建时间
}
