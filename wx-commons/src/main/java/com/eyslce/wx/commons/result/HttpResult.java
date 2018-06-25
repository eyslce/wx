package com.eyslce.wx.commons.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HttpResult<T> {
    /**
     * 成功标识：true/false
     */
    private boolean success;
    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;
    /**
     * 分页对象
     */
    private Page page;
}
