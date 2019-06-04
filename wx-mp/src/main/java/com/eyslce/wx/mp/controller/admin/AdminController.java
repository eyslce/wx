package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.service.ISysConfigService;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping("admin")
public class AdminController extends BaseController {

    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    WxConfigurationProperties wxConfigurationProperties;

    @GetMapping(value = {"","/", "/index"})
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin/index");
        view.addObject("username", getUserName());
        return view;
    }

    @GetMapping(value = {"content"})
    public String content() {
        return "admin/content";
    }

    @GetMapping("environment")
    public String environment() {
        return "admin/environment";
    }

    @GetMapping("system")
    public String system() {
        return "admin/system";
    }

    @GetMapping("nav")
    public String nav() {
        return "admin/nav";
    }

    @PostMapping("sysinfo")
    @ResponseBody
    public HttpResult getSystemInfo(HttpServletRequest request) {
        Properties props = System.getProperties();
        //java版本
        String javaVersion = props.getProperty("java.version");
        //操作系统名称
        String osName = props.getProperty("os.name") + props.getProperty("os.version");
        //用户的主目录
        String userHome = props.getProperty("user.home");
        //用户的当前工作目录
        String userDir = props.getProperty("user.dir");
        //服务器IP
        String serverIP = request.getLocalAddr();
        //客户端IP
        String clientIP = request.getRemoteHost();
        //WEB服务器
        String webVersion = request.getServletContext().getServerInfo();
        //CPU个数
        String cpu = Runtime.getRuntime().availableProcessors() + "核";
        //虚拟机内存总量
        String totalMemory = (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "M";
        //虚拟机空闲内存量
        String freeMemory = (Runtime.getRuntime().freeMemory() / 1024 / 1024) + "M";
        //虚拟机使用的最大内存量
        String maxMemory = (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "M";
        //MYSQL版本
        String mysqlVersion = sysConfigService.getMysqlVsesion();
        //网站根目录
        String webRootPath = request.getSession().getServletContext().getRealPath("");
        Map<String, String> propsMap = ImmutableMap.<String, String>builder()
                .put("javaVersion", javaVersion)
                .put("osName", osName)
                .put("userHome", userHome)
                .put("userDir", userDir)
                .put("clientIP", clientIP)
                .put("serverIP", serverIP)
                .put("cpu", cpu)
                .put("totalMemory", totalMemory)
                .put("freeMemory", freeMemory)
                .put("maxMemory", maxMemory)
                .put("webVersion", webVersion)
                .put("mysqlVersion", mysqlVersion)
                .put("webRootPath", webRootPath)
                .put("systemVersion", Constant.SYSTEM_VERSION)
                .put("systemName", Constant.SYSTEM_NAME)
                .put("systemUpdateTime", Constant.SYSTEM_UPDATE_TIME)
                .build();
        return this.success(propsMap, "操作成功");
    }

    @GetMapping("/file/{type}/{fileName}.{ext}")
    public void image(@PathVariable("type") String type, @PathVariable("fileName") String fileName, @PathVariable("ext") String ext, HttpServletResponse response) {
        try {
            String filePath = wxConfigurationProperties.getUpload_dir() + type + "/" + fileName + "." + ext;
            File file = FileUtils.getFile(filePath);
            if (!file.isFile()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            byte[] content = FileUtils.readFileToByteArray(file);
            response.getOutputStream().write(content);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
