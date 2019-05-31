package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.commons.util.Constant;
import com.eyslce.wx.commons.util.StringUtils;
import com.eyslce.wx.mp.dao.ImgResourceDao;
import com.eyslce.wx.mp.dao.MediaFilesDao;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.query.ImgQuery;
import com.eyslce.wx.mp.service.IImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        img.setFlag(Constant.IMG_FLAG0);
        img.setId(StringUtils.uuid());
        img.setCreateTime(new Date());
        img.setUpdateTime(new Date());
        imgResourceDao.add(img);

        //添加base表
        MsgBase base = new MsgBase();
        base.setCreateTime(new Date());
        base.setMsgType(img.getType());
        baseDao.add(base);
        //添加到素材表中
        MediaFiles entity = new MediaFiles();
        entity.setMediaId(img.getMediaId());
        entity.setMediaType(img.getType());
        entity.setBaseId(base.getId());
        entity.setCreateTime(new Date(System.currentTimeMillis()));
        entity.setUpdateTime(new Date(System.currentTimeMillis()));
        mediaFilesDao.add(entity);
    }

    @Override
    public ImgResource getImg(String id) {
        return imgResourceDao.getImgById(id);
    }

    @Override
    public boolean delImg(String id) {
        ImgResource img = imgResourceDao.getImgById(id);
        if (img == null) {
            return true;
        }
        imgResourceDao.deleteByMediaId(img.getMediaId());
        mediaFilesDao.deleteByMediaId(img.getMediaId());
        return true;
    }

    @Override
    public PageInfo<ImgResource> getImgListByPage(ImgQuery imgQuery) {
        PageHelper.startPage(imgQuery.getPage(), imgQuery.getPageSize());
        List<ImgResource> list = imgResourceDao.getImgListByPage(imgQuery);
        PageInfo<ImgResource> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
