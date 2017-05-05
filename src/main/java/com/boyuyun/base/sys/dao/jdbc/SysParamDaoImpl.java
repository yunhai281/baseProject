package com.boyuyun.base.sys.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.SysParamDao;
import com.boyuyun.base.sys.entity.SysParam;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysParamDaoImpl extends BaseDAO implements SysParamDao {

	@Override
	public void insert(List<SysParam> list) throws Exception{
		String sql = "insert into SYS_PARAM (paramKey, paramValue)values(?,?)";
		int length=list.size();
		Object [][] param = new Object [length][2] ;
		for (int i=0;i<length;i++){
			SysParam po=list.get(i);
			param[i][0]=po.getParamKey();
			param[i][1]=po.getParamValue();
		}
		super.executeBatchUpdate(sql, param);
	}

	public boolean delete()throws Exception {
		String sql = "delete from sys_param ";
		int i = this.executeUpdate(sql, new String[]{});
		return i>=0;
	}

	@Override
	public List<SysParam> getAll()throws Exception {
		String sql = "select * from sys_param ";
		ResultSetHandler handler = new BeanListHandler(SysParam.class);
		return this.executeQuery(sql,new String[]{},handler);
	}

	@Override
	public SysParam get(SysParam param) throws Exception {
		String sql = "select * from sys_param where 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(!Strings.isNullOrEmpty(param.getParamKey())&&!Strings.isNullOrEmpty(param.getParamValue())){
			sql+=" and paramKey = ? and paramValue = ? ";
			paramList.add(param.getParamKey());
			paramList.add(param.getParamValue());
			
		}
		ResultSetHandler handler = new BeanListHandler(SysParam.class);
		return (SysParam) this.executeQueryObject(sql,paramList.toArray(),handler);
	}
	

}
