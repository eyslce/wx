
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MediaFiles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MediaFilesDao {

	public void add(MediaFiles entity);
	
	public List<MediaFiles> getMediaFileList();
	
	/**
	 *  分页
	 * @param entity
	 * @return
	 */
	public List<MediaFiles> getMediaListByPage(MediaFiles entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteByMediaId(String mediaId);
	/**
	 * 获取单条数据
	 * @param mediaId
	 * @return
	 */
	public MediaFiles getFileByMediaId(String mediaId);
	/**
	 * 条件查询
	 * @param mediaId
	 * @return
	 */
	public MediaFiles getFileBySou(MediaFiles entity);
}
