package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.TplMsgText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TplMsgTextDao {

	TplMsgText getById(String id);

	List<TplMsgText> getTplMsgTextByPage(TplMsgText searchEntity);

	List<TplMsgText> getTplMsgTextList(TplMsgText searchEntity);

	void add(TplMsgText entity);

	void update(TplMsgText entity);

	void delete(TplMsgText entity);

	TplMsgText getByBaseId(String baseid);
}
