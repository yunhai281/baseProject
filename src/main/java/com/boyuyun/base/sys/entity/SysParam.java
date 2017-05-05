package com.boyuyun.base.sys.entity;

import java.io.Serializable;

import com.dhcc.common.db.page.Page;

/**
 * @author happyss
 * @fileName com.boyuyun.base.sys.entity.SysParam.java
 * @version 1.0
 * @createTime 2017-4-6 上午11:26:21
 * @description 类说明
 */
public class SysParam extends Page implements Serializable{
    private String paramKey; 
    private String paramValue;
    
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
