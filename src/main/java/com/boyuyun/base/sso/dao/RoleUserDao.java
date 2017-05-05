package com.boyuyun.base.sso.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleUser;

public interface RoleUserDao {
	
	int deleteByRoleId(String roleId) throws SQLException;

	void addBatch(List<RoleUser> userList) throws SQLException;

	List<RoleUser> getList(String roleId) throws SQLException;

	List<Role> getRoleListByUserId(String userId) throws SQLException;

	int deleteByRoleId(String roleId, String userType) throws SQLException;

}
