
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountMenu;

import java.util.List;


public interface AccountMenuDao {

	public AccountMenu getById(String id);

	public List<AccountMenu> listForPage(AccountMenu searchEntity);

	public List<AccountMenu> listParentMenu(AccountMenu entity);
	
	public List<AccountMenu> listWxMenus(AccountMenu entity);
	
	public void add(AccountMenu entity);

	public void update(AccountMenu entity);

	public void delete(AccountMenu entity);



}
