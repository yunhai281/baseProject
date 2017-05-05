package com.boyuyun.base.sys.biz;

import java.util.List;

import com.boyuyun.base.sys.entity.Dictionary;

public interface DictionaryBiz {
	
	/**
	 * 校验前台字典名,跟字典值
	 * @param dictionary
	 * @return
	 * @throws Exception 
	 */
	public boolean validateDictionary(Dictionary dictionary)throws Exception;
	public boolean add(Dictionary dictionary)throws Exception;
	public boolean update(Dictionary dictionary)throws Exception;
	public boolean delete(Dictionary dictionary)throws Exception;
	public List<Dictionary> getListNonePaged(Dictionary dictionary)throws Exception;
	public Dictionary get(int id)throws Exception;
	public Dictionary getByCode(String code)throws Exception;
}
