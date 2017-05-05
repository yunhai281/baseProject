package com.boyuyun.base.sso.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.RoleDao;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.consts.UserType;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleDaoImpl extends BaseDAO implements RoleDao {
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<User> getUserByRolePaged(String roleId,int begin,int end)throws SQLException{
		//String sql = "select * from user_base limit 0,10";
		StringBuffer sql = new StringBuffer();
		//1.查询无用户类型或用户机构限制的用户
		sql.append("select t1.*,d1.name as sexname,d2.`name` as schoolName from user_base t1 " +
				" left join dictionary_item d1 on d1.ID = t1.sex " +
				" left join " +
				"(select a.id,b.`name` from teacher a LEFT JOIN school b on a.schoolId = b.id union select c.id,d.`name` from student c LEFT JOIN school d " +
				"ON c.schoolId = d.id) d2 on d2.ID = t1.ID "+
				" where 1=1 " +
				"and (select count(t1.id) from sso_role t1 where t1.id='roleId'  " +
					"and t1.userscopetype = 'type' " +
					"and (t1.usertype='all' or t1.usertype is null)  " +
					"and (t1.govertype='all' or t1.govertype is null))>0 ");
		
		sql.append("union ");
		//2.查询设置方式为person的用户
		StringBuffer personbuf = new StringBuffer();
		personbuf.append("select t1.user_id as id from sso_role_user t1 " +
				"join sso_role t2 on t1.role_id = t2.id " +
				"where t2.id='roleId' and  t2.userscopetype='person' ");
		
		//3.查询设置方式为type，所有机构用户
		StringBuffer deptbuf = new StringBuffer();
		deptbuf.append("select t1.id from teacher t1 ");
		deptbuf.append("where t1.schoolId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from student t1 ");
		deptbuf.append("where t1.schoolId in (select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from student_parent_relation t1 ");
		deptbuf.append("left join student t2 on t1.studentId = t2.id ");
		deptbuf.append("where t2.schoolId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from bureau_user t1 ");
		deptbuf.append("where t1.governmentId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		
		//4.查询设置方式为type，userType为self的用户类型下所有用户（goverType为null或all）
		StringBuffer singleUserType = new StringBuffer();
		singleUserType.append("select tt1.id from ( ");
		singleUserType.append("select t1.id,'"+UserType.教师.ordinal()+"' as userType from teacher t1 ");
		singleUserType.append("union ");
		singleUserType.append("select t2.id,'"+UserType.学生.ordinal()+"' as userType from student t2 ");
		singleUserType.append("union ");
		singleUserType.append("select t3.id,'"+UserType.家长.ordinal()+"' as userType from student_parent_relation t3 ");
			singleUserType.append("left join student t4 on t3.studentId = t4.id ");
		singleUserType.append("union ");
		singleUserType.append("select t5.id,'"+UserType.教育局.ordinal()+"' as userType from bureau_user t5 ) as tt1 ");
		singleUserType.append("where tt1.userType in (select distinct tt1.usertype  from sso_role_user_type tt1 ");
		singleUserType.append("join sso_role tt2 on  tt2.id=tt1.role_id where tt2.userscopetype='type' and tt2.usertype='self' and tt1.role_id='roleId' ");
		singleUserType.append("and  (tt2.govertype='all' or tt2.govertype is null)) ");
		
		//5.组织查询语句
		sql.append("select t1.*,d1.name as sexname,d2.`name` as schoolName from user_base t1 left join dictionary_item d1 on d1.ID = t1.sex left join (select a.id,b.`name` from teacher a LEFT JOIN school b on a.schoolId = b.id union select c.id,d.`name` from student c LEFT JOIN school d ON c.schoolId = d.id) d2 on d2.ID = t1.ID where 1=1 and t1.id in ( ");
		sql.append(personbuf);
		sql.append("union ");
		sql.append(deptbuf);
		sql.append("union ");
		sql.append(singleUserType);
		sql.append("union ");
		//查询设置方式为type，userType为self,goverType不为空或者all
		sql.append("select tu.id from user_base tu where tu.id in ( "+deptbuf+" ) and tu.id in ( "+singleUserType+" ) ");
		sql.append(" ) ");
		if(begin>0&&end>0){
			sql.append(" limit "+begin+","+(end-begin+1));
		}
		//替换参数
		String sqlStr = sql.toString().replace("roleId", roleId);
		deptbuf=null;
		singleUserType=null;
		personbuf=null;
		return this.executeQuery(sqlStr, new BeanListHandler(User.class));
	}
	/**
	 * 根据角色查询人员
	 * @param roleId
	 * @return
	 */
	public int getUserByRoleCount(String roleId)throws SQLException{
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(t.id) from ( ");
		//1.查询无用户类型或用户机构限制的用户
		sql.append("select * from user_base t1 where 1=1 " +
				"and (select count(t1.id) from sso_role t1 where t1.id='roleId'  " +
					"and t1.userscopetype = 'type' " +
					"and (t1.usertype='all' or t1.usertype is null)  " +
					"and (t1.govertype='all' or t1.govertype is null))>0 ");
		
		sql.append("union ");
		//2.查询设置方式为person的用户
		StringBuffer personbuf = new StringBuffer();
		personbuf.append("select t1.user_id as id from sso_role_user t1 " +
				"join sso_role t2 on t1.role_id = t2.id " +
				"where t2.id='roleId' and  t2.userscopetype='person' ");
		
		//3.查询设置方式为type，所有机构用户
		StringBuffer deptbuf = new StringBuffer();
		deptbuf.append("select t1.id from teacher t1 ");
		deptbuf.append("where t1.schoolId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from student t1 ");
		deptbuf.append("where t1.schoolId in (select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from student_parent_relation t1 ");
		deptbuf.append("left join student t2 on t1.studentId = t2.id ");
		deptbuf.append("where t2.schoolId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		deptbuf.append("union ");
		deptbuf.append("select t1.id from bureau_user t1 ");
		deptbuf.append("where t1.governmentId in ");
		deptbuf.append("(select tt1.government_id from sso_role_user_government_school tt1 ");
		deptbuf.append("join sso_role tt2 on tt1.role_id = tt2.id ");
		deptbuf.append("where tt2.id='roleId' and  tt2.userscopetype='type' and (tt2.usertype='all' or tt2.usertype is null) and tt2.govertype !='all') ");
		
		//4.查询设置方式为type，userType为self的用户类型下所有用户（goverType为null或all）
		StringBuffer singleUserType = new StringBuffer();
		singleUserType.append("select tt1.id from ( ");
		singleUserType.append("select t1.id,'"+UserType.教师.ordinal()+"' as userType from teacher t1 ");
		singleUserType.append("union ");
		singleUserType.append("select t2.id,'"+UserType.学生.ordinal()+"' as userType from student t2 ");
		singleUserType.append("union ");
		singleUserType.append("select t3.id,'"+UserType.家长.ordinal()+"' as userType from student_parent_relation t3 ");
			singleUserType.append("left join student t4 on t3.studentId = t4.id ");
		singleUserType.append("union ");
		singleUserType.append("select t5.id,'"+UserType.教育局.ordinal()+"' as userType from bureau_user t5 ) as tt1 ");
		singleUserType.append("where tt1.userType in (select distinct tt1.usertype  from sso_role_user_type tt1 ");
		singleUserType.append("join sso_role tt2 on  tt2.id=tt1.role_id where tt2.userscopetype='type' and tt2.usertype='self' and tt1.role_id='roleId' ");
		singleUserType.append("and  (tt2.govertype='all' or tt2.govertype is null)) ");
		
		//5.组织查询语句
		sql.append("select * from user_base t1 where 1=1 and t1.id in ( ");
		sql.append(personbuf);
		sql.append("union ");
		sql.append(deptbuf);
		sql.append("union ");
		sql.append(singleUserType);
		sql.append("union ");
		//查询设置方式为type，userType为self,goverType不为空或者all
		sql.append("select tu.id from user_base tu where tu.id in ( "+deptbuf+" ) and tu.id in ( "+singleUserType+" ) ");
		sql.append(" )) as t ");
		//替换参数
		String sqlStr = sql.toString().replace("roleId", roleId);
		deptbuf=null;
		singleUserType=null;
		personbuf=null;
		return this.executeCount(sqlStr, new Object[]{});
	}
	/**
	 * Description 取得全部的角色
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#getAllRoleList()
	 */
	@Override
	public List<Role> getAllRoleList() throws SQLException {
		String sql="select * from sso_role ";
		ResultSetHandler handler = new BeanListHandler(Role.class);
		return this.executeQuery(sql, handler);
	}

	/**
	 * Description 新增角色
	 * @param role
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#insert(com.boyuyun.base.sso.entity.Role)
	 */
	@Override
	public boolean insert(Role role) throws SQLException {
		String sql = "insert into sso_role (id, name, remark,userscopetype) values (?,?,?,'type')";
		Object [] param = new Object[]{
				role.getId(),
				role.getName(),
				role.getRemark()
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	/**
	 * Description 取得明细
	 * @param id
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#get(java.lang.String)
	 */
	@Override
	public Role get(String id) throws SQLException {
		String sql = "select * from sso_role where id=?";
		ResultSetHandler handler = new BeanHandler(Role.class);
		return (Role) this.executeQueryObject(sql, new String[]{id}, handler);
	}

	/**
	 * Description 修改
	 * @param role
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#update(com.boyuyun.base.sso.entity.Role)
	 */
	@Override
	public boolean update(Role role) throws SQLException {
		String sql = "update sso_role set name = ?,remark = ?,userScopeType = ?,userType = ?,goverType = ? where id = ?";
		Object [] param = new Object[]{
				role.getName(),
				role.getRemark(),
				role.getUserScopeType(),
				role.getUserType(),
				role.getGoverType(),
				role.getId()
		};
		int i=this.executeUpdate(sql, param);
		return i>0;
	}

	/**
	 * Description 删除
	 * @param role
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#delete(com.boyuyun.base.sso.entity.Role)
	 */
	@Override
	public boolean delete(String id) throws SQLException {
		String sql = "delete from sso_role where id=?";
		int i = this.executeUpdate(sql, new String[]{id});
		return i>0;
	}
	
	/**
	 * Description  保存应用范围
	 * @param roleId
	 * @param applicationId
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#saveApp(java.lang.String, java.lang.String)
	 */
	public boolean saveApp(String id,String roleId,String applicationId)throws SQLException{
    	String sql = "insert into sso_role_application (id,roleId,applicationid) values (?,?,? )";
		String[] applicationIds=applicationId.split(",");
		int num=0;
		for (int i = 0; i < applicationIds.length; i++) {
			String string = applicationIds[i];
			if(!Strings.isNullOrEmpty(string)){
				Object [] param = new Object[]{
						UUID.randomUUID().toString().replace("-", ""),
						roleId,
						string
				};
				num=this.executeUpdate(sql, param);
			}
		}
		return num>0;
	}
	
	/**
	 * Description 删除
	 * @param roleId
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.sso.dao.RoleDao#delApp(java.lang.String)
	 */
	public boolean deleteApp(String roleId)  throws SQLException{
		String sql = "delete from sso_role_application where roleId=?";
		int i = this.executeUpdate(sql, new String[]{roleId});
		return i>0;
	}

	/**
	 * @Description 取得角色设置的应用
	 * @author jms
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public List getAPP(String roleId)  throws SQLException{
		String sql="select * from sso_role_application where roleId=?";
		List list= (List)this.executeQuery(sql,new String[]{roleId}, new ArrayListHandler());
		return list;
	}

	@Override
	public boolean deleteRoleApplication(Integer applicationId) throws SQLException {
		String sql = "delete from sso_role_application where applicationId=? ";
		return this.executeUpdate(sql, new Object[]{applicationId})>0;
	}
	
	@Override
	public List<Role> selectByName(String name) throws SQLException {
		String sql="select * from sso_role where 1=1";
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(name)){
			sql +=" and name like concat(concat('%',?),'%') ";
			param.add(name);
		}
		
		ResultSetHandler handler = new BeanListHandler(Role.class);
		return this.executeQuery(sql.toString(),param.toArray(),  handler);
	}
	
	
}
