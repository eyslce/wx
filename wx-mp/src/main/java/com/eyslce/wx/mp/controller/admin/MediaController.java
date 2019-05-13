package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.service.IImageService;
import com.eyslce.wx.mp.service.IMediaFileService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("admin/media")
public class MediaController extends BaseController {
    @Autowired
    private WxConfigurationProperties wxConfig;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IMediaFileService mediaFileService;

    @RequestMapping("document")
    public String document() {
        return "admin/media/document";
    }

    @RequestMapping("image")
    public String image() {
        return "admin/media/image";
    }

    @GetMapping("image/list")
    public String imageList() {
        return "admin/media/image/list";
    }

    @RequestMapping("video")
    public String video() {
        return "admin/media/video";
    }

    @RequestMapping("audio")
    public String audio() {
        return "admin/media/audio";
    }

    @PostMapping("addMateria")
    @ResponseBody
    public HttpResult addMateria(MultipartFile file, String type) throws IOException, WxErrorException {
        if (null == file) {
            return error("没有文件上传");
        }
        //获取扩展名
        String trueName = file.getOriginalFilename();
        String ext = FilenameUtils.getExtension(trueName);
        String path = wxConfig.getUpload_dir() + type;
        String name = System.currentTimeMillis() + "." + ext;
        //生成新名称
        File newFile = new File(path, name);
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        //保存文件
        file.transferTo(newFile);
        //上传微信
        WxMpMaterial material = new WxMpMaterial();
        material.setFile(newFile);
        material.setName(file.getName());
        if (type.equals(WxConsts.MediaFileType.VIDEO)) {
            material.setVideoTitle(file.getName());
        }
        WxMpMaterialUploadResult result = wxMpService.getMaterialService().materialFileUpload(type, material);

        if (type.equals(WxConsts.MediaFileType.IMAGE) || type.equals(WxConsts.MediaFileType.THUMB)) {
            ImgResource imgResource = ImgResource.builder()
                    .httpUrl(result.getUrl())
                    .mediaId(result.getMediaId())
                    .size(file.getSize())
                    .name(name)
                    .trueName(trueName)
                    .type(type)
                    .url(type + File.pathSeparator + name)
                    .build();
            imageService.add(imgResource);
            return success(imgResource, Constant.SUCCESS_MSG);
        }
        MediaFiles mediaFile = new MediaFiles();
        mediaFile.setUploadUrl(result.getUrl());
        mediaFile.setUrl(type + File.pathSeparator + name);
        mediaFile.setTitle(file.getName());//用title保存文件名
        mediaFile.setMediaId(result.getMediaId());
        mediaFile.setMediaType(type);
        mediaFile.setCreateTime(new Date(System.currentTimeMillis()));
        mediaFile.setUpdateTime(new Date(System.currentTimeMillis()));
        mediaFileService.add(mediaFile);
        return success();
    }

    @PostMapping("image/list")
    @ResponseBody
    public HttpResult imageListPage() {
        return success();
    }
}