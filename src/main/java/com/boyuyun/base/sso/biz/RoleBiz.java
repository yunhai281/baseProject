package com.boyuyun.base.sso.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.user.entity.User;

public interface RoleBiz{
	public List<Role> getAllRoleList() throws SQLException;
	public boolean add(Role role)throws SQLException;
	public Role get(String id)throws SQLException;
	public boolean update(Role role)throws SQLException;
	public boolean delete(String id)throws SQLException;
	
	public boolean saveApp(String id,String roleId,String applicationId)throws  SQLException;
	
	public List getAPP(String roleId)  throws SQLException;
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<User> getUserByRolePaged(Role role)throws SQLException;
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @return
	 */
	public int getUserByRoleCount(String roleId)throws SQLException;	
	
	public List searchByName(String name)  throws SQLException;
}
