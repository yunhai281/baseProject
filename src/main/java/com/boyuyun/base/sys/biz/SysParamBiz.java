package com.boyuyun.base.sys.biz;

import java.util.List;
import java.util.Map;

import com.boyuyun.base.sys.entity.SysParam;

public interface SysParamBiz  {
	public boolean save(SysParam param) throws Exception;
	public List<SysParam> getAll() throws Exception;
	public Map getAllAsMap()throws Exception;
}
