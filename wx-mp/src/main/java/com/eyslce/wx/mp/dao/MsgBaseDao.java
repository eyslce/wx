package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgBase;
import com.eyslce.wx.mp.domain.MsgNews;
import com.eyslce.wx.mp.domain.MsgText;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgBaseDao {

	MsgBase getById(String id);

	List<MsgBase> listForPage(MsgBase searchEntity);

	List<MsgNews> listMsgNewsByBaseId(String[] ids);

	MsgText getMsgTextByBaseId(String id);

	MsgText getMsgTextBySubscribe();

	MsgText getMsgTextByInputCode(String inputcode);

	Integer add(MsgBase entity);

	void update(MsgBase entity);

	void updateInputCode(MsgBase entity);

	void delete(MsgBase entity);

}
