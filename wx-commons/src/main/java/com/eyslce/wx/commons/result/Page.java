package com.eyslce.wx.commons.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Page {
    private int page = 1;// 当前页索引

    private int pageSize = 20;// 每页显示的数据条数

    private int total = 0;// 总条数

    private int totalPage = 1;// 总页数
}
