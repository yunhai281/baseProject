package com.boyuyun.base.sys.entity;

import java.io.Serializable;

import com.dhcc.common.db.page.Page;
/**
 * 系统年级
 * @author zhy
 */
public class Grade extends Page implements Serializable{
	private String id;
	private String name;
	private String shortName;
	private int stage;
	private int sortNum;
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
}
