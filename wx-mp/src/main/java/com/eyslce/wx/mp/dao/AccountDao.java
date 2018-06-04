
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.Account;

import java.util.List;


public interface AccountDao {

	public Account getById(Long id);
	
	public Account getByAccount(String account);
	
	public Account getSingleAccount();

	public List<Account> listForPage(Account searchEntity);

	public void add(Account entity);

	public void update(Account entity);

	public void delete(Account entity);



}
