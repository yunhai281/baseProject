package com.boyuyun.base.org.entity;

import java.io.Serializable;
import java.util.Date;

import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;
/**
 * 年级
 * @author zhy
 */
public class SchoolGrade extends Page implements Serializable{
	private String id;
	private String name;
	private String shortName;
	private String schoolId;
	@ForViewUse
	private String schoolName;
	private Date createTime;
	private int sortNum;
	
	private String sysGradeId;
	@ForViewUse
	private String sysGradeName;
	@ForViewUse
	private String sysGradeShortName;
	@ForViewUse
	private Integer sysGradeStage;
	@ForViewUse
	private int classNum = 0;//学校数量，需要手动查询
	
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public String getSysGradeId() {
		return sysGradeId;
	}
	public void setSysGradeId(String sysGradeId) {
		this.sysGradeId = sysGradeId;
	}
	public String getSysGradeName() {
		return sysGradeName;
	}
	public void setSysGradeName(String sysGradeName) {
		this.sysGradeName = sysGradeName;
	}
	public String getSysGradeShortName() {
		return sysGradeShortName;
	}
	public void setSysGradeShortName(String sysGradeShortName) {
		this.sysGradeShortName = sysGradeShortName;
	}
	public Integer getSysGradeStage() {
		return sysGradeStage;
	}
	public void setSysGradeStage(Integer sysGradeStage) {
		this.sysGradeStage = sysGradeStage;
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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
	public TreeDTO getTreeDTO(){
		TreeDTO treeDTO = new TreeDTO();
		treeDTO.setId(id);
		treeDTO.setName(name);
		treeDTO.setType("grade");
		treeDTO.setIsParent(classNum>0?"true":"false");
		treeDTO.setIcon(ConstantUtil.TREE_ICON_SCHOOL);
		return treeDTO;
	}
}
