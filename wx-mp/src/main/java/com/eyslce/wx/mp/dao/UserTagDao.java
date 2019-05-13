package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.UserTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserTagDao {

    UserTag getById(Long id);

    List<UserTag> getUserTagListByPage(UserTag searchEntity);

    void add(UserTag entity);

    void addList(List<UserTag> list);

    void update(UserTag entity);

    void delete(UserTag entity);

    Integer deleteBatchIds(String[] ids);

    Integer getMaxId();
}
