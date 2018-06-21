
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountFans;
import com.eyslce.wx.mp.query.FansQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountFansDao {

	AccountFans getById(String id);

	AccountFans getByOpenId(String openId);

	List<AccountFans> list(AccountFans searchEntity);

	Integer getTotalItemsCount(AccountFans searchEntity);
	
	List<AccountFans> getFansListByPage(FansQuery query);

	AccountFans getLastOpenId();
	
	void add(AccountFans entity);
	
	void addList(List<AccountFans> list);

	void update(AccountFans entity);

	void delete(AccountFans entity);

	void deleteByOpenId(String openId);


	List<AccountFans> getAccountFansList(AccountFans searchEntity);
}
