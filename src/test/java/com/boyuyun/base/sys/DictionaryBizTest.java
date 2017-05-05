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
import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.base.sys.biz.DictionaryBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DictionaryBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private DictionaryBiz diconaryService;
	@Test
	public void testE_ValidateDictionary() {
		Dictionary dictionary= null;
		Boolean flag=false;
		try {
			dictionary.setName("123");
			
			flag=diconaryService.validateDictionary(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(!flag);
	}

	@Test
	public void testAdd() { 
		Dictionary diconary = new Dictionary();
		diconary.setId(123);
		diconary.setName("123");
		diconary.setCode("123");
		diconary.setSchooldiy(false);
		diconary.setEditable(false);
		Exception ex = null;
		try {
			diconaryService.add(diconary);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testC_Update() {
		Dictionary disDictionary= new Dictionary();
		try {
			disDictionary=diconaryService.get(10028);
			disDictionary.setName("321");
			diconaryService.update(disDictionary);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			Assert.assertTrue(diconaryService.get(123).getName().equals("321"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testG_Delete() {
		Dictionary dictionary= new Dictionary();
		try {
			dictionary=diconaryService.get(10028);
			diconaryService.delete(dictionary);
			dictionary=diconaryService.get(10028);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(dictionary==null);
	}

	@Test
	public void testF_GetListNonePaged() {
		List<Dictionary> list =null;
		try {
			Dictionary dictionary= diconaryService.get(10028);
			list = diconaryService.getListNonePaged(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testB_Get() {
		Dictionary disDictionary= null;
		try {
			disDictionary=diconaryService.get(10028);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(disDictionary.getId()==10028);
	}

	@Test
	public void testD_GetByCode() {
		Dictionary disDictionary= new Dictionary();
		try {
			disDictionary=diconaryService.getByCode("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(disDictionary.getCode().equals("123"));
	}

}
