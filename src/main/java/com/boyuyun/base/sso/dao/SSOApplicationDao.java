package com.boyuyun.base.sso.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.SSOApplication;


public interface SSOApplicationDao {
	/**
	 * 获取新镇应用主键
	 * @return
	 * @throws Exception
	 */
	public int getNextId()throws Exception;
	public boolean insertAttributes(Integer appId,String attr,int aid)throws Exception;
	public boolean deleteAttributes(Integer appId) throws Exception;
	public boolean insert(SSOApplication app)throws SQLException;
	public SSOApplication get(int id)throws SQLException;
	public boolean update(SSOApplication app)throws SQLException;
	public boolean delete(SSOApplication app)throws SQLException;
	public List<SSOApplication> getListPaged(SSOApplication app)throws SQLException;
	public int getListPagedCount(SSOApplication app)throws SQLException;
	public List<SSOApplication> getAllEnabledAppList()throws Exception;
	public List getAttributes(int id)throws SQLException;
	public int getMaxSortNum()throws SQLException;
	public int getMaxOrder() throws SQLException;
}