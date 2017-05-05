package com.boyuyun.base.sys;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.common.junit.SpringJunitTest;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AreaBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(AreaBizTest.class);
	@Resource
	private AreaBiz areaService;
	
	@Test
	public void testGetListByParent() {
		Area area= null;
		List<Area> list=null;
		try {
			list=areaService.getListByParent("1231");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			 Assert.assertTrue(list!=null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetParent() {
		Area area= null; 
		try {
			area=areaService.getParent("1231");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			 Assert.assertTrue(areaService.get("1231").getId().equals("1231"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelectLinkByPrimaryKey() {
		Area area= null; 
		try {
			area=areaService.selectLinkByPrimaryKey("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			 Assert.assertTrue(area!=null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testAdd() {
		Area area = new Area();
		area.setId("123");
		area.setName("123");
		area.setAvailable(true);
		area.setCode("123");
		area.setDimension(123.123);
		area.setLongitude(123.123);
		area.setLevelType("2");

		Area area1 = new Area();
		area1.setId("1231");
		area1.setName("1231");
		area1.setAvailable(true);
		area1.setCode("1231");
		area1.setDimension(123.123);
		area1.setLongitude(123.123);
		area1.setLevelType("3");
		area1.setParentId("123");
		Exception ex = null;
		try {
			areaService.add(area);
			areaService.add(area1);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testC_Update() {
		Area area= new Area();
		try {
			area=areaService.get("123");
			area.setName("321");
			areaService.update(area);
			area=areaService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			Assert.assertTrue(area.getName().equals("321"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testZ_Delete() {
		Area area=null;
		try {
			area = areaService.get("1231");
			areaService.delete(area);
			area = areaService.get("1231");
			area = areaService.get("123");
			areaService.delete(area);
			area = areaService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		 Assert.assertFalse(area != null );
	}

	@Test
	public void testGetListNonePaged() {
		List<Area> list =null;
		try {
			list=areaService.getListNonePaged(new Area());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			Assert.assertTrue(list!=null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testB_Get() {
		Area area = null;
		try {
			area=areaService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(area.getName().equals("123"));
	}

}
