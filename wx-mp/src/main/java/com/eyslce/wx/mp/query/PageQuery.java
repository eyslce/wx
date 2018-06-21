package com.eyslce.wx.mp.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageQuery {
    private int page = 1;// 当前页索引
    private int pageSize = 20;// 每页显示的数据条数
    private String account;
}
