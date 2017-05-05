package com.boyuyun.base.sso.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleScope;

public interface RoleUserTypeBiz {

	public boolean save(RoleScope roleScope) throws SQLException;

	public RoleScope getRoleScope(String id) throws SQLException;

	/**
	 * @author happyss
	 * @creteTime 2017-3-31 下午4:12:22
	 * @description 根据用户id、用户类型列表，获取用户角色列表
	 * @param userId
	 * @param userTypes
	 * @return
	 * @throws SQLException
	 */
	public List<Role> getUserRoles(String userId) throws SQLException;

}
