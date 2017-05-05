package com.boyuyun.base.common.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.common.dao.UserBehaviorDao;
import com.boyuyun.base.common.entity.UserBehavior;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserBehaviorDaoImpl extends BaseDAO implements UserBehaviorDao{
	@Override
	public int add(UserBehavior behavior)throws SQLException {
		String sql = " insert into user_behavior(id,userId,behaviorType,behavior)values(?,?,?,?) ";
		Object [] param = new Object[]{
				behavior.getId(),
				behavior.getUserid(),
				behavior.getBehaviorType(),
				behavior.getBehavior()
		};
		int i=this.executeUpdate(sql, param);
		return i;
	}

	@Override
	public UserBehavior getUserBehavior(UserBehavior behavior) throws SQLException{
		List params =  new ArrayList();
		String sql = " select * from  user_behavior where 1=1  ";
		if(StringUtils.isNotEmpty(behavior.getId())){
			sql += " and id=? ";
			params.add(behavior.getId());
		}
		if(StringUtils.isNotEmpty(behavior.getUserid())){
			sql += " and userid=? ";
			params.add(behavior.getUserid());
		}
		if(StringUtils.isNotEmpty(behavior.getBehaviorType())){
			sql += " and behaviorType=? ";
			params.add(behavior.getBehaviorType());
		}
		ResultSetHandler handler = new BeanHandler(UserBehavior.class);
		UserBehavior g = (UserBehavior) this.executeQueryObject(sql, params.toArray(), handler);
		return g;
	}

	@Override
	public int update(UserBehavior behavior)throws SQLException {
		String sql = " update user_behavior set behavior=? where userid=? and behaviorType=?  ";
		Object [] param = new Object[]{
				behavior.getBehavior(),
				behavior.getUserid(),
				behavior.getBehaviorType(),
		};
		int i=this.executeUpdate(sql, param);
		return i;
	}

	@Override
	public List<UserBehavior> getList(UserBehavior behavior)
			throws SQLException {
		StringBuffer sql = new StringBuffer("select * from user_behavior where userid=? ");
		ResultSetHandler handler = new BeanListHandler(UserBehavior.class);
		return this.executeQuery(sql.toString(), new String[]{behavior.getUserid()},handler);
	}

}
