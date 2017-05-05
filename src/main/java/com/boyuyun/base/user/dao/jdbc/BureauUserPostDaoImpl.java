package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.BureauUserPostDao;
import com.boyuyun.base.user.entity.BureauUserPost;
import com.dhcc.common.db.BaseDAO;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BureauUserPostDaoImpl extends BaseDAO implements BureauUserPostDao {

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 删除人员和岗位关联记录
	 * @return
	 */
	@Override
	public int deleteByUserId(String bureauUserId) throws SQLException {
		String strSql = "delete from bureauuser_post where bureauUserId = ?";
		List params = new  ArrayList();
		params.add(bureauUserId);
		int result = super.executeUpdate(strSql, params.toArray());
		return result;
		
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 插入人员和岗位关联记录
	 * @return
	 */
	@Override
	public int insert(BureauUserPost bureau) throws SQLException {
		String strSql = "insert into bureauuser_post(id,bureauUserId,post)values(?,?,?) ";
		List params = new  ArrayList();
		params.add(bureau.getId());
		params.add(bureau.getBureauUserId());
		params.add(bureau.getPost());
		int result = super.executeUpdate(strSql, params.toArray());
		return result;
		
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 根据人员id获取岗位关联记录
	 * @return
	 */
	public List<BureauUserPost> getListByUserId(String bureauUserId) throws SQLException{
		List paramList = new ArrayList();
		String sql =  " select "+
					  "		p.post,d1.name as postName	 "+	
					  " FROM BureauUser_Post p  "+
					  " left join dictionary_item d1 on d1.id=p.post "+
					  " where bureauUserId =?"+
					  " order by p.post ";
		paramList.add(bureauUserId);
		Object [] param = paramList.toArray();
		return super.executeQuery(sql, param, new BeanListHandler(BureauUserPost.class));
	}

}
