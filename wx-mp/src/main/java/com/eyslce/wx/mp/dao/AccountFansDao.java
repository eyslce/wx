
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountFans;

import java.util.List;


public interface AccountFansDao {

	public AccountFans getById(String id);

	public AccountFans getByOpenId(String openId);
	
	public List<AccountFans> list(AccountFans searchEntity);

	public Integer getTotalItemsCount(AccountFans searchEntity);
	
	public List<AccountFans> getFansListByPage(AccountFans searchEntity);

	public AccountFans getLastOpenId();
	
	public void add(AccountFans entity);
	
	public void addList(List<AccountFans> list);

	public void update(AccountFans entity);

	public void delete(AccountFans entity);

	public void deleteByOpenId(String openId);

	/**
	 * 分页查询粉丝列表
	 * @param searchEntity
	 * @return
	 */
	public List<AccountFans> getAccountFansList(AccountFans searchEntity);
}
