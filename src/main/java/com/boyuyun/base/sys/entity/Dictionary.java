package com.boyuyun.base.sys.entity;

import java.io.Serializable;

public class Dictionary implements Serializable {
	private int id;// 主键ID
	private String code;// 编码
	private String name;// 名称
	private String remark;// 描述信息
	private boolean editable;// 是否可编辑
	private boolean schooldiy;// 是否每个学校可自定义

	@Override
	public String toString() {
		return "Dictionary [id=" + id + ", code=" + code + ", name=" + name
				+ ", remark=" + remark + ", editable=" + editable
				+ ", schooldiy=" + schooldiy + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isSchooldiy() {
		return schooldiy;
	}

	public void setSchooldiy(boolean schooldiy) {
		this.schooldiy = schooldiy;
	}

}