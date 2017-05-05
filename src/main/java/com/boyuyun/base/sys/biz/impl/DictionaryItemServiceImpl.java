package com.boyuyun.base.sys.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.entity.DictionaryItem;
@Service
public class DictionaryItemServiceImpl implements DictionaryItemBiz {

    @Resource
    private DictionaryItemDao dictionaryItemDao;

	@Transactional
	public void deleteByForeignKey(DictionaryItem dictionaryItem)throws Exception {
		this.dictionaryItemDao.deleteByForeignKey(dictionaryItem);
	}

	@Override
	public boolean validateDictionaryItem(DictionaryItem dictionaryItem)throws Exception {
		Integer val = dictionaryItemDao.validateDictionaryItem(dictionaryItem);
		return (val!=null&&val>0)?false:true;
	}

	@Transactional
	public boolean add(DictionaryItem dictionaryItem)throws Exception {
		
		return dictionaryItemDao.insert(dictionaryItem);
	}

	@Transactional
	public boolean update(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemDao.update(dictionaryItem);
	}

	@Transactional
	public boolean delete(DictionaryItem dictionaryItem)throws Exception {
		
		return dictionaryItemDao.delete(dictionaryItem);
	}

	@Override
	public List<DictionaryItem> getListNonePaged(DictionaryItem dictionaryItem)throws Exception {
		return dictionaryItemDao.getListNonePaged(dictionaryItem);
	}

	@Override
	public DictionaryItem get(int id)throws Exception {
		return dictionaryItemDao.get(id);
	}

	@Override
	public List<DictionaryItem> getDictionaryItemList(String code, String schoolId) throws SQLException {
		return dictionaryItemDao.getDictionaryItemList(code, schoolId);
	}
	

	/**
	 * @Description 取得字典来设置数据有效性
	 * @author jms
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String[] getArr(String code)  throws Exception{
		List<DictionaryItem> list=dictionaryItemDao.getDictionaryItemList(code, "");
		String[] strArr= new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			DictionaryItem dictionaryItem = list.get(i);
			strArr[i] =dictionaryItem.getName();
			
		}
		return strArr;
	}

}
