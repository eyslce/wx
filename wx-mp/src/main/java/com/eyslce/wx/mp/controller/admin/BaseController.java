package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 成功返回，不带data
     *
     * @param msg
     * @return
     */
    protected HttpResult success(String msg) {
        return HttpResult.builder()
                .msg(msg)
                .success(true)
                .build();
    }

    /**
     * 成功返回带data
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    protected <T> HttpResult<T> success(T data, String msg) {
        return HttpResult.<T>builder()
                .msg(msg)
                .success(true)
                .data(data)
                .build();
    }

    /**
     * 失败时返回不带data
     *
     * @param msg
     * @return
     */
    protected HttpResult error(String msg) {
        return HttpResult.builder()
                .msg(msg)
                .success(true)
                .build();
    }

    /**
     * 失败时返回带data
     *
     * @param data
     * @param msg
     * @param <T>
     * @return
     */
    protected <T> HttpResult<T> error(T data, String msg) {
        return HttpResult.<T>builder()
                .msg(msg)
                .success(true)
                .data(data)
                .build();
    }
}
