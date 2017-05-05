package com.boyuyun.base.org.entity;

import java.io.Serializable;
import java.util.Date;

import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;

public class School extends Page implements Serializable {
	private String id; // 主键ID
	private String address;// 地址
	private Date buildDate;// 建校日期
	private String code;// 编号,学校的组织代码
	private Date decorationDay;// 校庆日
	private String description;// 简介
	private String email;// 邮箱
	private String fax;// 传真
	private String legaNo;// 法人登记号
	private String motto;// 校训
	private String name;// 名称
	private String pinyin;// 拼音，全拼
	private String jianpin;// 简拼

	private int schoolBoardType;// 寄宿类型

	private int schoolStation; // 驻地类型

	private int schoolType; // 办学类型
	private int systemType;// 学校类型
	@ForViewUse
	private String systemTypeName;// 学校类型
	public String getHeadmaster() {
		return headmaster;
	}

	public void setHeadmaster(String headmaster) {
		this.headmaster = headmaster;
	}

	@ForViewUse
	private String schoolBoardTypeName;// 寄宿类型
	@ForViewUse
	private String schoolStationName; // 驻地类型
	@ForViewUse
	private String schoolTypeName; // 办学类型
	/*
	 * 
	 * 序列号，系统自动生成的序号，用于生成该学校下的 “管理员、教师、学生” 账号 1.serialNumber的生成： 由系统自增生成
	 * 2.使用(例如一个学校的serialNumber是(7667) 管理员账号：admin@7667（初始生成的账号） 教师账号： 1001@7667
	 * 学生账号： 900002@7667
	 */
	private String serialNumber;
	private String shortName;// 简称
	private String tel; // 电话
	private String zip;
	private String areaId;// 所属地域(主表area)
	@ForViewUse
	private String areaName;
	private String governmentId;// 作数教育机构(主表government)
	@ForViewUse
	private String governmentName;
	private Date updateTime;

	private String headmaster;// 校长(主表Teacher)
	private String adminMobile;// 管理员电话
	private String parentId;// 总校(主表 School) 目前只支持一层 只允许设置parent为null的学校为总校
	@ForViewUse
	private String parentName;
	@ForViewUse
	private String isParent = "false";

	@ForViewUse
	private int childNum = 0;
	@ForViewUse
	private int gradeNum;
	public int getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}

	public String getAddress() {
		return address;
	}

	public String getAdminMobile() {
		return adminMobile;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public Date getBuildDate() {
		return buildDate;
	}

	public int getChildNum() {
		return childNum;
	}

	public String getCode() {
		return code;
	}

	public Date getDecorationDay() {
		return decorationDay;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getFax() {
		return fax;
	}

	public String getGovernmentId() {
		return governmentId;
	}

	public String getGovernmentName() {
		return governmentName;
	}

	

	public String getId() {
		return id;
	}

	public String getIsParent() {
		return isParent;
	}

	public String getJianpin() {
		return jianpin;
	}

	public String getLegaNo() {
		return legaNo;
	}

	public String getMotto() {
		return motto;
	}

	public String getName() {
		return name;
	}

	public String getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public String getPinyin() {
		return pinyin;
	}

	public int getSchoolBoardType() {
		return schoolBoardType;
	}

	public String getSchoolBoardTypeName() {
		return schoolBoardTypeName;
	}

	public int getSchoolStation() {
		return schoolStation;
	}

	public String getSchoolStationName() {
		return schoolStationName;
	}

	public int getSchoolType() {
		return schoolType;
	}

	public String getSchoolTypeName() {
		return schoolTypeName;
	}
 
	public String getShortName() {
		return shortName;
	}

	public int getSystemType() {
		return systemType;
	}

	public String getSystemTypeName() {
		return systemTypeName;
	}

	public String getTel() {
		return tel;
	}

	public TreeDTO getTreeDTO() {
		TreeDTO dto = new TreeDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setType("school");
		dto.setIsParent(isParent);
		dto.setIcon(ConstantUtil.TREE_ICON_SCHOOL);
		return dto;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getZip() {
		return zip;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile == null ? null : adminMobile.trim();
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public void setDecorationDay(Date decorationDay) {
		this.decorationDay = decorationDay;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}

	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}


	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	public void setLegaNo(String legaNo) {
		this.legaNo = legaNo == null ? null : legaNo.trim();
	}

	public void setMotto(String motto) {
		this.motto = motto == null ? null : motto.trim();
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public void setSchoolBoardType(int schoolBoardType) {
		this.schoolBoardType = schoolBoardType;
	}

	public void setSchoolBoardTypeName(String schoolBoardTypeName) {
		this.schoolBoardTypeName = schoolBoardTypeName;
	}

	public void setSchoolStation(int schoolStation) {
		this.schoolStation = schoolStation;
	}

	public void setSchoolStationName(String schoolStationName) {
		this.schoolStationName = schoolStationName;
	}

	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}

	public void setSchoolTypeName(String schoolTypeName) {
		this.schoolTypeName = schoolTypeName;
	} 
	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public void setSystemType(int systemType) {
		this.systemType = systemType;
	}

	public void setSystemTypeName(String systemTypeName) {
		this.systemTypeName = systemTypeName;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setZip(String zip) {
		this.zip = zip == null ? null : zip.trim();
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}