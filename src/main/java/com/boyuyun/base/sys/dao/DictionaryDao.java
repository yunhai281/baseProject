package com.boyuyun.base.sys.dao;

import java.sql.SQLException;
import java.util.List;

import com.boyuyun.base.sys.entity.Dictionary;


public interface DictionaryDao {
	/**
	 * 校验字典值和字典名
	 * @param dictionary
	 * @return
	 */
	Integer validateDictionary(Dictionary dictionary)throws SQLException ;
	boolean insert(Dictionary dictionary)throws SQLException ;
	boolean update(Dictionary dictionary)throws SQLException ;
	boolean delete(Dictionary dictionary)throws SQLException ;
	List<Dictionary> getListNonePaged(Dictionary dictionary)throws SQLException ;
	Dictionary get(int id)throws SQLException ;
	Dictionary getByCode(String code)throws SQLException ;	
}