package com.gj.test.user.service;

import com.gj.test.base.paginator.mybatis.PageList;
import com.gj.test.user.beans.User;
import com.gj.test.user.search.UserSearch;

public interface IUserService{
	public Integer saveUser(User user) throws Exception;

	public Integer editUser(User user);

	public Integer deleteUser(Long userId) throws Exception;

	public User selectById(Long userId);

	public PageList<User> selectBySearch(UserSearch userSearch);
}
