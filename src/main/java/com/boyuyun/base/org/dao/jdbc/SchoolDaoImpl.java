package com.boyuyun.base.org.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.org.dao.SchoolDao;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.util.consts.Stage;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SchoolDaoImpl extends BaseDAO implements SchoolDao {

	@Override
	public School get(String id) throws SQLException {
		String sql = "select * from SCHOOL where id=?";
		ResultSetHandler handler = new BeanHandler(School.class);
		return (School) this.executeQueryObject(sql, new String[]{id}, handler);
	}
	
	@Override
	public int getSerialNumber() throws SQLException {
		String sql = "select count(1) from SCHOOL ";
		ResultSetHandler handler = new BeanHandler(School.class);
		return this.executeCount(sql);
	}

	@Override
	public List<School> selectAll()  throws SQLException{
		String sql = "select * from SCHOOL ";
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(), handler);
	}

	@Override
	public List<School> selectAllParent() throws SQLException {
		String sql = "select * from SCHOOL WHERE PARENTID IS NULL";
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(), handler);
	}
	@Override
	public List<School> getByParentIdWidthChildAndGradeNums(String id) throws SQLException{
		StringBuffer sql = new StringBuffer("select t1.*,(select count(1) from school t2 where t2.parentId=t1.id) as childNum"
				+ ",(select count(1) from school_grade t3 where t3.schoolId=t1.id) as gradeNum from SCHOOL t1 where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and PARENTID=? ");
			param.add(id);
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}
	@Override
	public List<School> getByParentId(String id) throws SQLException{
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and PARENTID=? ");
			param.add(id);
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}

	@Override
	public List<School> getByGovernmentId(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and GOVERNMENTID=? ");
			param.add(id);
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}

	@Override
	public List<School> findByGovIdsMap(List item)  throws SQLException{
		
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(item!=null && item.size()>0){
			String string="";
			for (Iterator iterator = item.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				param.add(object);
				string+="?,";
			}
			string= string.substring(0,string.length()-1);
			sql.append(" and GOVERNMENTID in ("+string+")  ");
			
			
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}

	@Override
	public List<School> selectSchoolByName(String name) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and name like concat(concat('%',?),'%')  ");
			param.add(name);
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}

	@Override
	public List<School> selectSchoolName(String name) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and name =? ");
			param.add(name);
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}
	
	@Override
	public int getListCodeCount(String code) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(code)){
			sql.append(" and code =? ");
			param.add(code);		
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}  
	
	@Override
	public School getSchoolByCode(String code) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from SCHOOL where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(code)){
			sql.append(" and code=? ");
			param.add(code);		
		} 
		BeanHandler handler = new BeanHandler(School.class);
		return (School) this.executeQueryObject(sql.toString(),param.toArray(),  handler);
	}
	
	@Override
	public School selectFullByPrimaryKey(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select  ");

		sql.append(" t1.ID, t1.ADDRESS, t1.BUILDDATE, t1.CODE, t1.DECORATIONDAY, t1.DESCRIPTION, t1.EMAIL, t1.FAX, t1.LEGANO, t1.MOTTO, ");
		sql.append(" t1.NAME, t1.SCHOOLBOARDTYPE, t1.SCHOOLSTATION, t1.SCHOOLTYPE, t1.SERIALNUMBER, t1.SHORTNAME, t1.TEL, t1.ZIP, ");
		sql.append(" t1.AREAID, t1.GOVERNMENTID, t1.UPDATETIME, t1.SYSTEMTYPE, t1.HEADMASTER, t1.ADMINMOBILE, t1.PARENTID , ");
		sql.append(" area.name as AREANAME,gov.name as GOVERNMENTNAME, ");
		sql.append(" sc.name as PARENTNAME, ");
		sql.append(" d1.name as SYSTEMTYPENAME, ");
		sql.append(" d2.name as SCHOOLBOARDTYPENAME, ");
		sql.append(" d3.name as SCHOOLSTATIONNAME, ");
		sql.append(" d4.name as SCHOOLTYPENAME "); 
		
		sql.append(" from SCHOOL t1 ");
		sql.append(" left join sys_area area on t1.areaid=area.id ");
		sql.append(" left join org_government gov on t1.governmentid=gov.id ");
		sql.append(" left join school sc  on t1.parentid=sc.id ");
		sql.append(" left join dictionary_item d1 on t1.SYSTEMTYPE=d1.id ");
		sql.append(" left join dictionary_item d2 on t1.SCHOOLBOARDTYPE=d2.id ");
		sql.append(" left join dictionary_item d3 on t1.SCHOOLSTATION=d3.id ");
		sql.append(" left join dictionary_item d4 on t1.SCHOOLTYPE=d4.id ");
		sql.append(" where t1.ID = ? ");
		
		
		ResultSetHandler handler = new BeanHandler(School.class);
		return (School) this.executeQueryObject(sql.toString(), new String[]{id}, handler);
	}

	@Override
	public boolean insert(School school) throws SQLException {
		String sql="insert into SCHOOL (ID, ADDRESS, BUILDDATE, " +
				"      CODE, DECORATIONDAY, DESCRIPTION, " +
				"      EMAIL, FAX, LEGANO, " +
				"      MOTTO, NAME, SCHOOLBOARDTYPE, " +
				"      SCHOOLSTATION, SCHOOLTYPE, SERIALNUMBER, " +
				"      SHORTNAME, TEL, ZIP, " +
				"      AREAID, GOVERNMENTID, UPDATETIME, " +
				"      SYSTEMTYPE, HEADMASTER, ADMINMOBILE, " +
				"      PARENTID,PINYIN,JIANPIN)" +
				"    values (?, ?,?,?, ?, ?,?, ?,?,?, ?, ?, ?,?, ?,?, ?, ?,?, ?, ?, ?, ?, ?,?, ?,?)";
		Object [] param = new Object[]{
				school.getId(), school.getAddress(),school.getBuildDate(),
				school.getCode(),school.getDecorationDay(),school.getDescription(),
				school.getEmail(),school.getFax(),school.getLegaNo(),
				school.getMotto(),school.getName(),school.getSchoolBoardType(),
				school.getSchoolStation(),school.getSchoolType(),school.getSerialNumber(),
				school.getShortName(),school.getTel(),school.getZip(),
				school.getAreaId(),school.getGovernmentId(),school.getUpdateTime(),
				school.getSystemType(),school.getHeadmaster(),school.getAdminMobile(),
				school.getParentId(),school.getPinyin(),school.getJianpin()

		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(School school) throws SQLException {
		String sql="update SCHOOL" +
				"    set ADDRESS = ?," +
				"      BUILDDATE = ?," +
				"      CODE = ?," +
				"      DECORATIONDAY =?," +
				"      DESCRIPTION = ?," +
				"      EMAIL = ?," +
				"      FAX = ?," +
				"      LEGANO =?," +
				"      MOTTO =?," +
				"      NAME = ?," +
				"      SCHOOLBOARDTYPE = ?," +
				"      SCHOOLSTATION = ?," +
				"      SCHOOLTYPE = ?," +
				"      SERIALNUMBER = ?," +
				"      SHORTNAME =?," +
				"      TEL =?," +
				"      ZIP = ?," +
				"      AREAID = ?," +
				"      GOVERNMENTID =?," +
				"      UPDATETIME = ?," +
				"      SYSTEMTYPE = ?," +
				"      HEADMASTER = ?," +
				"      ADMINMOBILE = ?," +
				"      PARENTID = ?," +
				"      PINYIN =?," +
				"      JIANPIN = ?" +
				"    where ID = ?";
		Object [] param = new Object[]{
				school.getAddress(),
				school.getBuildDate(),
				school.getCode(),
				school.getDecorationDay(),
				school.getDescription(),
				school.getEmail(),
				school.getFax(),
				school.getLegaNo(),
				school.getMotto(),
				school.getName(),
				school.getSchoolBoardType(),
				school.getSchoolStation(),
				school.getSchoolType(),
				school.getSerialNumber(),
				school.getShortName(),
				school.getTel(),
				school.getZip(),
				school.getAreaId(),school.getGovernmentId(),school.getUpdateTime(),
				school.getSystemType(),school.getHeadmaster(),school.getAdminMobile(),
				school.getParentId(),school.getPinyin(),school.getJianpin(),
				school.getId()

		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(String id) throws SQLException {
		String sql = "delete from SCHOOL where id=?";
		int i = this.executeUpdate(sql, new String[]{id});
		return i>0;
	}

	@Override
	public int getListPagedCount(School school) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(school.getName())){
			sql.append(" and name like concat(concat('%',?),'%') ");
			param.add(school.getName());		
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}
	/**
	 * 查询一个学校所有学段（根据年级查询）
	 * @param schoolId
	 * @return
	 * @throws SQLException
	 */
	public List<Stage> getSchoolStageList(String schoolId)throws SQLException{
		String sql = "select DISTINCT stage from sys_grade where id in(" +
				"select sysGradeId from school_grade where schoolId=?" +
				") order by stage asc";
		MapListHandler handler = new MapListHandler();
		List list = this.executeQuery(sql, new String[]{schoolId},handler);
		List<Stage> result = new ArrayList<Stage>();
		if(list!=null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				int s = (Integer) map.get("stage");
				result.add(Stage.values()[s]);
			}
		}
		return result;
	}
	@Override
	public List<School> getListPaged(School school) throws SQLException {
		StringBuffer sql = new StringBuffer("select  ");

		sql.append(" t1.ID, t1.ADDRESS, t1.BUILDDATE, t1.CODE, t1.DECORATIONDAY, t1.DESCRIPTION, t1.EMAIL, t1.FAX, t1.LEGANO, t1.MOTTO, ");
		sql.append(" t1.NAME, t1.SCHOOLBOARDTYPE, t1.SCHOOLSTATION, t1.SCHOOLTYPE, t1.SERIALNUMBER, t1.SHORTNAME, t1.TEL, t1.ZIP, ");
		sql.append(" t1.AREAID, t1.GOVERNMENTID, t1.UPDATETIME, t1.SYSTEMTYPE, t1.HEADMASTER, t1.ADMINMOBILE, t1.PARENTID , ");
		sql.append(" area.name as AREANAME,gov.name as GOVERNMENTNAME, ");
		sql.append(" sc.name as PARENTNAME, ");
		sql.append(" d1.name as SYSTEMTYPENAME, ");
		sql.append(" d2.name as SCHOOLBOARDTYPENAME, ");
		sql.append(" d3.name as SCHOOLSTATIONNAME, ");
		sql.append(" d4.name as SCHOOLTYPENAME "); 
		
		sql.append(" from SCHOOL t1 ");
		sql.append(" left join sys_area area on t1.areaid=area.id ");
		sql.append(" left join org_government gov on t1.governmentid=gov.id ");
		sql.append(" left join school sc  on t1.parentid=sc.id ");
		sql.append(" left join dictionary_item d1 on t1.SYSTEMTYPE=d1.id ");
		sql.append(" left join dictionary_item d2 on t1.SCHOOLBOARDTYPE=d2.id ");
		sql.append(" left join dictionary_item d3 on t1.SCHOOLSTATION=d3.id ");
		sql.append(" left join dictionary_item d4 on t1.SCHOOLTYPE=d4.id ");
		sql.append(" where 1=1 ");
		
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(school.getName())){
			sql.append(" and t1.name like concat(concat('%',?),'%') ");
			param.add(school.getName());		
		}
		ResultSetHandler handler = new BeanListHandler(School.class);
		return this.executeQueryPage(sql.toString(), school.getBegin(), 
				school.getEnd(), param.toArray(), handler);
	} 
	
	@Override
	public int getSchoolPinYinCount(String pinyin) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(pinyin)){
			sql.append(" and pinyin=? ");
			param.add(pinyin);		
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}
	
	@Override
	public int getSchoolJianPinCount(String jianpin) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(jianpin)){
			sql.append(" and jianpin=? ");
			param.add(jianpin);		
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}
	
	@Override
	public int getSchoolSerialNumberCount(String serialNumber,String code,String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		 
		if(!Strings.isNullOrEmpty(serialNumber)){
			sql.append(" and serialNumber=? ");
			param.add(serialNumber);		
		} 
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and id!=? ");
			param.add(id);		
		}
		
		if(!Strings.isNullOrEmpty(code)){
			sql.append(" and code=? ");
			param.add(code);
		}
		return this.executeCount(sql.toString(), param.toArray());
	}
	
	
	@Override
	public int validatName(String name,String id) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from SCHOOL where 1=1 ");
		List param = new ArrayList();
		  
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and id!=? ");
			param.add(id);		
		}
		
		if(!Strings.isNullOrEmpty(name)){
			sql.append(" and name=? ");
			param.add(name);
		}
		return this.executeCount(sql.toString(), param.toArray());
	}
}
