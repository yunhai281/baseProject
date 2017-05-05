package com.boyuyun.base.sso.entity;

import java.io.Serializable;
import java.util.Date;

import com.dhcc.common.db.page.Page;

public class SSOLog extends Page implements Serializable{
	private String id;
	private String userName;
	private Date loginTime;
	private String loginIp;
	private int loginResult;
	//查询开始时间
	private String bloginTime;
	//查询结束时间
	private String eloginTime;
	public static final int RESULT_SUCCESS = 1;//登录成功
	public static final int RESULT_WRONGPASSS = -1;//用户名或密码错误
	public static final int RESULT_NOTALLOWED = -2;//没有权限
	public static final int RESULT_DISABLED = -3;//用户被禁用
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public int getLoginResult() {
		return loginResult;
	}
	public void setLoginResult(int loginResult) {
		this.loginResult = loginResult;
	}
	public String getBloginTime() {
		return bloginTime;
	}
	public void setBloginTime(String bloginTime) {
		this.bloginTime = bloginTime;
	}
	public String getEloginTime() {
		return eloginTime;
	}
	public void setEloginTime(String eloginTime) {
		this.eloginTime = eloginTime;
	}
	
}
