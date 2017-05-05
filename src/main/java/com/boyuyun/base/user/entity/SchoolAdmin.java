package com.boyuyun.base.user.entity;

import com.boyuyun.common.annotation.ForViewUse;

public class SchoolAdmin  extends User{
    //是否是初始账号（超级管理员），该账号不可删除，并且拥有该学校系统的最高管理权限
    private boolean superAdmin;
    private String schoolId;//所属学校(外键)
    @ForViewUse
    private String schoolName;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
}