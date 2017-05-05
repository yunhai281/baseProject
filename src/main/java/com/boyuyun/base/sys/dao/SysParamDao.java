package com.boyuyun.base.sys.dao;

import java.util.List;

import com.boyuyun.base.sys.entity.SysParam;

public interface SysParamDao {
	public void insert(List<SysParam> list) throws Exception;
	public List<SysParam> getAll() throws Exception;
	public boolean delete() throws Exception;
	public SysParam get(SysParam param) throws Exception;
}