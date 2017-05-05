package com.boyuyun.base.sys.dao;

import java.util.List;

import com.boyuyun.base.sys.entity.Menu;

public interface MenuDao {
	public List<Menu> getMenuListByParentId(String parentId) throws Exception;
	public boolean insert(Menu menu) throws Exception;
	public boolean update(Menu menu) throws Exception;
	public boolean delete(Menu menu) throws Exception;
	public List<Menu> getAllAvailableMenu() throws Exception;
	public Menu get(String menuid) throws Exception;
}