package com.boyuyun.base.sso.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.user.entity.User;

public interface RoleDao  {
	public List<Role> getAllRoleList() throws SQLException;
	public boolean insert(Role role)throws SQLException;
	public Role get(String id)throws SQLException;
	public boolean update(Role role)throws SQLException;
	public boolean delete(String id)throws SQLException;
	public boolean saveApp(String id,String roleId,String applicationId)throws  SQLException;
	public boolean deleteApp(String roleId)  throws SQLException;
	public List getAPP(String roleId)  throws SQLException;
	/**
	 * 当应用管理删除时,删除关联表
	 */
	public boolean deleteRoleApplication(Integer applicationId)throws SQLException;
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<User> getUserByRolePaged(String roleId,int page,int pageSize)throws SQLException;
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @return
	 */
	public int getUserByRoleCount(String roleId)throws SQLException;
	
	public List<Role> selectByName(String name) throws SQLException;
}