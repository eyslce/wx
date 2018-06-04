
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgNews;

import java.util.List;


public interface MsgNewsDao {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);
	
	public List<MsgNews> getByMediaId(String mediaId);
	
	public List<MsgNews> getWebNewsListByPage(MsgNews searchEntity);

	public void add(MsgNews entity);

	public void update(MsgNews entity);
	
	public void updateUrl(MsgNews entity);

	public void delete(MsgNews entity);

	public List<MsgNews> getRandomMsg(Integer num);

	public List<MsgNews> getRandomMsgByContent(String inputcode, Integer num);
	
	public List<MsgNews> getMsgNewsByIds(String[] array);

	public MsgNews getByBaseId(String baseid);
	
	/**
	 * 同步到微信后更新表
	 * @param entity
	 */
	public void updateMediaId(MsgNews entity);
	
	/**
	 * 保存图文消息
	 * @param entity
	 */
	public Integer addNews(MsgNews entity);
	
	/**
	 * 查询图文列表
	 * @return
	 */
	public List<MsgNews> getMsgNewsList();
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteByMediaId(String mediaId);
	
	/**
	 * 更新图文媒体素材
	 * @param entity
	 */
	public void updateNews(MsgNews entity);
}
