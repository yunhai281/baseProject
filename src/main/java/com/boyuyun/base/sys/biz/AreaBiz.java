package com.boyuyun.base.sys.biz;

import java.util.List;

import com.boyuyun.base.sys.entity.Area;

public interface AreaBiz {
	
	public List<Area> getListByParent(String parentId)throws Exception;
	
	public Area getParent(String parentId)throws Exception;
	
	public Area selectLinkByPrimaryKey(String id)throws Exception;
	public boolean add(Area area)throws Exception;
	public boolean update(Area area)throws Exception;
	public boolean delete(Area area)throws Exception;
	public List<Area> getListNonePaged(Area area)throws Exception;
	public Area get(String id)throws Exception;
	
}
