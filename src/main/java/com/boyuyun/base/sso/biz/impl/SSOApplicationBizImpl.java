package com.boyuyun.base.sso.biz.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.boyuyun.base.sso.biz.SSOApplicationBiz;
import com.boyuyun.base.sso.dao.RoleDao;
import com.boyuyun.base.sso.dao.SSOApplicationDao;
import com.boyuyun.base.sso.entity.SSOApplication;

@Service
public class SSOApplicationBizImpl implements SSOApplicationBiz {
	@Resource
	private SSOApplicationDao sSOApplicationDao;
	@Resource
	private RoleDao roleDao;

	@Transactional
	public boolean add(SSOApplication app,String[] attrs) throws Exception{
		boolean result = true;
		//获取应用主键
		Integer id = sSOApplicationDao.getNextId();
		app.setId(id);
		result &= sSOApplicationDao.insert(app);
		for(int i=0;i<attrs.length;i++){
			result &= sSOApplicationDao.insertAttributes(id, attrs[i], i);
		}
		return result;
	}

	@Override
	public SSOApplication get(int id) throws Exception{
		SSOApplication application = sSOApplicationDao.get(id);
		List attrs = sSOApplicationDao.getAttributes(id);
		application.setAttributes(attrs);
		return application;
	}

	@Transactional
	public boolean update(SSOApplication app,String[] attrs) throws Exception{
		boolean result = true;
		//获取应用主键
		Integer id = app.getId();
		app.setId(id);
		result &= sSOApplicationDao.update(app);
		result &= sSOApplicationDao.deleteAttributes(id);
		for(int i=0;i<attrs.length;i++){
			result &= sSOApplicationDao.insertAttributes(id, attrs[i], i);
		}
		return result;
	}

	@Transactional
	public boolean delete(SSOApplication app) throws Exception{
		boolean result = true;
		result &= sSOApplicationDao.delete(app);
		result &= roleDao.deleteRoleApplication(app.getId());
		result &= sSOApplicationDao.deleteAttributes(app.getId());
		return result;
	}

	@Transactional
	public boolean deleteAll(List<SSOApplication> app)throws Exception {
		boolean result = true;
		if(app!=null){
			for (Iterator iterator = app.iterator(); iterator.hasNext();) {
				SSOApplication ssoApplication = (SSOApplication) iterator.next();
				result&=this.delete(ssoApplication);
			}
		}
		return result;				
	}

	@Override
	public List<SSOApplication> getListPaged(SSOApplication app)throws Exception {
		return sSOApplicationDao.getListPaged(app);
	}

	
	public int getListPagedCount(SSOApplication app) throws Exception{
		return sSOApplicationDao.getListPagedCount(app);
	}

	@Override
	public List<SSOApplication> getAllEnabledAppList() throws Exception {
		return sSOApplicationDao.getAllEnabledAppList();
	}

	@Override
	public int getMaxSortNum() throws Exception {
		return sSOApplicationDao.getMaxSortNum();
	}

	@Override
	public int getMaxOrder() throws SQLException {
		return sSOApplicationDao.getMaxOrder();
	}
	
}
