package com.boyuyun.base.sso.biz;

import java.util.List;

import com.boyuyun.base.sso.entity.SSOLog;

public interface SSOLogBiz {
	public List<SSOLog> getListPaged(SSOLog log)throws Exception;
	public int getListPagedCount(SSOLog log)throws Exception;
}
