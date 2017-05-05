package com.boyuyun.base.sso.entity;

import java.io.Serializable;
import java.util.List;

import com.dhcc.common.db.page.Page;

public class RoleScope extends Page implements Serializable{
	private String addOrUpdate;
	private List<RoleUser> userList;
	private List<RoleUserOrg> orgList;
	private String userType;
	private String goverType;
	private String roleId;
	private String userScopeType;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAddOrUpdate() {
		return addOrUpdate;
	}
	public void setAddOrUpdate(String addOrUpdate) {
		this.addOrUpdate = addOrUpdate;
	}
	public List<RoleUser> getUserList() {
		return userList;
	}
	public void setUserList(List<RoleUser> userList) {
		this.userList = userList;
	}
	public List<RoleUserOrg> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<RoleUserOrg> orgList) {
		this.orgList = orgList;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getGoverType() {
		return goverType;
	}
	public void setGoverType(String goverType) {
		this.goverType = goverType;
	}
	public String getUserScopeType() {
		return userScopeType;
	}
	public void setUserScopeType(String userScopeType) {
		this.userScopeType = userScopeType;
	}

}
