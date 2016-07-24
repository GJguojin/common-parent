package com.gj.test.user.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gj.test.user.dao.UserDao;
import com.gj.test.user.beans.User;
import com.gj.test.user.service.IUserService;
import com.gj.test.user.service.impl.UserServiceImpl;
import com.gj.test.base.paginator.mybatis.PageList;
import com.gj.test.user.search.UserSearch;

@Service
public class UserServiceImpl implements IUserService {
	public static Log log = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;

	@Override
	public Integer saveUser(User user) throws Exception {
		return userDao.insert(user);
	}

	@Override
	public Integer editUser(User user) {
		return userDao.update(user);
	}

	@Override
	public Integer deleteUser(Long userId) throws Exception {
		return userDao.delete(userId);
	}

	@Override
	public User selectById(Long userId) {
		return userDao.selectById(userId);
	}

	@Override
	public PageList<User> selectBySearch(UserSearch userSearch) {
		return userDao.selectBySearch(userSearch);
	}
}