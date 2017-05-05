package com.boyuyun.base.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.DictionaryItem;


public interface DictionaryItemDao {
	/**
	 * 根据外键删除记录
	 * @param dictionaryItem
	 */
	void deleteByForeignKey(DictionaryItem dictionaryItem)throws SQLException;
	
	/**
	 * 校验字典值和字典名
	 * @param dictionary
	 * @return
	 */
	Integer validateDictionaryItem(DictionaryItem dictionaryItem)throws SQLException;
	
	
	boolean insert(DictionaryItem dictionaryItem)throws SQLException;
	boolean update(DictionaryItem dictionaryItem)throws SQLException;
	boolean delete(DictionaryItem dictionaryItem)throws SQLException;
	List<DictionaryItem> getListNonePaged(DictionaryItem dictionaryItem)throws SQLException;
	DictionaryItem get(int id)throws SQLException;
	public List<DictionaryItem> getDictionaryItemList(String code,String schoolId)throws SQLException;
}