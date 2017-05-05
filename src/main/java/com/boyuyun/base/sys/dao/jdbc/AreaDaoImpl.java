package com.boyuyun.base.sys.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.AreaDao;
import com.boyuyun.base.sys.entity.Area;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AreaDaoImpl extends BaseDAO implements AreaDao {

	public List<Area> selectByParent(String parentId)throws SQLException {
		String sql = "select ID, AVAILABLE, CODE, DIMENSION, FIRSTLETTER, LEVELTYPE, LONGITUDE, MERGERNAME, NAME, PINYIN, SHORTNAME, ZIP, PARENTID,null as parentname from SYS_AREA where PARENTID = ?";
		ResultSetHandler handler = new BeanListHandler(Area.class);
		return this.executeQuery(sql.toString(), new Object[]{parentId}, handler);
	}

	@Override
	public Area getParentByPid(String parentId)throws SQLException {
		String sql = "select t1.ID, t1.AVAILABLE, t1.CODE, t1.DIMENSION, t1.FIRSTLETTER, t1.LEVELTYPE, t1.LONGITUDE, t1.MERGERNAME, t1.NAME,t1.PINYIN, t1.SHORTNAME, t1.ZIP, t1.PARENTID,t2.NAME as PARENTNAME from SYS_AREA t1 left join SYS_AREA t2 on t1.PARENTID=t2.id where t1.ID = ?";
		ResultSetHandler handler = new BeanHandler(Area.class);
		return (Area) this.executeQueryObject(sql, new String[]{parentId}, handler);
	}

	@Override
	public Area selectLinkByPrimaryKey(String id)throws SQLException {
		String sql = "select t1.ID, t1.AVAILABLE, t1.CODE, t1.DIMENSION, t1.FIRSTLETTER, t1.LEVELTYPE, t1.LONGITUDE, t1.MERGERNAME, t1.NAME,t1.PINYIN, t1.SHORTNAME, t1.ZIP, t1.PARENTID,t2.NAME as PARENTNAME from SYS_AREA t1 left join SYS_AREA t2 on t1.PARENTID=t2.id where t1.Id=?";
		ResultSetHandler handler = new BeanHandler(Area.class);
		return (Area) this.executeQueryObject(sql, new String[]{id}, handler);
	}

	@Override
	public boolean insert(Area area)throws SQLException {
		String sql = "insert into SYS_AREA ("+
					"	ID, AVAILABLE,CODE,DIMENSION,FIRSTLETTER,"+
					"	LEVELTYPE,LONGITUDE,MERGERNAME,NAME,PINYIN,"+
					"	SHORTNAME, ZIP, PARENTID)"+
					"values (?,?,?,?,?,"+
					"		 ?,?,?,?,?,"+
					"		 ?,?,?)";
		Object[] param = {
			area.getId(),
			area.isAvailable(),
			area.getCode(),
			area.getDimension(),
			area.getFirstLetter(),
			area.getLevelType(),
			area.getLongitude(),
			area.getMergerName(),
			area.getName(),
			area.getPinyin(),
			area.getShortName(),
			area.getZip(),
			area.getParentId()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(Area area)throws SQLException {
		String sql = "update SYS_AREA"+
					"set AVAILABLE =?,CODE =?,DIMENSION =?,FIRSTLETTER =?,LEVELTYPE =?,"+
					"		LONGITUDE =?,MERGERNAME =?,NAME =?,PINYIN =?,SHORTNAME =?,"+
					"		ZIP =?,PARENTID =?"+
					"where ID =?";
		Object[] param = {
				area.isAvailable(),
				area.getCode(),
				area.getDimension(),
				area.getFirstLetter(),
				area.getLevelType(),
				area.getLongitude(),
				area.getMergerName(),
				area.getName(),
				area.getPinyin(),
				area.getShortName(),
				area.getZip(),
				area.getParentId(),
				area.getId()
		};
		int i = this.executeUpdate(sql, param);
		return false;
	}

	@Override
	public boolean delete(Area area)throws SQLException {
		String sql = "delete from SYS_AREA where ID = ?";
		return this.executeUpdate(sql,new String[]{area.getId()})>0;
	}

	@Override
	public List<Area> getListNonePaged(Area area)throws SQLException {
		StringBuffer sql = new StringBuffer("select  t1.ID, t1.AVAILABLE, t1.CODE, t1.DIMENSION, t1.FIRSTLETTER, t1.LEVELTYPE, t1.LONGITUDE, t1.MERGERNAME, t1.NAME,   t1.PINYIN, t1.SHORTNAME, t1.ZIP, t1.PARENTID,t2.NAME as PARENTNAME from SYS_AREA t1 left join SYS_AREA t2 on t1.PARENTID=t2.id  where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(area.getParentId())){
			sql.append(" and t1.parentId=? ");
			param.add(area.getId());
		}	
		ResultSetHandler handler = new BeanListHandler(Area.class);
		return this.executeQuery(sql.toString(), param.toArray(), handler);
	}

	@Override
	public Area get(String id)throws SQLException {
		String sql = "select * from sys_area where id=?";
		ResultSetHandler handler = new BeanHandler(Area.class);
		return (Area) this.executeQueryObject(sql, new String[]{id}, handler);
	}
	
	
	public List<Area> selectByName(String name)throws SQLException {
		String sql = "select ID, AVAILABLE, CODE, DIMENSION, FIRSTLETTER, LEVELTYPE, LONGITUDE, MERGERNAME, NAME, PINYIN, SHORTNAME, ZIP, PARENTID,null as parentname from SYS_AREA where name = ?";
		ResultSetHandler handler = new BeanListHandler(Area.class);
		return this.executeQuery(sql.toString(), new Object[]{name}, handler);
	}


}
