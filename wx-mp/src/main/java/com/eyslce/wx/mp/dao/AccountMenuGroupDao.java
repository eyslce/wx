
package com.eyslce.wx.mp.dao;

import com.eyslce.wx.mp.domain.AccountMenuGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountMenuGroupDao {

	AccountMenuGroup getById(String id);

	List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	Integer getTotalItemsCount(AccountMenuGroup searchEntity);

	List<AccountMenuGroup> getGroupListByPage(AccountMenuGroup searchEntity);

	void add(AccountMenuGroup entity);

	void update(AccountMenuGroup entity);

	void updateMenuGroupDisable();

	void updateMenuGroupEnable(String gid);

	void deleteAllMenu(AccountMenuGroup entity);

	void delete(AccountMenuGroup entity);

	void deleteGroupById(long id);

	void deleteMenuByGId(long id);
}
