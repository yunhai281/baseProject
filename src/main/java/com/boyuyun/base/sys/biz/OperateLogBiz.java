package com.boyuyun.base.sys.biz;

import java.util.List;
import java.util.List;

import com.boyuyun.base.sso.entity.SSOLog;
import com.boyuyun.base.sys.entity.OperateLog;


public interface OperateLogBiz {
	public boolean add(OperateLog operateLog)throws Exception;
	public List<OperateLog> getListPaged(OperateLog operateLog)throws Exception;
	public int getListPagedCount(OperateLog operateLog)throws Exception;
	public boolean add(List logs)throws Exception;
}
