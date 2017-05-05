package com.boyuyun.base.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.user.entity.Parent;

public interface ParentDao {
	int insert(Parent parent) throws SQLException;
	int delete(String id) throws SQLException;
	int update(Parent parent) throws SQLException;
	List<Parent> getList(Parent parent) throws SQLException;
	Parent getParent(Parent parent) throws SQLException;
	int getListPagedCount(Parent parent) throws SQLException;
}
