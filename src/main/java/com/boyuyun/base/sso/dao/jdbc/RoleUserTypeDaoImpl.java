package com.boyuyun.base.sso.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.RoleUserTypeDao;
import com.boyuyun.base.sso.entity.Role;
import com.boyuyun.base.sso.entity.RoleUserType;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleUserTypeDaoImpl extends BaseDAO implements RoleUserTypeDao {

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:26:48
	 * @description 增加角色用户范围-用户类型
	 * @param po
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int insert(RoleUserType po) throws SQLException {
		String sql = "insert into sso_role_user_type (role_id, userType, goverType) values (?,?,?)";
		Object [] param = new Object[]{
				po.getRoleId(),
				po.getUserType(),
				po.getGoverType()
		};
		int i=this.executeUpdate(sql, param);
		return i;
	}
	
	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:27:02
	 * @description 根据roleId获取角色用户范围-用户类型
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public RoleUserType get(String roleId) throws SQLException {
		String sql = "select role_id as roleId,userType,goverType from sso_role_user_type where role_id = ?";
		ResultSetHandler handler = new BeanHandler(Role.class);
		return (RoleUserType) this.executeQueryObject(sql, new String[]{roleId}, handler);
	}
	
	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:27:06
	 * @description 修改角色用户范围-用户类型
	 * @param po
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int update(RoleUserType po) throws SQLException {
		String sql = "update sso_role_user_type set userType = ?,goverType = ? where role_id = ?";
		Object [] param = new Object[]{
				po.getUserType(),
				po.getGoverType(),
				po.getRoleId()
		};
		int i=this.executeUpdate(sql, param);
		return i;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午5:37:29
	 * @description 根据roleId删除角色用户范围-用户类型
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deleteByRoleId(String roleId) throws SQLException {
		String sql = "delete from sso_role_user_type where role_id=?";
		int i = this.executeUpdate(sql, new String[]{roleId});
		return i;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午5:37:23
	 * @description 批量添加角色用户范围-用户类型
	 * @param userTypeList
	 * @throws SQLException
	 */
	@Override
	public void addBatch(List<RoleUserType> userTypeList) throws SQLException {
		StringBuilder strSql = new StringBuilder();
		strSql.append("insert into sso_role_user_type(role_id,userType,goverType) values(?,?,?)");
		int length=userTypeList.size();
		Object [][] param = new Object [length][3] ;
		for (int i=0;i<length;i++){
			RoleUserType po=userTypeList.get(i);
			param[i][0]=po.getRoleId();
			param[i][1]=po.getUserType();
			param[i][2]=po.getGoverType();
		}
		super.executeBatchUpdate(strSql.toString(), param);
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午5:37:26
	 * @description 根据roleId获取角色用户范围-用户类型列表
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	public List<RoleUserType> getList(String roleId) throws SQLException {
		List<RoleUserType> list = null;
		List<Object> param = new ArrayList<Object>();
		if(StringUtils.isNotBlank(roleId)){
			String sql="select * from sso_role_user_type where role_id = ? ";
			param.add(roleId);
			ResultSetHandler handler = new BeanListHandler(RoleUserType.class);
			list =  this.executeQuery(sql, param.toArray(), handler);
		}
		return list;
	}

}
