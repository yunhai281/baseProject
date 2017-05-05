package com.boyuyun.base.sys.entity;

import java.io.Serializable;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;

public class DictionaryItem extends Page implements Serializable{
	private int id;//主键
	
    private int dictionaryId;//外键(主表dictionary)
    @ForViewUse
    private String dictionaryName;
    
    private String name;

    private String value;

    private String remark;//描述信息

    private int num;//数字

    private int sortNum;//排序字段
    private String schoolId;//外键(主表School)
    @ForViewUse
    private String schoolName;
    




	@Override
	public String toString() {
		return "DictionaryItem [id=" + id + ", dictionaryId=" + dictionaryId
				+ ", dictionaryName=" + dictionaryName + ", name=" + name
				+ ", value=" + value + ", remark=" + remark + ", num=" + num
				+ ", sortNum=" + sortNum + ", schoolId=" + schoolId
				+ ", schoolName=" + schoolName + "]";
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}



	public int getDictionaryId() {
		return dictionaryId;
	}



	public void setDictionaryId(int dictionaryId) {
		this.dictionaryId = dictionaryId;
	}



	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
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
    
}