package com.boyuyun.base.sso.entity;

import java.io.Serializable;
import java.util.List;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
/**
 * 年级
 * @author zhy
 */
public class RoleUserType extends Page implements Serializable{
	private String roleId;
	private String userType;
	private String goverType;
	@ForViewUse
	private String userTypeName;
	@ForViewUse
	private List<RoleUser> userList;
	@ForViewUse
	private List<RoleUserOrg> orgList;
	@ForViewUse
	private String addOrUpdate = "add";
	
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
	public String getGoverType() {
		return goverType;
	}
	public void setGoverType(String goverType) {
		this.goverType = goverType;
	}
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public List<RoleUser> getUserList() {
		return userList;
	}
	public void setUserList(List<RoleUser> userList) {
		this.userList = userList;
	}
	public String getAddOrUpdate() {
		return addOrUpdate;
	}
	public void setAddOrUpdate(String addOrUpdate) {
		this.addOrUpdate = addOrUpdate;
	}
	public List<RoleUserOrg> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<RoleUserOrg> orgList) {
		this.orgList = orgList;
	}
}
