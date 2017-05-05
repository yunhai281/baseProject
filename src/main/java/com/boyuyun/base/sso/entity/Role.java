package com.boyuyun.base.sso.entity;

import java.io.Serializable;

import com.dhcc.common.db.page.Page;

public class Role extends Page implements Serializable{
    private String id;

    private String name;

    private String remark;
    
    private String userScopeType;//用户范围：type表示人员类型，person表示指定人员
    private String userType;//用户身份：all表示全部，self表示自定义
    private String goverType;//用户机构：all表示全部，government表示教育局，schoo表示学校

    

    @Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", remark=" + remark
				+ ", userScopeType=" + userScopeType + ", userType=" + userType
				+ ", goverType=" + goverType + "]";
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getUserScopeType() {
		return userScopeType;
	}

	public void setUserScopeType(String userScopeType) {
		this.userScopeType = userScopeType;
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
}