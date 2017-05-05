package com.boyuyun.base.sys.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.dao.DictionaryDao;
import com.boyuyun.base.sys.entity.Dictionary;
@Service
public class DictionaryBizImpl implements DictionaryBiz {

    @Resource
    DictionaryDao dictionaryDao;

	@Override
	public boolean validateDictionary(Dictionary dictionary)throws Exception {
		Integer val = dictionaryDao.validateDictionary(dictionary); 
		return (val!=null && val>0)?false:true;
	}

	@Transactional
	public boolean add(Dictionary dictionary)throws Exception {
		return dictionaryDao.insert(dictionary);
	}

	@Transactional
	public boolean update(Dictionary dictionary)throws Exception {
		return dictionaryDao.update(dictionary);
	}

	@Transactional
	public boolean delete(Dictionary dictionary)throws Exception {
		return dictionaryDao.delete(dictionary);
	}

	@Override
	public List<Dictionary> getListNonePaged(Dictionary dictionary)throws Exception {
		return dictionaryDao.getListNonePaged(dictionary);
	}

	@Override
	public Dictionary get(int id)throws Exception {
		return dictionaryDao.get(id);
	}

	@Override
	public Dictionary getByCode(String code)throws Exception {
		return dictionaryDao.getByCode(code);
	}
    

}
