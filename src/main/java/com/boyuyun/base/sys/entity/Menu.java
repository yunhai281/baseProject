package com.boyuyun.base.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台菜单
 */
public class Menu implements Serializable,Comparable<Menu>{
    private String id;//主键ID
    
    private boolean available;//是否可用

    private String css;//样式
    private int levelType;//菜单级别(数字格式，如1、2、3、4、5)

    private String name;//名称

    private String picPath;//图片路径
    
	private int sortNum;//排序

    private String url;//路径
    private String parentId;
    private Menu parent;//上级菜单(主表Menu自身)
    public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private List<Menu> child = new ArrayList<Menu>();//子菜单
    public List<Menu> getChild() {
		return child;
	}
	public void setChild(List<Menu> child) {
		this.child = child;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css == null ? null : css.trim();
    }

    public int getLevelType() {
		return levelType;
	}
	public void setLevelType(int levelType) {
		this.levelType = levelType;
	}
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	@Override
	public int compareTo(Menu o) {
		return sortNum-o.sortNum;
	}

    
}