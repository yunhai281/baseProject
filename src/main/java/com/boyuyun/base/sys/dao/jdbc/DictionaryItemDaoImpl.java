package com.boyuyun.base.sys.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DictionaryItemDaoImpl extends BaseDAO implements DictionaryItemDao {

	@Override
	public void deleteByForeignKey(DictionaryItem dictionaryItem)throws SQLException {
		String sql ="DELETE FROM DICTIONARY_ITEM where dictionaryId = ?";
		this.executeUpdate(sql, new Object[]{dictionaryItem.getDictionaryId()});
	}

	@Override
	public Integer validateDictionaryItem(DictionaryItem dictionaryItem)throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM DICTIONARY_ITEM where 1=1 ");
		List param = new ArrayList();
		if(dictionaryItem.getDictionaryId()>0){
			sql.append(" AND DictionaryId = ?");
			param.add(dictionaryItem.getDictionaryId());
		}
		if(!Strings.isNullOrEmpty(dictionaryItem.getName())){
			sql.append(" AND NAME = ?");
			param.add(dictionaryItem.getName());
		}
		if(!Strings.isNullOrEmpty(dictionaryItem.getValue())){
			sql.append(" AND VALUE = ?");
			param.add(dictionaryItem.getValue());			
		}
		if(dictionaryItem.getId()>0){
			sql.append(" AND Id != ?");
			param.add(dictionaryItem.getId());			
		}		
		return this.executeCount(sql.toString(),param.toArray());
	}
	private int getNextId()throws SQLException{
		String sql = "select (IFNULL(max(id),9999)+1) as id from dictionary_Item ";
		return this.executeCount(sql);
	} 

	@Override
	public boolean insert(DictionaryItem dictionaryItem)throws SQLException {
		String  sql = "insert into DICTIONARY_ITEM (ID,DICTIONARYID, NAME, VALUE, REMARK, NUM, SORTNUM,SCHOOLID)"+
                                                         "values (?,?,?,?,?,?,?,?)";
		dictionaryItem.setId(this.getNextId());
		Object[] Param ={
			    dictionaryItem.getId(),
				dictionaryItem.getDictionaryId(),
				dictionaryItem.getName(),
				dictionaryItem.getValue(),
				dictionaryItem.getRemark(),
				dictionaryItem.getNum(),
				dictionaryItem.getSortNum(),
				dictionaryItem.getSchoolId()
		};
		return this.executeUpdate(sql, Param)>0;
	}

	@Override
	public boolean update(DictionaryItem dictionaryItem)throws SQLException {
		String sql = "update DICTIONARY_ITEM "+
	               "set NAME = ?,VALUE = ?,REMARK = ?,NUM = ?,SORTNUM = ?,SCHOOLID = ?,DICTIONARYID= ?   where ID = ?";
		Object[] param = {
				dictionaryItem.getName(),
				dictionaryItem.getValue(),
				dictionaryItem.getRemark(),
				dictionaryItem.getNum(),
				dictionaryItem.getSortNum(),
				dictionaryItem.getSchoolId(),
				dictionaryItem.getDictionaryId(),
				dictionaryItem.getId()
		};
		return this.executeUpdate(sql, param)>0;
	}

	@Override
	public boolean delete(DictionaryItem dictionaryItem)throws SQLException {
		String sql ="delete from DICTIONARY_ITEM where ID = ?";
		return this.executeUpdate(sql, new Object[]{dictionaryItem.getId()})>0;
	}

	@Override
	public List<DictionaryItem> getListNonePaged(DictionaryItem dictionaryItem)throws SQLException {
		StringBuffer sql =new StringBuffer("SELECT ID,DICTIONARYID, NAME, VALUE, REMARK, NUM, SORTNUM, SCHOOLID FROM DICTIONARY_ITEM where 1=1");
		List param = new ArrayList();
		if(dictionaryItem.getDictionaryId()>0){
			sql.append(" AND DICTIONARYID = ?");
			param.add(dictionaryItem.getDictionaryId());
		}
		if(!Strings.isNullOrEmpty(dictionaryItem.getSchoolId())){
			sql.append(" AND SCHOOLID = ?");
			param.add(dictionaryItem.getSchoolId());		
		}	
		if(!Strings.isNullOrEmpty(dictionaryItem.getValue())){
			sql.append(" AND VALUE = ?");
			param.add(dictionaryItem.getValue());
		}
		ResultSetHandler handler = new BeanListHandler(DictionaryItem.class);
		return this.executeQuery(sql.toString(), param.toArray(),handler);
	}

	@Override
	public DictionaryItem get(int id)throws SQLException {
		String sql ="select t1.ID, t1.DICTIONARYID, t2.NAME AS DICTIONARYNAME, t1.NAME, t1.VALUE, t1.REMARK, t1.NUM, t1.SORTNUM, t1.SCHOOLID,t3.NAME AS SCHOOL_NAME from DICTIONARY_ITEM t1 left join DICTIONARY t2 on t1.DICTIONARYID = t2.ID left join SCHOOL t3 on t1.SCHOOLID = t3.ID where t1.ID = ?";
		ResultSetHandler handler = new BeanHandler(DictionaryItem.class);
		return (DictionaryItem)this.executeQueryObject(sql, new Object[]{id}, handler);
	}
	
	
	public List<DictionaryItem> getDictionaryItemList(String code,String schoolId)throws SQLException {
		StringBuffer sql =new StringBuffer("SELECT DICTIONARY_ITEM.ID,dictionaryId, DICTIONARY_ITEM.name, DICTIONARY_ITEM.value, DICTIONARY_ITEM.REMARK, DICTIONARY_ITEM.NUM, DICTIONARY_ITEM.SORTNUM, DICTIONARY_ITEM.SCHOOLID  FROM DICTIONARY_ITEM ");
		sql.append(" left join dictionary on dictionary.id=DICTIONARY_ITEM.dictionaryId ");
		sql.append(" where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(schoolId)){
			sql.append(" AND DICTIONARY_ITEM.SCHOOLID = ?");
			param.add(schoolId);		
		}	
		if(!Strings.isNullOrEmpty(code)){
			sql.append(" AND dictionary.code = ?");
			param.add(code);
		}
		ResultSetHandler handler = new BeanListHandler(DictionaryItem.class);
		return this.executeQuery(sql.toString(), param.toArray(),handler);
	}

}
