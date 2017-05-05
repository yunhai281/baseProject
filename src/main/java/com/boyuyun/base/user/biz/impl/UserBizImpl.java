package com.boyuyun.base.user.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.user.biz.UserBiz;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.consts.UserType;
import com.dhcc.common.util.StringUtil;

/**
 * @author LHui
 * 2017-2-10 上午9:43:03
 */
@Service
public class UserBizImpl implements UserBiz{
	
	@Resource
	private UserDao userDao;

	@Override
	public User selectByUserName(String username,String password,UserType type) throws Exception{
		return this.userDao.selectByUserName(username,password,type);
	}

	@Transactional
	public void resetPassword(List<User> users) throws Exception {
		for (User user : users) {
			String encryptMd5 = StringUtil.encryptMd5(ConstantUtil.USER_DEFAULT_PASSWORD);
			user.setPwd(encryptMd5);
			this.update(user);
		}
		
	}
	
	@Override
	public boolean updatePassword(String id,String pwd) throws Exception {
		return userDao.updatePassword(id,pwd);
	}

	@Override
	public User get(String id) throws Exception {
		return userDao.get(id);
	}

	@Transactional
	public boolean update(User user) throws Exception {
		return userDao.update(user);
	}

	@Transactional
	public boolean add(User user) throws Exception {
		return userDao.insert(user);
	}

	@Transactional
	public boolean delete(User user) throws Exception {
		return userDao.delete(user);
	}

	@Override
	public int getMobileCount(String mobile) throws SQLException {
		return userDao.getMobileCount(mobile);
	}

	@Override
	public List<User> getListPagedAdmin(User user) throws SQLException {
		return userDao.getListPagedAdmin(user);
	}

	@Override
	public int getListPagedCountAdmin(User user) throws SQLException {
		return userDao.getListPagedCountAdmin(user);
	}

	@Override
	public boolean deleteAll(List<User> users) throws SQLException {
		return userDao.deleteAll(users);
	}

	@Override
	public boolean validateUserName(String userName,String id) throws SQLException {
		Integer val = userDao.validateUserName(userName, id); 
		return (val!=null && val>0)?false:true;
	}
}
