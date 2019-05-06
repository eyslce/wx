package com.eyslce.wx.mp.service;

import com.eyslce.wx.mp.domain.UserTag;
import com.github.pagehelper.PageInfo;

public interface IUserTagService {
    public UserTag getById(Long id);

    public PageInfo<UserTag> listForPage(UserTag searchEntity);

    public void add(UserTag entity);

    public void update(UserTag entity);

    public void delete(UserTag entity);

    public Integer deleteBatchIds(String[] ids);

    //获取数据库中用户标签的最大值，判断是否同步
    public Integer getMaxId();
}
