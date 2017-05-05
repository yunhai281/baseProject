package com.boyuyun.base.org.util;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LHui
 * 2017-3-3 下午1:55:22
 */
public class TreeDTO {

	private String id;
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	private String name;
	private List<TreeDTO> children = new ArrayList<TreeDTO>();
	private String type;
	private String icon;
	private String phone;
	private String checked = "false";
	private String iconSkin;
	private String open;
	private String nocheck;
	private String isParent = "false";
	private String levelType;
	
	private String parentId;
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
	public List<TreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDTO> children) {
		this.children = children;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getNocheck() {
		return nocheck;
	}
	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
}
