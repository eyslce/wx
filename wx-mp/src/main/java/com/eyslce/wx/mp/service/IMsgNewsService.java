package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.domain.MsgNews;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IMsgNewsService {
    MsgNews getById(String id);

    PageInfo<MsgNews> listForPage(MsgNews searchEntity);

    List<MsgNews> getByMediaId(String mediaId);

    PageInfo<MsgNews> getWebNewsListByPage(MsgNews searchEntity);

    void add(MsgNews entity);

    void update(MsgNews entity);

    void delete(MsgNews entity);

    //根据用户发送的文本消息，随机获取 num 条文本消息
    List<MsgNews> getRandomMsg(String inputcode, Integer num);

    MsgNews getByBaseId(String baseid);

    int updateMediaId(MsgNews entity);

    int addSingleNews(MsgNews news, MediaFiles entity);

    int addMediaFiles(MediaFiles entity);

    int addMoreNews(MsgNews news);

    //多图文添加
    Boolean addMoreNews(List<MsgNews> news);

    List<MediaFiles> getMediaFileList();

    void updateSingleNews(MsgNews news);
}
