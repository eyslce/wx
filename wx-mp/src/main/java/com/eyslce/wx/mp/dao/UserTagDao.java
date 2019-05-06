package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.UserTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserTagDao {

    public UserTag getById(Long id);

    public List<UserTag> getUserTagListByPage(UserTag searchEntity);

    public void add(UserTag entity);

    public void addList(List<UserTag> list);

    public void update(UserTag entity);

    public void delete(UserTag entity);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param ids 主键ID列表
     * @return int
     */
    Integer deleteBatchIds(String[] ids);

    /**
     * 获取最大的ID和本地库存的比较决定是否同步
     *
     * @return
     */
    public Integer getMaxId();
}
