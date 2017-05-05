package com.boyuyun.base.sso.entity;

import java.io.Serializable;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
/**
 * 角色指定用户
 * @author shiss
 */
public class RoleUser extends Page implements Serializable{
	private String roleId;
	private String userId;
	private String userType;
	@ForViewUse
	private String roleName;
	@ForViewUse
	private String userName;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
