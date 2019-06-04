package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.MsgArticleDao;
import com.eyslce.wx.mp.dao.MsgNewsDao;
import com.eyslce.wx.mp.domain.MsgArticle;
import com.eyslce.wx.mp.domain.MsgNews;
import com.eyslce.wx.mp.service.IMsgArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgArticleServiceImpl implements IMsgArticleService {
    @Autowired
    private MsgArticleDao articleDao;

    @Autowired
    private MsgNewsDao msgNewsDao;

    @Override
    public List<MsgArticle> getByNewsId(int id) {
        return null;
    }

    @Override
    public MsgArticle getById(int id) {
        return null;
    }

    @Override
    public void add(MsgArticle article) {

    }

    @Override
    public void insertByBatch(List<MsgArticle> articles) {

    }

    @Override
    public void update(MsgArticle article) {
        if (article.getNewsIndex() == 0) {
            MsgNews news = msgNewsDao.getById(String.valueOf(article.getNewsId()));
            news.setTitle(article.getTitle());
            news.setAuthor(article.getAuthor());
            news.setBrief(article.getDigest());
            news.setDescription(article.getContent());
            news.setPicpath(article.getPicUrl());
            news.setThumbMediaId(article.getThumbMediaId());
            news.setFromurl(article.getContentSourceUrl());
            news.setShowpic(article.getShowCoverPic());
            msgNewsDao.update(news);
        }
        articleDao.update(article);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteByBatch(int id) {

    }
}
