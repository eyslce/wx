package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.MediaFilesDao;
import com.eyslce.wx.mp.dao.MsgArticleDao;
import com.eyslce.wx.mp.dao.MsgBaseDao;
import com.eyslce.wx.mp.dao.MsgNewsDao;
import com.eyslce.wx.mp.domain.MediaFiles;
import com.eyslce.wx.mp.domain.MsgArticle;
import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.domain.MsgNews;
import com.eyslce.wx.mp.service.IMsgNewsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MsgNewsImpl implements IMsgNewsService {
    @Autowired
    private MsgNewsDao msgNewsDao;
    @Autowired
    private MsgBaseDao baseDao;
    @Autowired
    private MediaFilesDao mediaFilesDao;
    @Autowired
    private MsgArticleDao articleDao;

    @Override
    public MsgNews getById(String id) {
        return msgNewsDao.getById(id);
    }

    @Override
    public PageInfo<MsgNews> listForPage(MsgNews searchEntity) {
        PageHelper.startPage(searchEntity.getPage(), searchEntity.getPageSize());
        List<MsgNews> list = msgNewsDao.listForPage(searchEntity);
        PageInfo<MsgNews> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List<MsgNews> getByMediaId(String mediaId) {
        return null;
    }

    @Override
    public PageInfo<MsgNews> getWebNewsListByPage(MsgNews searchEntity) {
        PageHelper.startPage(searchEntity.getPage(), searchEntity.getPageSize());
        List<MsgNews> list = msgNewsDao.getWebNewsListByPage(searchEntity);
        PageInfo<MsgNews> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void add(MsgNews entity) {

    }

    @Override
    public void update(MsgNews entity) {

    }

    @Override
    public void delete(MsgNews entity) {
        MsgBase base = new MsgBase();
        base.setId(entity.getBaseId());
        articleDao.deleteByBatch(entity.getId().intValue());
        msgNewsDao.delete(entity);
        baseDao.delete(entity);
    }

    @Override
    public List<MsgNews> getRandomMsg(String inputcode, Integer num) {
        return null;
    }

    @Override
    public MsgNews getByBaseId(String baseid) {
        return null;
    }

    @Override
    public int updateMediaId(MsgNews entity) {
        int n = 0;
        try {
            msgNewsDao.updateMediaId(entity);
            n = 1;
        } catch (Exception e) {
            //
        }
        return n;
    }

    @Override
    @Transactional
    public int addSingleNews(MsgNews news, MediaFiles entity) {
        int n = 0;
        int m = 0;
        try {
            //保存基本消息
            MsgBase base = new MsgBase();
            base.setCreateTime(new Date());
            base.setMsgType(WxConsts.MaterialType.NEWS);
            baseDao.add(base);
            //保存图文信息
            news.setCreateTime(new Date());
            news.setBaseId(base.getId());
            Integer newId = this.msgNewsDao.addNews(news);
            MsgArticle art = new MsgArticle();
            art.setAuthor(news.getAuthor());
            art.setContent(news.getDescription());
            art.setContentSourceUrl(news.getFromurl());
            art.setDigest(news.getBrief());
            art.setMediaId(news.getMediaId());
            art.setNewsIndex(0);
            art.setPicUrl(news.getPicpath());
            art.setShowCoverPic(news.getShowpic());
            art.setThumbMediaId(news.getThumbMediaId());
            art.setTitle(news.getTitle());
            art.setUrl(news.getUrl());

            art.setNewsId(news.getId().intValue());
            articleDao.add(art);
            n = 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (n > 0) {
            mediaFilesDao.add(entity);
            m = 1;
        }
        if (n > 0 && m > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int addMediaFiles(MediaFiles entity) {
        return 0;
    }

    @Override
    @Transactional
    public int addMoreNews(MsgNews news) {
        int n = 0;

        try {
            List<MsgArticle> articles = news.getArticles();
            List<MsgArticle> list = new ArrayList<MsgArticle>();
            //保存基本消息
            MsgBase base = new MsgBase();
            base.setCreateTime(new Date());
            base.setMsgType(WxConsts.MaterialType.NEWS);
            baseDao.add(base);
            news.setBaseId(base.getId());
            news.setCreateTime(new Date());
            //保存图文信息
            this.msgNewsDao.addNews(news);
            for (int i = 0; i < articles.size(); i++) {
                MsgArticle article = articles.get(i);
                article.setNewsId(news.getId().intValue());
                list.add(article);
            }
            articleDao.insertByBatch(list);
            n = 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (n == 1) {
            MediaFiles entity = new MediaFiles();
            entity.setMediaId(news.getMediaId());
            entity.setMediaType("news");
            entity.setCreateTime(news.getCreateTime());
            entity.setUpdateTime(news.getCreateTime());
            mediaFilesDao.add(entity);
        }
        return n;
    }

    @Override
    public Boolean addMoreNews(List<MsgNews> news) {
        return null;
    }

    @Override
    public List<MediaFiles> getMediaFileList() {
        return null;
    }

    @Override
    @Transactional
    public void updateSingleNews(MsgNews news) {
        MsgArticle art = new MsgArticle();
        art.setAuthor(news.getAuthor());
        art.setContent(news.getDescription());
        art.setContentSourceUrl(news.getFromurl());
        art.setDigest(news.getBrief());
        art.setMediaId(news.getMediaId());
        art.setNewsIndex(0);
        art.setPicUrl(news.getPicpath());
        art.setShowCoverPic(news.getShowpic());
        art.setThumbMediaId(news.getThumbMediaId());
        art.setTitle(news.getTitle());
        art.setUrl(news.getUrl());

        art.setNewsId(news.getId().intValue());
        int arId = articleDao.getByNewsId(news.getId().intValue()).get(0).getArId();
        art.setArId(arId);
        articleDao.update(art);
        this.msgNewsDao.updateNews(news);
    }

}
