package com.boyuyun.base.sys.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.DictionaryDao;
import com.boyuyun.base.sys.entity.Dictionary;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DictionaryDaoImpl extends BaseDAO implements DictionaryDao {

	
	@Override
	public Integer validateDictionary(Dictionary dictionary)throws SQLException  {
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM DICTIONARY where 1=1");
		List param = new ArrayList();
		if(dictionary.getId()>0){
			sql.append(" and Id!=? ");
			param.add(dictionary.getId());
		}
		if(!Strings.isNullOrEmpty(dictionary.getCode())){
			sql.append(" and CODE=? ");
			param.add(dictionary.getCode());
		}
		if(!Strings.isNullOrEmpty(dictionary.getName())){
			sql.append(" and NAME=? ");
			param.add(dictionary.getName());
		}		
		return this.executeCount(sql.toString(), param.toArray());
	}
	/**
	 * 获取下一个id
	 * @return
	 * @throws SQLException
	 */
	private int getNextId()throws SQLException{
		String sql = "select (IFNULL(max(id),9999)+1) as id from dictionary ";
		return this.executeCount(sql);
	}
	@Override
	public boolean insert(Dictionary dictionary)throws SQLException  {
		String sql ="    insert into DICTIONARY (ID, CODE, NAME, REMARK, EDITABLE, SCHOOLDIY)"+
                                                "values (?, ?, ?, ?, ?, ?)";
		dictionary.setId(this.getNextId());
		Object[] param = {
				dictionary.getId(),
				dictionary.getCode(),
				dictionary.getName(),
				dictionary.getRemark(),
				dictionary.isEditable(),
				dictionary.isSchooldiy()
		};
		return this.executeUpdate(sql, param)>0;
	}

	@Override
	public boolean update(Dictionary dictionary)throws SQLException  {
		String sql = "update DICTIONARY set CODE = ?, NAME = ?,REMARK = ?,EDITABLE = ?,SCHOOLDIY = ? where ID = ?";
		Object [] param= {
				dictionary.getCode(),
				dictionary.getName(),
				dictionary.getRemark(),
				dictionary.isEditable(),
				dictionary.isSchooldiy(),
				dictionary.getId()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(Dictionary dictionary)throws SQLException  {
		String sql ="    delete from DICTIONARY where ID = ?";
		int i = this.executeUpdate(sql, new Object[]{dictionary.getId()});
		return false;
	}

	@Override
	public List<Dictionary> getListNonePaged(Dictionary dictionary)throws SQLException  {
		StringBuffer sql = new StringBuffer("select ID, CODE, NAME, REMARK, EDITABLE, SCHOOLDIY from DICTIONARY where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(dictionary.getCode())){
			sql.append(" AND CODE LIKE concat(concat('%',?),'%') ");
			param.add(dictionary.getCode());
		}
		if(!Strings.isNullOrEmpty(dictionary.getName())){
			sql.append(" OR NAME LIKE concat(concat('%',?),'%')");
			param.add(dictionary.getName());		
		}	
		ResultSetHandler handler = new BeanListHandler(Dictionary.class);
		return this.executeQuery(sql.toString(), param.toArray(), handler);
	}

	@Override
	public Dictionary get(int id)throws SQLException  {
		String sql = "select ID, CODE, NAME, REMARK, EDITABLE, SCHOOLDIY from DICTIONARY where ID = ?";
		ResultSetHandler hanlder = new BeanHandler(Dictionary.class);
		return (Dictionary)this.executeQueryObject(sql, new Object[]{id}, hanlder);
	}

	@Override
	public Dictionary getByCode(String code)throws SQLException  {
		String sql = "select ID, CODE, NAME, REMARK, EDITABLE, SCHOOLDIY from DICTIONARY where code = ?";
		ResultSetHandler hanlder = new BeanHandler(Dictionary.class);
		return (Dictionary)this.executeQueryObject(sql, new Object[]{code}, hanlder);		
	}

}
