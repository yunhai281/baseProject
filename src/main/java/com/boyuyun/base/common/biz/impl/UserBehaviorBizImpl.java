package com.boyuyun.base.common.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.common.biz.UserBehaviorBiz;
import com.boyuyun.base.common.dao.UserBehaviorDao;
import com.boyuyun.base.common.entity.UserBehavior;

@Service
public class UserBehaviorBizImpl implements UserBehaviorBiz{
	@Resource
	UserBehaviorDao behaviorDao;
	@Override
	public List<UserBehavior> getList(UserBehavior behavior)throws SQLException {
		return behaviorDao.getList(behavior);
	}

	@Override
	public int add(UserBehavior behavior) throws SQLException {
		return behaviorDao.add(behavior);
	}

	@Override
	public UserBehavior getUserBehavior(UserBehavior behavior) throws SQLException {
		return behaviorDao.getUserBehavior(behavior);
	}

	@Override
	public int update(UserBehavior behavior) throws SQLException {
		return behaviorDao.update(behavior);
	}
	
}
