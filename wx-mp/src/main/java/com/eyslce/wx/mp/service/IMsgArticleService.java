package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.MsgArticle;

import java.util.List;

public interface IMsgArticleService {
    List<MsgArticle> getByNewsId(int id);

    MsgArticle getById(int id);

    void add(MsgArticle article);

    void insertByBatch(List<MsgArticle> articles);

    void update(MsgArticle article);

    void delete(int id);

    void deleteByBatch(int id);
}
