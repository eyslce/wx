
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MediaFiles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MediaFilesDao {

	void add(MediaFiles entity);

	List<MediaFiles> getMediaFileList();

	List<MediaFiles> getMediaListByPage(MediaFiles entity);

	void deleteByMediaId(String mediaId);

	MediaFiles getFileByMediaId(String mediaId);

	MediaFiles getFileBySou(MediaFiles entity);
}
