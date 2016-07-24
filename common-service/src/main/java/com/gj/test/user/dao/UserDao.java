package com.gj.test.user.dao;

import com.gj.test.base.dao.BaseDao;
import com.gj.test.base.paginator.mybatis.PageList;
import com.gj.test.user.beans.User;
import com.gj.test.user.search.UserSearch;


public interface UserDao extends BaseDao<User>{
	
	public PageList<User> selectBySearch( UserSearch userSearch );

}
