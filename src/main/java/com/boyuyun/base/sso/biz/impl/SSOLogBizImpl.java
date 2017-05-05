package com.boyuyun.base.sso.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.sso.biz.SSOLogBiz;
import com.boyuyun.base.sso.dao.SSOLogDao;
import com.boyuyun.base.sso.entity.SSOLog;
@Service
public class SSOLogBizImpl implements SSOLogBiz {
	@Resource
	private SSOLogDao sSOLogDao;
	@Override
	public List<SSOLog> getListPaged(SSOLog log) throws Exception {
		return sSOLogDao.getListPaged(log);
	}

	@Override
	public int getListPagedCount(SSOLog log) throws Exception {
		return sSOLogDao.getListPagedCount(log);
	}

}
