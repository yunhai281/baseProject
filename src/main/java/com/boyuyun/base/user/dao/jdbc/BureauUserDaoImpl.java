package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.BureauUserDao;
import com.boyuyun.base.user.entity.BureauUser;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BureauUserDaoImpl extends BaseDAO implements BureauUserDao {

	/**
	 * 教育局用户编辑
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public boolean update(BureauUser bureau) throws SQLException {
		String strSql = "update Bureau_User set GOVERNMENTJUID = ?, GOVERNMENTTINGID = ?,GOVERNMENTID = ? where ID = ?";
		List params = new  ArrayList();
		params.add(bureau.getGovernmentJuId());
		params.add(bureau.getGovernmentTingId());
		params.add(bureau.getGovernmentId());
		params.add(bureau.getId());
		int result = super.executeUpdate(strSql, params.toArray());
		return result>0?true:false;
	}
	
	/**
	 * 教育局用户只更新编号
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public boolean updateSortnum(BureauUser bureau) throws SQLException {
		String strSql = "update Bureau_User set sortnum = ?  where ID = ?";
		List params = new  ArrayList();
		params.add(bureau.getSortnum()); 
		params.add(bureau.getId());
		int result = super.executeUpdate(strSql, params.toArray());
		return result>0?true:false;
	}
	
	/**
	 * 教育局用户新建
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean insert(BureauUser bureau) throws SQLException {
		String strSql = "insert into Bureau_User (ID,GOVERNMENTJUID,GOVERNMENTTINGID,GOVERNMENTID,sortnum) values(?,?,?,?,?)";
		List params = new  ArrayList();
		params.add(bureau.getId());
		params.add(bureau.getGovernmentJuId());
		params.add(bureau.getGovernmentTingId());
		params.add(bureau.getGovernmentId());
		String governmentId="";
		if (!Strings.isNullOrEmpty(bureau.getGovernmentJuId())) {
			governmentId=bureau.getGovernmentJuId();
		}else if (!Strings.isNullOrEmpty(bureau.getGovernmentTingId())) {
			governmentId=bureau.getGovernmentTingId();
		}else {
			governmentId=bureau.getGovernmentId();
		}
		// 按照部门排序
		params.add(getMaxSortnum(governmentId) +1);
		int result = super.executeUpdate(strSql, params.toArray());
		return result>0?true:false;
	}

	/**
	 * 教育局用户删除
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delete(BureauUser bureau) throws SQLException {
		String strSql = "delete from Bureau_User where ID = ?";
		List params = new  ArrayList();
		params.add(bureau.getId());
		int result = super.executeUpdate(strSql, params.toArray());
		return result>0?true:false;
	}

	/**
	 * 获取教育局用户
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public BureauUser getBureauUser(BureauUser bureau) throws SQLException {
		String sql = "    select " +
						"       u.userName," +
						"		u.email," +
						"		u.mobile," +
						"		u.pwd,"+
						"		u.realName,u.sex,"+
						"		org.name governmentName,"+
						"		d1.name as sexName," +
						"       t.governmentId,t.id,t.sortnum "+
						"    from Bureau_User  t " +
						"    left join user_base u on u.id=t.id"+
						"    left join dictionary_item d1 on d1.id=u.sex "+
						"    left join org_government org on org.id=t.governmentId " +
						" where t.Id=? ";
		return (BureauUser) super.executeQueryObject(sql, new Object []{bureau.getId()}, new BeanHandler(bureau.getClass()));
	}
	/**
	 * 获取教育局用户列表
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BureauUser> getList(BureauUser bureau) throws SQLException {
		List paramList = new ArrayList();
		String sql = " select u.username,"+
					     "		u.email,u.ENABLE," +
					     "		u.mobile," +
					     "		u.pwd,"+
					     "      (SELECT GROUP_CONCAT(d2.name) name FROM bureauuser_post p left join  dictionary_item d2 on d2.id= p.post WHERE p.bureauUserId=t.id) as postName, "+
						 "		u.realName,u.sex,"+
						 "		org.name governmentName,"+
						 "		d1.name as sexName,"+
						 " 		t.governmentId,t.id "+
						 " from Bureau_User t " +
						 " left join user_base u on u.id=t.id "+
						 " left join dictionary_item d1 on d1.id=u.sex "+
						 " left join org_government org on org.id=t.governmentId " +
						 " where 1=1 ";
		if (!Strings.isNullOrEmpty(bureau.getRealName())) {
			sql += " and u.realName like concat(concat('%',?),'%')";
			paramList.add(bureau.getRealName());
		}
		
		if(!Strings.isNullOrEmpty(bureau.getGovernmentId())){
			if(bureau.getGovernmentId().indexOf(",")>-1){
				sql += " and t.governmentId in ('"+StringUtils.join(bureau.getGovernmentId().split(","), "','")+"') ";
			}else{
				sql += " and t.governmentId=? ";
				paramList.add(bureau.getGovernmentId());
			}
			sql += " order by t.sortnum ";
		}
//		if(bureau.getBegin()>0&&bureau.getEnd()>0){
//			sql+=" limit ?,?  ";
//			paramList.add(bureau.getBegin());
//			paramList.add(bureau.getEnd());
//		}
		Object [] param = paramList.toArray();
		ResultSetHandler handler = new BeanListHandler(BureauUser.class);
		return this.executeQueryPage(sql,bureau.getBegin(),bureau.getEnd(), param, handler);
	}
	/**
	 * 获取教育局用户列表个数
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getListPagedCount(BureauUser bureau) throws SQLException {
		List paramList = new ArrayList();
		String sql  = "select " +
					  "    count(0) " +
					  " from Bureau_User t " +
					  " left join user_base u on u.id=t.id "+
					  " left join dictionary_item d1 on d1.id=u.sex "+
					  " left join org_government org on org.id=t.governmentId " +
					  " where 1=1 ";
		if (!Strings.isNullOrEmpty(bureau.getRealName())) {
			sql += " and u.realName like concat(concat('%',?),'%') ";
			paramList.add(bureau.getRealName());
		}
		if(!Strings.isNullOrEmpty(bureau.getGovernmentId())){
			if(bureau.getGovernmentId().indexOf(",")>-1){
				sql += " and t.governmentId in ('"+StringUtils.join(bureau.getGovernmentId().split(","), "','")+"') ";
			}else{
				sql += " and t.governmentId=? ";
				paramList.add(bureau.getGovernmentId());
			}
			
		}
		return super.executeCount(sql, paramList.toArray());
	}


	 
	/**
	 * Description 用户名是否重复
	 * @param name
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.user.dao.BureauUserDao#getListByName(java.lang.String)
	 */
	@Override
	public int getListByName(String name) throws SQLException {
		List paramList = new ArrayList();
		String sql  = "select " +
					  "    count(0) " +
					  " from Bureau_User t " +
					  " left join user_base u on u.id=t.id "+
					  " where 1=1 ";
		if (!Strings.isNullOrEmpty(name)) {
			sql += " and u.userName =? ";
			paramList.add(name);
		}
		return super.executeCount(sql, paramList.toArray());
	}
	
	/**
	 * @Description 按照部门取得最大排序
	 * @author jms 
	 * @param governmentId 部门
	 * @return
	 * @throws SQLException
	 */
	public int getMaxSortnum(String governmentId) throws SQLException {
		List paramList = new ArrayList();
		String sql  = "select MAX(sortnum) from bureau_user where governmentJuId=? or governmentTingId=? or governmentId=?";
		paramList.add(governmentId);
		paramList.add(governmentId);
		paramList.add(governmentId);
		return super.executeCount(sql, paramList.toArray());
	}
	
	@Override
	public List<BureauUser> getSortnumList(BureauUser bureau) throws SQLException {
		List paramList = new ArrayList();
		String sql = " select u.username,"+
					     "		u.email," +
					     "		u.mobile," +
					     "		u.pwd,"+
					     "      (SELECT GROUP_CONCAT(d2.name) name FROM bureauuser_post p left join  dictionary_item d2 on d2.id= p.post WHERE p.bureauUserId=t.id) as postName, "+
						 "		u.realName,u.sex,"+
						 "		org.name governmentName,"+
						 "		d1.name as sexName,"+
						 " 		t.governmentId,t.id,t.sortnum "+
						 " from Bureau_User t " +
						 " left join user_base u on u.id=t.id "+
						 " left join dictionary_item d1 on d1.id=u.sex "+
						 " left join org_government org on org.id=t.governmentId " +
						 " where 1=1 ";
		
		if(!Strings.isNullOrEmpty(bureau.getGovernmentId())){
			if(bureau.getGovernmentId().indexOf(",")>-1){
				sql += " and t.governmentId in ('"+StringUtils.join(bureau.getGovernmentId().split(","), "','")+"') ";
			}else{
				sql += " and t.governmentId=? ";
				paramList.add(bureau.getGovernmentId());
			}
			
		}
		sql += " and t.sortnum=? ";
		sql += " order by t.sortnum ";
		paramList.add(bureau.getSortnum());
		Object [] param = paramList.toArray();
		ResultSetHandler handler = new BeanListHandler(BureauUser.class);
		return this.executeQuery(sql, param, handler);
	}
}
