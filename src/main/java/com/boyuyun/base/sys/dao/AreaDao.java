package com.boyuyun.base.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.Area;

public interface AreaDao {
	List<Area> selectByParent(String parentId)throws SQLException;
	Area getParentByPid(String parentId)throws SQLException;
	Area selectLinkByPrimaryKey(String id)throws SQLException;
    boolean insert(Area area)throws SQLException;
    boolean update(Area area)throws SQLException;
	boolean delete(Area area)throws SQLException;
	List<Area> getListNonePaged(Area area)throws SQLException;
	Area get(String id)throws SQLException;	
	public List<Area> selectByName(String name)throws SQLException;
}