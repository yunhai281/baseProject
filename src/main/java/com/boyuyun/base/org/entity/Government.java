package com.boyuyun.base.org.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.boyuyun.base.org.util.TreeDTO;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.common.annotation.ForViewUse;
import com.dhcc.common.db.page.Page;

/**
 * 管理机构
 * @author 
 */
public class Government extends Page implements Serializable{
    private String id;//主键ID
    private String code;//机构号
    public static final String GOVERNMENT_LEVEL_BU = "1";
    public static final String GOVERNMENT_LEVEL_TING = "2";
    public static final String GOVERNMENT_LEVEL_JU = "3";
    public static final String GOVERNMENT_LEVEL_KESHI = "4";
    private String levelType;//等级(1部 2厅 3局)，在局端平台中如果是科室，则使用4（char levelTemp = '4'） // 科室已经独立为其他的实体BureauDept
    private String name;//名称
    private String shortName;//简称
    private String areaId;//所属地域(主表area)
    @ForViewUse
    private String areaName;//所属地域(主表area)
    private String parentId;//上级单位(主表government)
    @ForViewUse
    private int childNum =0;
    public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@ForViewUse
    private String parentName;//树详情冗余字段，用于上级机构的显示，否则会出现死循环
    @ForViewUse
    private String isParent = "false";//是否有子节点
    @ForViewUse
    private List<Government> children = new ArrayList<Government>();
    
    public List<Government> getChildren() {
		return children;
	}

	public void setChildren(List<Government> children) {
		this.children = children;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

    private int seq;//用于排序
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType == null ? null : levelType.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public int getChildNum() {
		return childNum;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public TreeDTO getTreeDTO(){
		TreeDTO dto = new TreeDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setType("government");
		dto.setIsParent(isParent);
		dto.setLevelType(levelType);
		if(GOVERNMENT_LEVEL_BU.equals(levelType)){
			dto.setIcon(ConstantUtil.TREE_ICON_GOVERNMENT_BU);
		}else if(GOVERNMENT_LEVEL_TING.equals(levelType)){
			dto.setIcon(ConstantUtil.TREE_ICON_GOVERNMENT_TING);
		}else if(GOVERNMENT_LEVEL_JU.equals(levelType)){
			dto.setIcon(ConstantUtil.TREE_ICON_GOVERNMENT_JU);
		}else if(GOVERNMENT_LEVEL_KESHI.equals(levelType)){
			dto.setIcon(ConstantUtil.TREE_ICON_GOVERNMENT_KESHI);
		}
		return dto;
	}
}