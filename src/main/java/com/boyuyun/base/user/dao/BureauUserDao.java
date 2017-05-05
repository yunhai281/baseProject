package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.BureauUser;


public interface BureauUserDao {
	/**
	 * 教育局用户编辑
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public boolean update(BureauUser bureau) throws SQLException;
	/**
	 * 教育局用户新建
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public boolean insert(BureauUser bureau) throws SQLException;
	/**
	 * 教育局用户删除
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	public boolean delete(BureauUser bureau) throws SQLException;
	/**
	 * 获取教育局用户
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	BureauUser getBureauUser(BureauUser bureau) throws SQLException;
	/**
	 * 教育局用户列表
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	List<BureauUser> getList(BureauUser bureau)throws SQLException;
	/**
	 * 教育局用户列表个数
	 * @param user 教育局用户
	 * @return
	 * @throws Exception
	 */
	int getListPagedCount(BureauUser bureau) throws SQLException;
	
	public int getListByName(String name) throws SQLException;
	
	public List<BureauUser> getSortnumList(BureauUser bureau) throws SQLException;
	public boolean updateSortnum(BureauUser bureau) throws SQLException;
	public int getMaxSortnum(String governmentId) throws SQLException;
}
