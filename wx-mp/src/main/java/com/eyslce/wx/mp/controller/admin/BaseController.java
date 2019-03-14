package com.eyslce.wx.mp.controller.admin;

import com.alibaba.fastjson.JSON;
import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.result.Page;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.mp.domain.SysUser;
import com.github.pagehelper.PageInfo;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    protected HttpResult success() {
        return HttpResult.builder()
                .msg(Constant.SUCCESS_MSG)
                .success(true)
                .build();
    }

    protected <T> HttpResult success(PageInfo<T> pageInfo) {
        return HttpResult.builder()
                .msg(Constant.SUCCESS_MSG)
                .success(true)
                .data(pageInfo.getList())
                .page(Page.builder()
                        .page(pageInfo.getPageNum())
                        .pageSize(pageInfo.getPageSize())
                        .total(pageInfo.getSize())
                        .totalPage(pageInfo.getPages())
                        .build())
                .build();
    }

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
                .success(false)
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
                .success(false)
                .data(data)
                .build();
    }

    @ExceptionHandler(Throwable.class)
    public void handleException(Throwable e, HttpServletRequest request, HttpServletResponse response) {
        logger.error(request.getRequestURI(), e);
        if (isAjaxRequest(request)) {
            HttpResult httpResult = error("程序出现异常");
            response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            try {
                JSON.writeJSONString(response.getWriter(), httpResult);
            } catch (IOException e1) {
                logger.error("exception json error", e);
            }
        } else {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("timestamp", new Date());
                map.put("message", "程序出现异常");
                thymeleafViewResolver.resolveViewName("admin/error", Locale.CHINA).render(map, request, response);
            } catch (Exception e1) {
                logger.error("exception view error", e1);
            }
        }
    }

    protected boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjax = request.getHeader("x-requested-with") != null &&
                request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
        return isAjax;
    }

    /**
     * 获取账户名
     *
     * @return
     */
    protected String getUserName() {
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if (null == user) {
            return null;
        }
        return user.getAccount();
    }
}
