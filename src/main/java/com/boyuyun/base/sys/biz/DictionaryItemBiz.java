package com.boyuyun.base.sys.biz;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.DictionaryItem;

public interface DictionaryItemBiz {
	/**
	 * 根据外键删除记录
	 * @param dictionaryItem
	 */
	public void deleteByForeignKey(DictionaryItem dictionaryItem)throws Exception;

	/**
	 * 校验前台字典名,跟字典值
	 * @param dictionary
	 * @return
	 */
	public boolean validateDictionaryItem(DictionaryItem dictionaryItem)throws Exception;	
	public boolean add(DictionaryItem dictionaryItem)throws Exception;
	public boolean update(DictionaryItem dictionaryItem)throws Exception;
	public boolean delete(DictionaryItem dictionaryItem)throws Exception;
	public List<DictionaryItem> getListNonePaged(DictionaryItem dictionaryItem)throws Exception;
	public DictionaryItem get(int id)throws Exception;
	
	public List<DictionaryItem> getDictionaryItemList(String code,String schoolId)throws SQLException;
	
	public String[] getArr(String code)  throws Exception;
}
