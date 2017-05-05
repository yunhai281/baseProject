package com.boyuyun.base.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.OperateLog;



public interface OperateLogDao {
    boolean insert(OperateLog operateLog)throws SQLException;
	List<OperateLog> getListPaged(OperateLog operateLog) throws Exception;
	int getListPagedCount(OperateLog operateLog) throws Exception;
}