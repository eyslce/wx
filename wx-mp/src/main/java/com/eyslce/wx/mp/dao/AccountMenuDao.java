
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountMenuDao {

	AccountMenu getById(String id);

	List<AccountMenu> listForPage(AccountMenu searchEntity);

	List<AccountMenu> listParentMenu(AccountMenu entity);

	List<AccountMenu> listWxMenus(AccountMenu entity);

	void add(AccountMenu entity);

	void update(AccountMenu entity);

	void delete(AccountMenu entity);



}
