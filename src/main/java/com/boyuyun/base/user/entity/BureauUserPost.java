package com.boyuyun.base.user.entity;

import java.io.Serializable;

import com.boyuyun.common.annotation.ForViewUse;

public class BureauUserPost implements Serializable{
	private String id;
	private String bureauUserId;
	private int post;
	@ForViewUse
	private String postName;
	private int sortNum;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBureauUserId() {
		return bureauUserId;
	}
	public void setBureauUserId(String bureauUserId) {
		this.bureauUserId = bureauUserId;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
	
	
}
