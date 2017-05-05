package com.boyuyun.base.org.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.org.entity.Government;


public interface GovernmentDao {
	
	public boolean insert(Government gov) throws SQLException;
	public boolean update(Government gov) throws SQLException;
	public boolean delete(String id) throws SQLException;
	
	public Government get(String id)  throws SQLException;
	public List<Government> selectGovRoot()  throws SQLException;
	
	public List<Government> selectByParentId(String id)  throws SQLException;
	
	public List<Government> validateGov(Government government)  throws SQLException;//增加、修改操作时的查重校验
	
	public Government selectFullById(String id)  throws SQLException;//查询机构包括父节点name
	
	public List<Government> selectGovByName(String name)  throws SQLException;//根据name查询所有机构
	
	public List<Government> getGovByName(String name) throws SQLException;
	public int getGovByCode(String code) throws SQLException;
} 