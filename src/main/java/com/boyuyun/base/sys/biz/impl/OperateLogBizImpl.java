package com.boyuyun.base.sys.biz.impl;

import java.util.List;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sys.biz.OperateLogBiz;
import com.boyuyun.base.sys.dao.OperateLogDao;
import com.boyuyun.base.sys.entity.OperateLog;
@Service
public class OperateLogBizImpl implements OperateLogBiz {

	@Resource
	private OperateLogDao operateLogDao;
	
	@Transactional
	public boolean add(OperateLog operateLog) throws Exception {
		
		return operateLogDao.insert(operateLog);
	}

	@Override
	public List<OperateLog> getListPaged(OperateLog operateLog)
			throws Exception {
		return this.operateLogDao.getListPaged(operateLog);
	}

	@Override
	public int getListPagedCount(OperateLog operateLog) throws Exception {
		return this.operateLogDao.getListPagedCount(operateLog);
	}

	@Override
	@Transactional
	public boolean add(List logs) throws Exception {
		if(logs==null)return false;
		boolean res = true;
		for (Iterator iterator = logs.iterator(); iterator.hasNext();) {
			OperateLog log = (OperateLog) iterator.next();
			res&=operateLogDao.insert(log);
		}
		return res;
	}

}
