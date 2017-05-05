package com.boyuyun.base.sys.dao.jdbc;

import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.MenuDao;
import com.boyuyun.base.sys.entity.Menu;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuDaoImpl extends BaseDAO implements MenuDao {
	@Override
	public List<Menu> getMenuListByParentId(String parentId)throws Exception {
		String sql = "select * from sys_menu where parentId=? ";
		sql+=" order by sortNum";
		ResultSetHandler handler = new BeanListHandler(Menu.class);
		return this.executeQuery(sql,new String[]{parentId},handler);
	}

	@Override
	public boolean insert(Menu menu) throws Exception{
		String sql = "insert into SYS_MENU (ID, AVAILABLE, CSS,"
				+ " LEVELTYPE, NAME, PICPATH, SORTNUM, URL, PARENTID)values(?,?,?,?,?,?,?,?,?)";
		Object [] param = new Object[]{
				menu.getId(),
				menu.isAvailable(),
				menu.getCss(),
				menu.getLevelType(),
				menu.getName(),
				menu.getPicPath(),
				menu.getSortNum(),
				menu.getUrl(),
				menu.getParentId()
		};
		int i  = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(Menu menu)throws Exception {
		String sql = "update SYS_MENU set AVAILABLE = ?,CSS = ?,LEVELTYPE = ?,"
				+ " NAME = ?,PICPATH = ?,SORTNUM = ?,URL = ?,PARENTID =? where ID = ?";
		Object [] param = new Object[]{
				menu.isAvailable(),
				menu.getCss(),
				menu.getLevelType(),
				menu.getName(),
				menu.getPicPath(),
				menu.getSortNum(),
				menu.getUrl(),
				menu.getParentId(),
				menu.getId()
		};
		int i  = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(Menu menu)throws Exception {
		if(menu==null)return false;
		String sql = "delete from sys_menu where id=?";
		int i = this.executeUpdate(sql, new String[]{menu.getId()});
		return i>0;
	}

	@Override
	public List<Menu> getAllAvailableMenu()throws Exception {
		String sql = "select * from sys_menu where available=1 ";//暂时不加查询条件
		sql+=" order by sortNum";
		ResultSetHandler handler = new BeanListHandler(Menu.class);
		return this.executeQuery(sql,handler);
	}

	@Override
	public Menu get(String menuid)throws Exception {
		String sql = "select * from sys_menu where id=?";
		ResultSetHandler handler = new BeanHandler(Menu.class);
		return (Menu) this.executeQueryObject(sql,new String[]{menuid},handler);
	}
	

}
