package com.boyuyun.base.sso.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.RoleUserOrg;

public interface RoleUserOrgDao {
	public int deleteByRoleId(String roleId) throws SQLException;

	public void addBatch(List<RoleUserOrg> orgList) throws SQLException;

	public List<RoleUserOrg> getList(String roleId) throws SQLException;
}
