package com.eyslce.wx.mp.controller;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.mp.domain.SysUser;
import com.eyslce.wx.mp.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private HttpSession session;
    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public HttpResult login(SysUser user) {
        user.setPwd(DigestUtils.md5Hex(user.getPwd()));
        SysUser sysUser = userService.getSysUser(user);
        if (null == sysUser) {
            return HttpResult.builder()
                    .msg("用户名或者密码错误")
                    .success(false)
                    .build();
        }
        session.setAttribute("user", user);
        return HttpResult.builder()
                .msg("登录成功")
                .success(true)
                .build();
    }

    @RequestMapping("/logout")
    @ResponseBody
    public HttpResult logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return HttpResult.builder()
                .success(true)
                .msg("操作成功")
                .build();
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public HttpResult updatePwd(HttpServletRequest request, SysUser sysUser) {
        SysUser user = (SysUser) session.getAttribute("user");
        if (!user.getPwd().equals(DigestUtils.md5Hex(sysUser.getPwd()))) {
            return HttpResult.builder()
                    .success(false)
                    .msg("密码错误")
                    .build();
        }
        sysUser.setNewpwd(DigestUtils.md5Hex(sysUser.getNewpwd()));
        userService.updateLoginPwd(sysUser);
        request.getSession().invalidate();
        return HttpResult.builder()
                .msg("更新成功")
                .success(true)
                .build();
    }

}
