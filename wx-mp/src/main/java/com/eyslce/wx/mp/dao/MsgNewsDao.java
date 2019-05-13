
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgNewsDao {

	MsgNews getById(String id);

	List<MsgNews> listForPage(MsgNews searchEntity);

	List<MsgNews> getByMediaId(String mediaId);

	List<MsgNews> getWebNewsListByPage(MsgNews searchEntity);

	void add(MsgNews entity);

	void update(MsgNews entity);

	void updateUrl(MsgNews entity);

	void delete(MsgNews entity);

	List<MsgNews> getRandomMsg(Integer num);

	List<MsgNews> getRandomMsgByContent(String inputcode, Integer num);

	List<MsgNews> getMsgNewsByIds(String[] array);

	MsgNews getByBaseId(String baseid);

	void updateMediaId(MsgNews entity);

	Integer addNews(MsgNews entity);

	List<MsgNews> getMsgNewsList();

	void deleteByMediaId(String mediaId);

	void updateNews(MsgNews entity);
}
