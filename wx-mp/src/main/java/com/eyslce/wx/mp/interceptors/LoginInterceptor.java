package com.eyslce.wx.mp.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (null == user) {
            if(isAjaxRequest(request)){
                response.setStatus(401);
            }else{
                response.sendRedirect("/login");
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        boolean isAjax = request.getHeader("x-requested-with") != null &&
                request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
        return isAjax;
    }
}
