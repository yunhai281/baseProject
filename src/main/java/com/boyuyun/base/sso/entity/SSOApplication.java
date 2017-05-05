package com.boyuyun.base.sso.entity;

import java.io.Serializable;
import java.util.List;

import com.dhcc.common.db.page.Page;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class SSOApplication extends Page implements Serializable{
    private Integer id;//主键

    private String expressionType = "regex";//表达类型

    private boolean allowedtoproxy = false;//是否允许作为代理

    private boolean anonymousaccess = false;//是否允许匿名访问

    private String description;//描述

    private boolean enabled;//是否启用

    private boolean ignoreattributes;//是否此工具设置的属性忽略

    private String name;//名称

    private String applicationType;//应用类型  
    
    private String serviceid;//应用URL

    private boolean ssoenabled = true;//是否参与SSO

    private String theme;//主题
    
    private int evaluation_order;//测评顺序
    
    private String manufacturer;//厂商
    private boolean wicketed;//是否打开新窗口
    private int usecount;//使用计数
    private String icon;
    private String url;
    
    private List attributes;
//    private int usernameAttr;//用户名属性(要不是int就是还是int)
//    @ForViewUse
//    private String usernameAttrName;//

	@Override
	public String toString() {
		return "SSOApplication [id=" + id + ", expressionType="
				+ expressionType + ", allowedtoproxy=" + allowedtoproxy
				+ ", anonymousaccess=" + anonymousaccess + ", description="
				+ description + ", enabled=" + enabled + ", ignoreattributes="
				+ ignoreattributes + ", name=" + name + ", applicationType="
				+ applicationType + ", serviceid=" + serviceid
				+ ", ssoenabled=" + ssoenabled + ", theme=" + theme
				+ ", evaluation_order=" + evaluation_order + ", manufacturer="
				+ manufacturer + ", wicketed=" + wicketed + ", usecount="
				+ usecount + ", icon=" + icon + ", url=" + url
				+ ", attributes=" + attributes + "]";
	}

	
    public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public List getAttributes() {
		return attributes;
	}


	public void setAttributes(List attributes) {
		this.attributes = attributes;
	}


	public Integer getId() {
		return id;
	}

	public String getExpressionType() {
		return expressionType;
	}
	public boolean isAllowedtoproxy() {
		return allowedtoproxy;
	}
	public boolean isAnonymousaccess() {
		return anonymousaccess;
	}
	public String getDescription() {
		return description;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public boolean isIgnoreattributes() {
		return ignoreattributes;
	}
	public String getName() {
		return name;
	}
	public String getServiceid() {
		return serviceid;
	}
	public boolean isSsoenabled() {
		return ssoenabled;
	}
	public String getTheme() {
		return theme;
	}
	public int getEvaluation_order() {
		return evaluation_order;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public boolean isWicketed() {
		return wicketed;
	}
	public int getUsecount() {
		return usecount;
	}
	public String getIcon() {
		return icon;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setExpressionType(String expressionType) {
		this.expressionType = expressionType;
	}
	public void setAllowedtoproxy(boolean allowedtoproxy) {
		this.allowedtoproxy = allowedtoproxy;
	}
	public void setAnonymousaccess(boolean anonymousaccess) {
		this.anonymousaccess = anonymousaccess;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setIgnoreattributes(boolean ignoreattributes) {
		this.ignoreattributes = ignoreattributes;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public void setSsoenabled(boolean ssoenabled) {
		this.ssoenabled = ssoenabled;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public void setEvaluation_order(int evaluation_order) {
		this.evaluation_order = evaluation_order;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setWicketed(boolean wicketed) {
		this.wicketed = wicketed;
	}
	public void setUsecount(int usecount) {
		this.usecount = usecount;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}