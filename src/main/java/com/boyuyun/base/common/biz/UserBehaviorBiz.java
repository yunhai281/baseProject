package com.boyuyun.base.common.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.common.entity.UserBehavior;

public interface UserBehaviorBiz {
	List<UserBehavior> getList(UserBehavior behavior)  throws SQLException;
	int add(UserBehavior behavior) throws SQLException;
	UserBehavior getUserBehavior (UserBehavior behavior)throws SQLException;
	int update(UserBehavior behavior)throws SQLException;
}
