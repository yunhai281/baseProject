package com.boyuyun.base.sso.entity;

import java.io.Serializable;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
/**
 * 年级
 * @author zhy
 */
public class RoleUserOrg extends Page implements Serializable{
	private String roleId;
	private String governmentId;
	private String governmentType;
	@ForViewUse
	private String roleName;
	@ForViewUse
	private String governmentName;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getGovernmentId() {
		return governmentId;
	}
	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}
	public String getGovernmentType() {
		return governmentType;
	}
	public void setGovernmentType(String governmentType) {
		this.governmentType = governmentType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getGovernmentName() {
		return governmentName;
	}
	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}
	
}
