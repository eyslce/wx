
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.ImgResource;
import com.eyslce.wx.mp.query.ImgQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface ImgResourceDao {

	List<ImgResource> getImgListByPage(ImgQuery imgQuery);

	ImgResource getImgById(String id);

	void add(ImgResource img);

	void deleteByMediaId(String otherId);
	
}
