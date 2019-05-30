package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.commons.util.StringUtils;
import com.eyslce.wx.mp.dao.ImgResourceDao;
import com.eyslce.wx.mp.dao.MediaFilesDao;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImageServiceImpl implements IImageService {
    @Autowired
    private ImgResourceDao imgResourceDao;
    @Autowired
    private MediaFilesDao mediaFilesDao;
    @Autowired
    private MsgBaseDao baseDao;

    @Override
    public void add(ImgResource img) {
        img.setId(StringUtils.uuid());
        img.setCreateTime(new Date());
    }

    @Override
    public ImgResource getImg(String id) {
        return null;
    }

    @Override
    public boolean delImg(String id) {
        return false;
    }
}
