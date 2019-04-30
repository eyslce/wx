package com.eyslce.wx.mp.controller;

import com.eyslce.wx.commons.util.WxConfigurationProperties;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class IndexController extends BaseController {
    Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    WxConfigurationProperties wxConfigurationProperties;

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hello Wx Mp";
    }

    @GetMapping("/images/{fileName}.{ext}")
    public void image(@PathVariable("fileName") String fileName, @PathVariable("ext") String ext, HttpServletResponse response) {
        try {
            String file = wxConfigurationProperties.getUpload_dir() + fileName + "." + ext;
            File imageFile = FileUtils.getFile(file);
            if (!imageFile.isFile()) {
                response.sendRedirect("/images/common/login-bg.jpg");
                return;
            }
            byte[] content = FileUtils.readFileToByteArray(imageFile);
            response.getOutputStream().write(content);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.error("image not found", e);
        }
    }

}
