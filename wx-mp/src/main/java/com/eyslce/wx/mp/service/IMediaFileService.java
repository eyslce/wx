package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.MediaFiles;
import com.github.pagehelper.PageInfo;

public interface IMediaFileService {
    void add(MediaFiles entity);

    PageInfo<MediaFiles> getMediaListByPage(MediaFiles mediaFiles);

    void deleteByMediaId(String mediaId);
}
