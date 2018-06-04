
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountMenuGroup;

import java.util.List;


public interface AccountMenuGroupDao {

	public AccountMenuGroup getById(String id);

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	public Integer getTotalItemsCount(AccountMenuGroup searchEntity);

	public List<AccountMenuGroup> getGroupListByPage(AccountMenuGroup searchEntity);

	public void add(AccountMenuGroup entity);

	public void update(AccountMenuGroup entity);
	
	public void updateMenuGroupDisable();
	
	public void updateMenuGroupEnable(String gid);
	
	public void deleteAllMenu(AccountMenuGroup entity);
	
	public void delete(AccountMenuGroup entity);

	/**
	 * 删除菜单组
	 * @param id
	 */
	public void deleteGroupById(long id);
	/**
	 * 删除菜单组下的菜单
	 * @param id
	 */
	public void deleteMenuByGId(long id);
}
