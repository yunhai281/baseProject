package com.boyuyun.base.sso.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sso.dao.RoleUserOrgDao;
import com.boyuyun.base.sso.entity.RoleUserOrg;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RoleUserOrgDaoImpl extends BaseDAO implements RoleUserOrgDao {

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:23:52
	 * @description 根据roleId删除角色用户范围-用户机构
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int deleteByRoleId(String roleId) throws SQLException {
		String sql = "delete from sso_role_user_government_school where role_id=?";
		int i = this.executeUpdate(sql, new String[]{roleId});
		return i;
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:23:04
	 * @description 批量添加角色用户范围-用户机构
	 * @param orgList
	 * @throws SQLException
	 */
	@Override
	public void addBatch(List<RoleUserOrg> orgList) throws SQLException {
		StringBuilder strSql = new StringBuilder();
		strSql.append("insert into sso_role_user_government_school(role_id,government_id,government_type) values(?,?,?)");
		int length=orgList.size();
		Object [][] param = new Object [length][3] ;
		for (int i=0;i<length;i++){
			RoleUserOrg po=orgList.get(i);
			param[i][0]=po.getRoleId();
			param[i][1]=po.getGovernmentId();
			param[i][2]=po.getGovernmentType();
		}
		super.executeBatchUpdate(strSql.toString(), param);
	}

	/**
	 * @author happyss
	 * @creteTime 2017-3-21 下午3:23:23
	 * @description 根据roleId获取角色用户范围-用户机构列表
	 * @param roleId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<RoleUserOrg> getList(String roleId) throws SQLException {
		List<RoleUserOrg> list = null;
		List<Object> param = new ArrayList<Object>();
		if(StringUtils.isNotBlank(roleId)){
			String sql="select t1.role_id as roleId,t1.government_id as governmentId,t1.government_type as governmentType," +
					"case when t1.government_type='school' then t2.name else t3.name end as governmentName " +
					"from sso_role_user_government_school t1 " +
					"left join school t2 on t1.government_id = t2.id " +
					"left join org_government t3 on t1.government_id = t3.id " +
					"where t1.role_id = ? ";
			param.add(roleId);
			ResultSetHandler handler = new BeanListHandler(RoleUserOrg.class);
			list =  this.executeQuery(sql, param.toArray(), handler);
		}
		return list;
	}

}
