
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountDao {

	Account getById(Long id);

	Account getByAccount(String account);

	Account getByAppId(String appId);

	Account getSingleAccount();

	List<Account> listForPage(Account searchEntity);

	void add(Account entity);

	void update(Account entity);

	void delete(Account entity);

}
