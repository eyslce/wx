package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgTextDao {

	MsgText getById(String id);

	List<MsgText> getMsgTextByPage(MsgText searchEntity);

	List<MsgText> getMsgTextList(MsgText searchEntity);

	void add(MsgText entity);

	void update(MsgText entity);

	void delete(MsgText entity);

	MsgText getRandomMsg(String inputCode);

	MsgText getRandomMsg2();

	MsgText getByBaseId(String baseid);
}
