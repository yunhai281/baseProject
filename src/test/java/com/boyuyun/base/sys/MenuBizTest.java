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
import com.boyuyun.base.sys.biz.MenuBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.Menu;
import com.boyuyun.common.junit.SpringJunitTest;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private MenuBiz menuService;
	
	@Test
	public void testC_GetTopMenuListWithChild() {
		List<Menu> menus = null;
		try {
			menus = menuService.getTopMenuListWithChild();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(menus!=null);
	}

	@Test
	public void testD_GetMenuListByParentId() {
		List<Menu> menus = null;
		try {
			menus = menuService.getMenuListByParentId("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(menus!=null);
	}

	@Test
	public void testAdd() {
		Menu menu = new Menu();
		menu.setId("123");
		menu.setName("123");  
		menu.setLevelType(1);
		Menu menu1 = new Menu();
		menu1.setId("1231");
		menu1.setName("1231");  
		menu1.setLevelType(1);
		menu1.setParent(menu);
		Exception ex = null;
		try {
			menuService.add(menu);
			menuService.add(menu1);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testE_Update() {
		Menu menu =null;
		try {
			menu = menuService.get("1231");
			menuService.delete(menu);
			menu = menuService.get("1231");
			menu = menuService.get("123");
			menuService.delete(menu);
			menu = menuService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		 Assert.assertFalse(menu != null );
	}

	@Test
	public void testF_Delete() {
		Menu menu =null;
		try {
			menu=menuService.get("123");
			menuService.delete(menu);
			menu=menuService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(menu==null);
	}

	@Test
	public void testB_Get() {
		Menu menu = new Menu();
		try {
			menu=menuService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(menu.getName().equals("123"));
	}

}
