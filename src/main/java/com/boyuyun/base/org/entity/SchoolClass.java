package com.boyuyun.base.org.entity;

import java.io.Serializable;
import java.util.Date;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
/**
 * 班级
 * @author zhy
 */
public class SchoolClass extends Page implements Serializable{
	private String id;
	private String name;
	private String shortName;
	private String schoolId;
	private String gradeId;
	private Date createTime;
	private int sortNum;
	private int status;
	private int beginYear;
	private Date statusTime;
	
	private String stageId;
	@ForViewUse
	private String gradeName;
	@ForViewUse
	private String schoolName;
	@ForViewUse
	private String statusName;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBeginYear() {
		return beginYear;
	}
	public void setBeginYear(int beginYear) {
		this.beginYear = beginYear;
	}
	public Date getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	
}
