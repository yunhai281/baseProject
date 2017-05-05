package com.boyuyun.base.user.entity;

import java.util.List;

import com.boyuyun.common.annotation.ForViewUse;

public class Parent extends User{
	private String work;//工作单位
	private int education;//学历，字典
	private List<StudentParentRelation> students;//冗余字段，学生集合
    @ForViewUse
	private String educationName;//学历中文名
	@ForViewUse
	private String schoolId;//冗余字段，学生所在学校
	@ForViewUse
	private String childName;//冗余字段，孩子姓名
	
    
    
	@Override
	public String toString() {
		return "Parent [work=" + work + ", education=" + education
				+ ", students=" + students + ", educationName=" + educationName
				+ "]";
	}
	public String getEducationName() {
		return educationName;
	}
	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public int getEducation() {
		return education;
	}
	public void setEducation(int education) {
		this.education = education;
	}
	public List<StudentParentRelation> getStudents() {
		return students;
	}
	public void setStudents(List<StudentParentRelation> students) {
		this.students = students;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
}
