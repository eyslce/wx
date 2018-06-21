
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.MsgArticle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MsgArticleDao {

	List<MsgArticle> getByNewsId(int id);
	
	MsgArticle getById(int id);
	
	void add(MsgArticle article);
	
	void insertByBatch(List<MsgArticle> articles);
	
	void update(MsgArticle article);
	
	void delete(int id);
	
	void deleteByBatch(int id);
}
