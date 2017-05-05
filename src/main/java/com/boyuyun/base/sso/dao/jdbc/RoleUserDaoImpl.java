package com.boyuyun.base.sso.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.RoleUserDao;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleUser;
import com.boyuyun.base.user.entity.User;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleUserDaoImpl extends BaseDAO implements RoleUserDao{

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:24:47
	 * @description 根据roleId获取角色用户范围-指定用户列表
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override  
	public List<RoleUser> getList(String roleId) throws SQLException {
		List<RoleUser> list = null;
		List<Object> param = new ArrayList<Object>();
		if(StringUtils.isNotBlank(roleId)){
			String sql="select t1.role_id as roleId,t1.user_id as userId,t1.userType,t2.realName as userName from sso_role_user t1 " +
					"left join user_base t2 on t1.user_id = t2.id  " +
					"where t1.role_id = ? ";
			param.add(roleId);
			ResultSetHandler handler = new BeanListHandler(RoleUser.class);
			list =  this.executeQuery(sql, param.toArray(), handler);
		}
		return list;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:25:24
	 * @description 根据roleId删除角色用户范围-指定用户
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deleteByRoleId(String roleId) throws SQLException {
		String sql = "delete from sso_role_user where role_id=?";
		int i = this.executeUpdate(sql, new String[]{roleId});
		return i;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:25:35
	 * @description 批量添加角色用户范围-指定用户
	 * @param userList
	 * @throws SQLException 
	 */
	@Override
	public void addBatch(List<RoleUser> userList) throws SQLException {
		StringBuilder strSql = new StringBuilder();
		strSql.append("insert into sso_role_user(role_id,user_id,usertype) values(?,?,?)");
		int length=userList.size();
		Object [][] param = new Object [length][3] ;
		for (int i=0;i<length;i++){
			RoleUser po=userList.get(i);
			param[i][0]=po.getRoleId();
			param[i][1]=po.getUserId();
			param[i][2]=po.getUserType();
		}
		super.executeBatchUpdate(strSql.toString(), param);
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-27 下午3:20:03
	 * @description 根据用户id获取用户角色列表
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<Role> getRoleListByUserId(String userId) throws SQLException {
		List<Role> list = null;
		List<Object> param = new ArrayList<Object>();
		if(StringUtils.isNotBlank(userId)){
			//1、获取用户类型
			String userType = "";
			String userTypeSql = "select * from user_base where id=?";
			User user = (User) this.executeQueryObject(userTypeSql, new Object[]{userId},new BeanHandler(User.class));
			if(user!=null){
				userType = user.getUserType();
			}
			//取用户类型交集
			List<String> userTypeList = null;
			if(StringUtils.isNotBlank(userType)){
				userTypeList = Arrays.asList(userType.split(","));;
			}
			//2、获取用户机构
			String governmentSql="select t1.schoolId as governmentId from teacher t1 where t1.id='userId' " +
					"union select t1.schoolId as governmentId from student t1 where t1.id='userId' " +
					"union select t1.schoolId as governmentId from student t1 where t1.id in (select t2.studentId from student_parent_relation t2 where t2.parentId='userId') " +
					"union select t1.governmentId from bureau_user t1 where t1.id='userId' ";
			
			//3、查询用户角色
			String sql="select t1.* from sso_role t1  where t1.userscopetype='type' and (t1.usertype='all' or t1.usertype is null)  and (t1.govertype='all' or t1.govertype is null) " +
					"union select t1.* from sso_role t1 left join sso_role_user t2 on t1.id = t2.role_id where t2.user_id='userId' and  t1.userscopetype='person' " +
					"union select t1.* from sso_role t1 where t1.userscopetype='type' and (t1.usertype='all' or t1.usertype is null) and t1.govertype !='all' " +
						" and t1.id in (select tt1.role_id  from sso_role_user_government_school tt1 where tt1.government_id in (userGovernmentId) ) " +
					"union select t1.* from sso_role t1 left join sso_role_user_type t2 on t1.id = t2.role_id " +
						"where t1.userscopetype='type' and t1.id=t2.role_id and t1.usertype='self' and t2.usertype in (userRoleType) and  (t1.govertype='all' or t1.govertype is null) " +
					"union select t1.* from sso_role t1 left join sso_role_user_type t2 on t1.id = t2.role_id " +
						"left join sso_role_user_government_school t3 on t1.id = t3.role_id " +
						"where t1.userscopetype='type' and t1.id=t2.role_id and t1.usertype='self' and t2.usertype in (userRoleType) and  t3.government_id in (userGovernmentId) ";
			sql = sql.replace("userGovernmentId", governmentSql);
			sql = sql.replace("userId",userId);
			sql = sql.replace("userRoleType", "'"+StringUtils.join(userTypeList,"','")+"'");
			ResultSetHandler rolehandler = new BeanListHandler(Role.class);
			list =  this.executeQuery(sql, new Object[]{}, rolehandler);
		}
		return list;
	}

	@Override
	public int deleteByRoleId(String roleId, String userType)
			throws SQLException {
		String sql = "delete from sso_role_user where role_id=? and userType=? ";
		int i = this.executeUpdate(sql, new String[]{roleId,userType});
		return i;
	}


}
