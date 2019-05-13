package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.ImgResource;

public interface IImageService {
    void add(ImgResource img);

    ImgResource getImg(String id);

    boolean delImg(String id);
}
