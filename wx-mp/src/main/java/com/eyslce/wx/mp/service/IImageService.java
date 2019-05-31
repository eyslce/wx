package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.query.ImgQuery;
import com.github.pagehelper.PageInfo;

public interface IImageService {
    void add(ImgResource img);

    ImgResource getImg(String id);

    boolean delImg(String id);

    PageInfo<ImgResource> getImgListByPage(ImgQuery imgQuery);
}
