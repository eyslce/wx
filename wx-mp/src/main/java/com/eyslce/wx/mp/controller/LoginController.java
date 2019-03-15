package com.eyslce.wx.mp.controller;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.mp.domain.SysUser;
import com.eyslce.wx.mp.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录验证、密码修改
 */
@Controller
public class LoginController extends BaseController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

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
            return this.error("用户名或者密码错误");
        }
        session.setAttribute("user", user);
        return this.success("登录成功");
    }

    @RequestMapping("/logout")
    @ResponseBody
    public HttpResult logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return this.success("登出成功");
    }

    @PostMapping("/updatePwd")
    @ResponseBody
    public HttpResult updatePwd(HttpServletRequest request, SysUser sysUser) {
        SysUser user = (SysUser) session.getAttribute("user");
        if (!user.getPwd().equals(DigestUtils.md5Hex(sysUser.getPwd()))) {
            return this.error("密码错误");
        }
        sysUser.setNewpwd(DigestUtils.md5Hex(sysUser.getNewpwd()));
        userService.updateLoginPwd(sysUser);
        request.getSession().invalidate();
        return this.success("更新成功");
    }

}
