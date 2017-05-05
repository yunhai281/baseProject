package com.boyuyun.base.user.entity;

import java.util.Date;
import java.util.List;

import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.common.annotation.ForViewUse;

public class Teacher extends User {
	private String dgreeNo;// 学位证号
	private String educationNo;// 学历证号
	private Date graduateDate;// 毕业时间
	private String graduateSchool;// 毕业院校
	private boolean hasCompile; // 是否有教师编制
	private boolean hasTeacherCertificate;// 是否有教师资格证
	private Date hireDate;// 入职时间（加入本校时间）
	private int compileType;// 编制类型
	private int dgree;// 学位
	private int education;// 学历
	private int maritalStatus;// 婚姻状况
	private int positionalTitle;// 职称
	private int postType;// 岗位类型
	private int status;// 任职状态
	private int skeletonType;// 骨干教师类型
	private int workStatus;// 在职状态
	private int politics;// 政治面貌
	@ForViewUse
	private String compileTypeName;// 编制类型
	@ForViewUse
	private String dgreeName;// 学位
	@ForViewUse
	private String educationName;// 学历
	@ForViewUse
	private String maritalStatusName;// 婚姻状况
	@ForViewUse
	private String positionalTitleName;// 职称
	@ForViewUse
	private String postTypeName;// 岗位类型
	@ForViewUse
	private String statusName;// 任职状态
	@ForViewUse
	private String skeletonTypeName;// 骨干教师类型
	@ForViewUse
	private String workStatusName;// 在职状态
	@ForViewUse
	private String politicsName;// 政治面貌

	private String profession;// 专业

	private Date startWorkDate;// 参加工作时间

	private String teacherCertificateNo;// 教师资格证号

	private String teacherNo;// 教师编号（该学生在本校的序号，长度为4位起，如1000{默认从1000起}）

	private String schoolId;// 所属学校

	@ForViewUse
	private String schoolName;

	private String archiveLocationId;// 档案所在地

	@ForViewUse
	private String archiveLocationName;// 档案所在地

	private int deptSortNum;// 该教师在所属部门中的排序字段
	
	private List<TeacherCourse> courses;//所教科目

	public String getArchiveLocationId() {
		return archiveLocationId;
	}

	public String getArchiveLocationName() {
		return archiveLocationName;
	}

	public int getCompileType() {
		return compileType;
	}

	public String getCompileTypeName() {
		return compileTypeName;
	}

	public int getDeptSortNum() {
		return deptSortNum;
	}

	public int getDgree() {
		return dgree;
	}

	public String getDgreeName() {
		return dgreeName;
	}

	public String getDgreeNo() {
		return dgreeNo;
	}

	public int getEducation() {
		return education;
	}

	public String getEducationName() {
		return educationName;
	}

	public String getEducationNo() {
		return educationNo;
	}

	public Date getGraduateDate() {
		return graduateDate;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public Date getHireDate() {
		return hireDate;
	}
	public int getMaritalStatus() {
		return maritalStatus;
	}

	public String getMaritalStatusName() {
		return maritalStatusName;
	}
	public int getPositionalTitle() {
		return positionalTitle;
	}

	public String getPositionalTitleName() {
		return positionalTitleName;
	}

	public int getPostType() {
		return postType;
	}

	public String getPostTypeName() {
		return postTypeName;
	}

	public String getProfession() {
		return profession;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public int getSkeletonType() {
		return skeletonType;
	}

	public String getSkeletonTypeName() {
		return skeletonTypeName;
	}

	public Date getStartWorkDate() {
		return startWorkDate;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getTeacherCertificateNo() {
		return teacherCertificateNo;
	}

	public String getTeacherNo() {
		return teacherNo;
	}

	public int getWorkStatus() {
		return workStatus;
	}

	public String getWorkStatusName() {
		return workStatusName;
	}

	public boolean isHasCompile() {
		return hasCompile;
	}

	public boolean isHasTeacherCertificate() {
		return hasTeacherCertificate;
	}

	public void setArchiveLocationId(String archiveLocationId) {
		this.archiveLocationId = archiveLocationId;
	}

	public void setArchiveLocationName(String archiveLocationName) {
		this.archiveLocationName = archiveLocationName;
	}

	public void setCompileType(int compileType) {
		this.compileType = compileType;
	}

	public void setCompileTypeName(String compileTypeName) {
		this.compileTypeName = compileTypeName;
	}

	public void setDeptSortNum(int deptSortNum) {
		this.deptSortNum = deptSortNum;
	}

	public void setDgree(int dgree) {
		this.dgree = dgree;
	}

	public void setDgreeName(String dgreeName) {
		this.dgreeName = dgreeName;
	}

	public void setDgreeNo(String dgreeNo) {
		this.dgreeNo = dgreeNo;
	}

	public void setEducation(int education) {
		this.education = education;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public void setEducationNo(String educationNo) {
		this.educationNo = educationNo;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public void setHasCompile(boolean hasCompile) {
		this.hasCompile = hasCompile;
	}

	public void setHasTeacherCertificate(boolean hasTeacherCertificate) {
		this.hasTeacherCertificate = hasTeacherCertificate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setMaritalStatusName(String maritalStatusName) {
		this.maritalStatusName = maritalStatusName;
	}

	public void setPositionalTitle(int positionalTitle) {
		this.positionalTitle = positionalTitle;
	}

	public void setPositionalTitleName(String positionalTitleName) {
		this.positionalTitleName = positionalTitleName;
	}

	public void setPostType(int postType) {
		this.postType = postType;
	}

	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setSkeletonType(int skeletonType) {
		this.skeletonType = skeletonType;
	}

	public void setSkeletonTypeName(String skeletonTypeName) {
		this.skeletonTypeName = skeletonTypeName;
	}

	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setTeacherCertificateNo(String teacherCertificateNo) {
		this.teacherCertificateNo = teacherCertificateNo;
	}

	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}

	public void setWorkStatus(int workStatus) {
		this.workStatus = workStatus;
	}

	public void setWorkStatusName(String workStatusName) {
		this.workStatusName = workStatusName;
	}

	public int getPolitics() {
		return politics;
	}

	public void setPolitics(int politics) {
		this.politics = politics;
	}

	public String getPoliticsName() {
		return politicsName;
	}

	public void setPoliticsName(String politicsName) {
		this.politicsName = politicsName;
	}

	public List<TeacherCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<TeacherCourse> courses) {
		this.courses = courses;
	}


	
	
}