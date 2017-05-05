package com.boyuyun.base.user.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.Student;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.consts.UserType;

/**
 * @author LHui 2017-2-10 上午9:42:41
 */
public interface UserBiz {
	public User selectByUserName(String username, String password, UserType type)throws Exception;
	public void resetPassword(List<User> users) throws Exception;
	public User get(String id) throws Exception;
	public boolean update(User user) throws Exception;
	public boolean add(User user) throws Exception;
	public boolean delete(User user) throws Exception;
	public boolean deleteAll(List<User> users)throws SQLException;
	public int getMobileCount(String mobile)throws SQLException;
	public List<User> getListPagedAdmin(User user) throws SQLException ;
	public int getListPagedCountAdmin(User user)throws SQLException;
	public boolean updatePassword(String id,String pwd)throws Exception;
	public boolean validateUserName(String userName,String id) throws SQLException;
}
