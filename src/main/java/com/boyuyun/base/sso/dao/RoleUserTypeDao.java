package com.boyuyun.base.sso.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.RoleUserType;

public interface RoleUserTypeDao {
	public int update(RoleUserType po) throws SQLException;

	public int insert(RoleUserType po) throws SQLException;

	public RoleUserType get(String roleId) throws SQLException;

	public int deleteByRoleId(String roleId) throws SQLException;

	public void addBatch(List<RoleUserType> userTypeList) throws SQLException;
	
	public List<RoleUserType> getList(String roleId) throws SQLException;
}
