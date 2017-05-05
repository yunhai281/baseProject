package com.boyuyun.base.sys.entity;

import java.io.Serializable;
import java.util.Date;

import com.dhcc.common.db.page.Page;

public class OperateLog extends Page implements Serializable{
	private String id;
	private String moduleName;//操作模块名60
	private Date operateTime;//操作时间
	private String operateType;//操作类型20
	private String operateUserId;
	private String operateUserName;//操作人60
	private String operateResult;//操作结果1000
	private String operateDataId;//操作记录ID(具体记录)
	private String remark;//备注
	private String ip;//ip地址
	private String operate;//查询操作框(包括各种操作)
	private String boperateTime;//查询操作开始时间
	private String eoperateTime;//查询操作结束时间
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	} 
	public String getBoperateTime() {
		return boperateTime;
	}
	public void setBoperateTime(String boperateTime) {
		this.boperateTime = boperateTime;
	}
	public String getEoperateTime() {
		return eoperateTime;
	}
	public void setEoperateTime(String eoperateTime) {
		this.eoperateTime = eoperateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperateDataId() {
		return operateDataId;
	}
	public void setOperateDataId(String operateDataId) {
		this.operateDataId = operateDataId;
	}
	public String getOperateUserId() {
		return operateUserId;
	}
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
	public String getOperateUserName() {
		return operateUserName;
	}
	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
}
