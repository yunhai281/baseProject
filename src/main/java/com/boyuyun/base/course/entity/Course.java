package com.boyuyun.base.course.entity;

import java.io.Serializable;

import com.boyuyun.base.util.SortablePage;
import com.boyuyun.common.annotation.ForViewUse;

public class Course extends SortablePage implements Serializable{
	private String id;// 主键ID
	private String cover;// 课程封面图片路径
	private boolean generalCourse;// 是否是通用课程。如果是通用课程
	private int hours;// 每学期的总课时
	private boolean mainCourse;// 是否是主课
	private String name;// 名称
	private int sortNum;// 排序
	private int type;// 课程类型，国家课程或者
	private int subjectField;//学科领域
	private String subjectCode;//学科代码
//	private School school;	
	@ForViewUse
	private String schoolName;// 该课程归属学校，默认为null，该值在校本课程管理功能中更改
	@ForViewUse
	private String subjectFieldName;//学科领域名称
	private String schoolId;
	private String wdCode;// 对应伟东云的课程的编码
	@ForViewUse
	private String typeName;
	
	
	
	
	@Override
	public String toString() {
		return "Course [id=" + id + ", cover=" + cover + ", generalCourse="
				+ generalCourse + ", hours=" + hours + ", mainCourse="
				+ mainCourse + ", name=" + name + ", sortNum=" + sortNum
				+ ", type=" + type + ", subjectField=" + subjectField
				+ ", subjectCode=" + subjectCode + ", schoolName=" + schoolName
				+ ", subjectFieldName=" + subjectFieldName + ", schoolId="
				+ schoolId + ", wdCode=" + wdCode + ", typeName=" + typeName
				+ "]";
	}

	public int getSubjectField() {
		return subjectField;
	}

	public void setSubjectField(int subjectField) {
		this.subjectField = subjectField;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectFieldName() {
		return subjectFieldName;
	}

	public void setSubjectFieldName(String subjectFieldName) {
		this.subjectFieldName = subjectFieldName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover == null ? null : cover.trim();
	}

	public String getName() {
		return name;
	}

	public boolean isGeneralCourse() {
		return generalCourse;
	}

	public void setGeneralCourse(boolean generalCourse) {
		this.generalCourse = generalCourse;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public boolean isMainCourse() {
		return mainCourse;
	}

	public void setMainCourse(boolean mainCourse) {
		this.mainCourse = mainCourse;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}


	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getWdCode() {
		return wdCode;
	}

	public void setWdCode(String wdCode) {
		this.wdCode = wdCode == null ? null : wdCode.trim();
	}
}