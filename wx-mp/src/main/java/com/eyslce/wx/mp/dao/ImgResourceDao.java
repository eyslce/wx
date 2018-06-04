
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.ImgResource;

import java.util.List;



public interface ImgResourceDao {
	
	/**
	 * 分页查询
	 * @param entity
	 * @return
	 */
	public List<ImgResource> getImgListByPage(ImgResource entity);
	/**
	 * 获取图片信息
	 * @param id
	 * @return
	 */
	public ImgResource getImgById(String id);
	
	/**
	 * 创建资源
	 * @param img
	 * @return
	 */
	public void add(ImgResource img);
	
	/**
	 * 删除中间表记录
	 * @param otherId
	 * @return
	 */
	public void deleteByMediaId(String otherId);
	
}
