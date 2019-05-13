package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.MediaFilesDao;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.service.IMediaFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MediaFileServiceImpl implements IMediaFileService {
    @Autowired
    private MediaFilesDao mediaFilesDao;
    @Autowired
    private MsgBaseDao msgBaseDao;

    @Override
    public void add(MediaFiles entity) {
        MsgBase base = new MsgBase();
        base.setCreateTime(new Date());
        base.setMsgType(entity.getMediaType());
        msgBaseDao.add(base);
        //关联回复表
        entity.setBaseId(base.getId());
        //需要对base表添加数据
        mediaFilesDao.add(entity);
    }

    @Override
    public PageInfo<MediaFiles> getMediaListByPage(MediaFiles mediaFiles) {
        PageHelper.startPage(mediaFiles.getPage(), mediaFiles.getPageSize());
        List<MediaFiles> list = mediaFilesDao.getMediaListByPage(mediaFiles);
        PageInfo<MediaFiles> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void deleteByMediaId(String mediaId) {
        mediaFilesDao.deleteByMediaId(mediaId);
    }
}
