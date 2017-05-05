package com.boyuyun.base.common.entity;

import java.io.Serializable;

import com.dhcc.common.db.page.Page;


/**
 * 用户操作习惯
 * @author zhy
 */
public class UserBehavior extends Page implements Serializable{
	private String id;
	private String userid;
	private String behaviorType;
	private String behavior;
	
	public static final String USER_BEHAVIOR_PAGE_ROWS = "default_page_rows";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBehaviorType() {
		return behaviorType;
	}
	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}
	public String getBehavior() {
		return behavior;
	}
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
}
