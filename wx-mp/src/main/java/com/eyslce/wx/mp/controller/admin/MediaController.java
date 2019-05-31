package com.eyslce.wx.mp.controller.admin;

import com.eyslce.wx.commons.result.HttpResult;
import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.WxConfigurationProperties;
import com.eyslce.wx.mp.controller.BaseController;
import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.query.ImgQuery;
import com.eyslce.wx.mp.service.IImageService;
import com.eyslce.wx.mp.service.IMediaFileService;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import org.apache.commons.io.FileUtils;
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
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("video/add")
    public String videoAdd() {
        return "admin/media/video/add";
    }

    @RequestMapping("video")
    public String video() {
        return "admin/media/video";
    }

    @RequestMapping("audio")
    public String audio() {
        return "admin/media/audio";
    }

    @RequestMapping("list")
    @ResponseBody
    public HttpResult list(MediaFiles mediaFiles) {
        PageInfo<MediaFiles> pageInfo = mediaFileService.getMediaListByPage(mediaFiles);
        return success(pageInfo);
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
            FileUtils.forceMkdirParent(newFile);
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

    @ResponseBody
    @RequestMapping("delMediaFile")
    public HttpResult delMediaFile(String mediaId) throws WxErrorException {
        wxMpService.getMaterialService().materialDelete(mediaId);
        mediaFileService.deleteByMediaId(mediaId);
        return success();
    }

    @PostMapping("image/list")
    @ResponseBody
    public HttpResult imageListPage(ImgQuery imgQuery) {
        PageInfo<ImgResource> pageInfo = imageService.getImgListByPage(imgQuery);
        return success(pageInfo);
    }

    @ResponseBody
    @RequestMapping("image/del")
    public HttpResult delMediaImg(String id) throws Exception {
        ImgResource img = imageService.getImg(id);
        if (img == null) {
            return success();
        }
        wxMpService.getMaterialService().materialDelete(img.getMediaId());
        imageService.delImg(id);
        return success();
    }

    @ResponseBody
    @RequestMapping("uploadVideoFile")
    public HttpResult uploadFile(MultipartFile file) throws Exception {
        //原文件名称
        String fileName = file.getOriginalFilename();
        //文件后缀名
        String ext = FilenameUtils.getExtension(fileName);
        String path = wxConfig.getUpload_dir() + WxConsts.MediaFileType.VIDEO + File.pathSeparator;
        String name = System.currentTimeMillis() + "." + ext;

        File saveFile = new File(path, name);
        if (!saveFile.exists()) {
            FileUtils.forceMkdirParent(saveFile);
        }
        file.transferTo(saveFile);
        //构造返回参数
        Map<String, Object> mapData = new HashMap();
        mapData.put("src", path + name);//文件url
        mapData.put("url", path + name);//文件绝对路径url
        mapData.put("title", fileName);//图片名称，这个会显示在输入框里
        return success(mapData, Constant.SUCCESS_MSG);
    }
}
