package com.boyuyun.base.user.entity;

import java.util.Date;

import com.boyuyun.common.annotation.ForViewUse;
/**
 * 学生
 * @author zhy
 */
public class Student extends User {
	private int entryType;//入学方式
	@ForViewUse
	private String entryTypeName;
	private Date leavingDate;//离校日期（离校可能是毕业、肄业、开除、退学，但是不包括休学）
	private int status;//学习状态
	@ForViewUse
	private String statusName;
	private String studentNo;//学生编号,非空（该学生在本校的序号，长度为6位起，如100000{默认从100000起}）
	private int studyType;//就读方式
	@ForViewUse
	private String studyTypeName;
	private String schoolId;//所属学校
	private String belongClassId;//所属班级(外键)
	private String belongGradeId;//所属年级(外键)
	@ForViewUse
	private String schoolName;
	@ForViewUse
	private String belongClassName;//所属班级(外键)
	@ForViewUse
	private String belongGradeName;//所属年级(外键)
	@ForViewUse
	private String belongGradeShortName;//所属年级(外键)
	@ForViewUse
	private Integer belongGradeStage;//所属学段

	private double entryScore;//入学成绩(总分)
	public Integer getBelongGradeStage() {
		return belongGradeStage;
	}

	public void setBelongGradeStage(Integer belongGradeStage) {
		this.belongGradeStage = belongGradeStage;
	}

	private String xueJiNo;//学籍号（可以为空）
	private Date entryDate;//入学日期
	
	
	public int getEntryType() {
		return entryType;
	}

	public void setEntryType(int entryType) {
		this.entryType = entryType;
	}

	public String getEntryTypeName() {
		return entryTypeName;
	}

	public void setEntryTypeName(String entryTypeName) {
		this.entryTypeName = entryTypeName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public int getStudyType() {
		return studyType;
	}

	public void setStudyType(int studyType) {
		this.studyType = studyType;
	}

	public String getStudyTypeName() {
		return studyTypeName;
	}

	public void setStudyTypeName(String studyTypeName) {
		this.studyTypeName = studyTypeName;
	}

	
	public String getBelongClassName() {
		return belongClassName;
	}

	public void setBelongClassName(String belongClassName) {
		this.belongClassName = belongClassName;
	}

	public String getBelongGradeName() {
		return belongGradeName;
	}

	public void setBelongGradeName(String belongGradeName) {
		this.belongGradeName = belongGradeName;
	}
	

	

	public Date getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(Date leavingDate) {
		this.leavingDate = leavingDate;
	}

	

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	

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

	public String getBelongClassId() {
		return belongClassId;
	}

	public void setBelongClassId(String belongClassId) {
		this.belongClassId = belongClassId;
	}

	public String getBelongGradeId() {
		return belongGradeId;
	}

	public void setBelongGradeId(String belongGradeId) {
		this.belongGradeId = belongGradeId;
	}
	
	public double getEntryScore() {
		return entryScore;
	}

	public void setEntryScore(double entryScore) {
		this.entryScore = entryScore;
	}

	public String getXueJiNo() {
		return xueJiNo;
	}

	public void setXueJiNo(String xueJiNo) {
		this.xueJiNo = xueJiNo;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

}