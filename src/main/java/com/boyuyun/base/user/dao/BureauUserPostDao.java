package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.BureauUserPost;


public interface BureauUserPostDao{

	/**
	 * 根据userId删除记录
	 * @param dictionaryItem
	 */
	int deleteByUserId(String  bureauUserId) throws SQLException;
	int insert(BureauUserPost bureau)throws SQLException;
	List<BureauUserPost> getListByUserId(String bureauUserId)throws SQLException;
}
