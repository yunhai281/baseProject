package com.boyuyun.base.sys.biz;

import java.util.List;

import com.boyuyun.base.sys.entity.Menu;

public interface MenuBiz  {
	/**
	 * 查询所有顶级菜单，并且自动设置好子菜单
	 * @return
	 */
	public List<Menu> getTopMenuListWithChild()throws Exception; 
	public List<Menu> getMenuListByParentId(String parentId)throws Exception; 
	public boolean add(Menu menu)throws Exception;
	public boolean update(Menu menu)throws Exception;
	public boolean delete(Menu menu)throws Exception;
	public Menu get(String menuid)throws Exception;
}
