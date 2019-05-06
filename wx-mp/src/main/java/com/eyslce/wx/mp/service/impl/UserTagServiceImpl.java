package com.eyslce.wx.mp.service.impl;

import com.eyslce.wx.mp.dao.UserTagDao;
import com.eyslce.wx.mp.domain.UserTag;
import com.eyslce.wx.mp.service.IUserTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTagServiceImpl implements IUserTagService {
    @Autowired
    private UserTagDao userTagDao;


    @Override
    public UserTag getById(Long id) {
        return userTagDao.getById(id);
    }

    @Override
    public PageInfo<UserTag> listForPage(UserTag searchEntity) {
        PageHelper.startPage(searchEntity.getPage(), searchEntity.getPageSize());
        List<UserTag> list = userTagDao.getUserTagListByPage(searchEntity);
        PageInfo<UserTag> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void add(UserTag entity) {
        userTagDao.add(entity);
    }

    @Override
    public void update(UserTag entity) {
        userTagDao.update(entity);
    }

    @Override
    public void delete(UserTag entity) {
        userTagDao.delete(entity);
    }

    @Override
    public Integer deleteBatchIds(String[] ids) {
        return userTagDao.deleteBatchIds(ids);
    }

    @Override
    public Integer getMaxId() {
        return userTagDao.getMaxId();
    }
}
