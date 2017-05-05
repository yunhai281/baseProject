package com.boyuyun.base.user.entity;

import java.util.List;

import com.boyuyun.common.annotation.ForViewUse;

/**
 * 教育局用户
 * @author zhy
 */
public class BureauUser extends User{
	private String governmentJuId;//所属局级机构,界面不显示
	private String governmentTingId;//所属厅级机构，界面不显示
	private String governmentId;//所属部门
	private int sortnum;
	@ForViewUse
	private String governmentName;//所属部门名称，冗余字段
	@ForViewUse
	private List<BureauUserPost> post;//岗位集合
	@ForViewUse
	private String postName;//岗位名称,冗余字段
	public String getGovernmentJuId() {
		return governmentJuId;
	}
	public void setGovernmentJuId(String governmentJuId) {
		this.governmentJuId = governmentJuId;
	}
	public String getGovernmentTingId() {
		return governmentTingId;
	}
	public void setGovernmentTingId(String governmentTingId) {
		this.governmentTingId = governmentTingId;
	}
	public String getGovernmentId() {
		return governmentId;
	}
	public void setGovernmentId(String governmentId) {
		this.governmentId = governmentId;
	}
	public String getGovernmentName() {
		return governmentName;
	}
	public void setGovernmentName(String governmentName) {
		this.governmentName = governmentName;
	}
	public List<BureauUserPost> getPost() {
		return post;
	}
	public void setPost(List<BureauUserPost> post) {
		StringBuilder names = new StringBuilder();
		for(BureauUserPost userPost:post){
			names.append(userPost.getPostName()+"  ");
		}
		this.postName = names.toString();
		this.post = post;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public int getSortnum() {
		return sortnum;
	}
	public void setSortnum(int sortnum) {
		this.sortnum = sortnum;
	}
	
}
