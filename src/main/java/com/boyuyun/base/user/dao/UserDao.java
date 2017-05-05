package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.consts.UserType;

public interface UserDao {
    User selectByUserName(String username,String password,UserType type) throws SQLException;
    public User get(String id)throws SQLException;
	public boolean update(User user)throws SQLException;
	public boolean insert(User user)throws SQLException;
	public boolean delete(User user)throws SQLException;
	public boolean deleteAll(List<User> users)throws SQLException;
	public int getMobileCount(String mobile)throws SQLException;
	public int getEmailCount(String email)throws SQLException;
	public List<User> getUserList(String schoolId) throws SQLException;
	public List<User> getListPagedAdmin(User user) throws SQLException ;
	public int getListPagedCountAdmin(User user)throws SQLException;
	boolean updatePassword(String id,String pwd) throws SQLException;
	public Integer validateUserName(String userName,String id) throws SQLException;
}