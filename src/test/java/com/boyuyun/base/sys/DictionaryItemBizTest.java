package com.boyuyun.base.sys;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.CourseBizTest;
import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DictionaryItemBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private DictionaryItemBiz dictionaryItemService;

	@Test
	public void testE_ValidateDictionaryItem() {
		Boolean flag=false;
		try {
			DictionaryItem dictionaryItem =new DictionaryItem();
			dictionaryItem.setName("123");
			flag = dictionaryItemService.validateDictionaryItem(dictionaryItem);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(!flag);
	}

	@Test
	public void testAdd() {
		DictionaryItem dictionaryItem = new DictionaryItem();
		dictionaryItem.setId(123);
		dictionaryItem.setName("123") ;
		dictionaryItem.setValue("123"); 
		dictionaryItem.setDictionaryId(10027);
		Exception ex = null;
		try {
			dictionaryItemService.add(dictionaryItem);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testF_Update() {
		DictionaryItem dictionaryItem= new DictionaryItem();
		try {
			dictionaryItem=dictionaryItemService.get(10028);
			dictionaryItem.setName("321");
			dictionaryItemService.update(dictionaryItem);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			Assert.assertTrue(dictionaryItemService.get(123).getName().equals("321"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testH_Delete() {
		DictionaryItem dictionaryItem= new DictionaryItem();
		try {
			dictionaryItem=dictionaryItemService.get(10028);
			dictionaryItemService.delete(dictionaryItem);
			dictionaryItem=dictionaryItemService.get(10028);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(dictionaryItem==null);
	}

	@Test
	public void testD_GetListNonePaged() {
		List<DictionaryItem> list =null;
		try {
			DictionaryItem dictionaryItem= dictionaryItemService.get(10119);
			list = dictionaryItemService.getListNonePaged(dictionaryItem);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testB_Get() {
		DictionaryItem diconaryiItem= null;
		try {
			diconaryiItem=dictionaryItemService.get(10119);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(diconaryiItem.getId()==10119);
	}

	@Test
	public void testC_GetDictionaryItemList() {
		List<DictionaryItem> list =null;
		try { 
			list = dictionaryItemService.getDictionaryItemList("123", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testG_GetArr() {
		String[] string= new String[]{};
		try { 
			string = dictionaryItemService.getArr("PositionalTitle");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(string.length>0);
	}

}
