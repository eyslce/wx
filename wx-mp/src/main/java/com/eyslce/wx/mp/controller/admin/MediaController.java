package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.mp.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/media")
public class MediaController extends BaseController {
    @RequestMapping("document")
    public String document() {
        return "admin/media/document";
    }

    @RequestMapping("image")
    public String image() {
        return "admin/media/image";
    }

    @RequestMapping("video")
    public String video() {
        return "admin/media/video";
    }

    @RequestMapping("audio")
    public String audio() {
        return "admin/media/audio";
    }
}
