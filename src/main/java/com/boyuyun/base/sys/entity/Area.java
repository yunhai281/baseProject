package com.boyuyun.base.sys.entity;

import java.io.Serializable;

import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;

/**
 * 地域
 * @author 
 *
 */
public class Area extends Page implements Serializable{
    private String id; //使用行政区划编号作为id，例如北京为“110000”

    private boolean available;//是否可用
    private String code;//区号
    private Double dimension;//纬度
    private String firstLetter;//首字母
    private String levelType;//区域等级(1省/2市/3区县)
    private Double longitude;//经度

    private String mergerName;//完整名称，例如“海淀区”的完整名称是“中国.北京.北京市.海淀区”

    private String name;//名称,例如"海淀区"

    private String pinyin;//拼音

    private String shortName;//短名称，例如“海淀区”段名称为“海淀”

    private String zip;//邮编

    private String parentId;//父节点
    
    private String isParent = "true";//冗余字段，用于是否为父节点判断
    
//    private Area parent;//父节点
    
    @ForViewUse
    private String parentName;

    
    
    @Override
	public String toString() {
		return "Area [id=" + id + ", available=" + available + ", code=" + code
				+ ", dimension=" + dimension + ", firstLetter=" + firstLetter
				+ ", levelType=" + levelType + ", longitude=" + longitude
				+ ", mergerName=" + mergerName + ", name=" + name + ", pinyin="
				+ pinyin + ", shortName=" + shortName + ", zip=" + zip
				+ ", parentId=" + parentId + ", isParent=" + isParent
				+ ", parentName=" + parentName + "]";
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getId() {
        return id;
    }



	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Double getDimension() {
        return dimension;
    }

    public void setDimension(Double dimension) {
        this.dimension = dimension;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType == null ? null : levelType.trim();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName == null ? null : mergerName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

//	public Area getParent() {
//		return parent;
//	}
//
//	public void setParent(Area parent) {
//		this.parent = parent;
//	}
}