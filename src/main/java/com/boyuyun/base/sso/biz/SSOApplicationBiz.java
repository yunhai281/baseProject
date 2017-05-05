package com.boyuyun.base.sso.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sso.entity.SSOApplication;


public interface SSOApplicationBiz{
	public boolean add(SSOApplication app,String[] attrs)throws Exception;
	public SSOApplication get(int id)throws Exception;
	public boolean update(SSOApplication app,String[] attrs)throws Exception;
	public boolean delete(SSOApplication app)throws Exception;
	public boolean deleteAll(List<SSOApplication> list)throws Exception;
	public List<SSOApplication> getListPaged(SSOApplication sso)throws Exception;
	public int getListPagedCount(SSOApplication sso)throws Exception;

	public List<SSOApplication> getAllEnabledAppList()throws Exception;
	public int getMaxSortNum()throws Exception;
	public int getMaxOrder() throws SQLException;
}
