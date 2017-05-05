package com.boyuyun.base.org.biz;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.common.junit.SpringJunitTest;

/**
 * 本类单方法测试
 * @author Administrator
 *
 */
public class SchoolGradeBizTest extends SpringJunitTest {
	
	private static Logger logger=Logger.getLogger(SchoolGradeBizTest.class);
	
	private SchoolGradeBiz sgServices;

//	@Test
//	public void testGetListNonePaged() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetListPaged() {
		List<SchoolGrade> list=null;
		try {
			SchoolGrade sg=sgServices.get("123");
			list=sgServices.getListPaged(sg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetListPagedCount() {
		int i=0;
		try {
			SchoolGrade sg=sgServices.get("123");
			i=sgServices.getListPagedCount(sg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue(i>0);
	}

	@Test
	public void testAdd() {
		SchoolGrade sg=new SchoolGrade();
		sg.setId("123");
		sg.setSysGradeId("2828921d3261427f8697a058595a3ed5");
		sg.setSchoolId("080754bbb56c472abd5863f7e3db4574");
		sg.setName("123");
		sg.setSortNum(1);
		sg.setCreateTime(new Date());
		boolean tag=true;
		try {
			sgServices.add(sg);
		} catch (Exception e) {
			tag=false;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(tag);
	}

	@Test
	public void testY_Update() {
		SchoolGrade sg=null;
		try {
			sg=sgServices.get("123");
			sg.setName("222");
			sgServices.update(sg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue("222".equals(sg.getName()));
	}

	@Test
	public void testZ_Delete() {
		SchoolGrade sg=null;
		try {
			sg=sgServices.get("123");
			sgServices.delete(sg);
			sg=sgServices.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(sg==null);
	}

//	@Test
//	public void testDeleteAll() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGet() {
		SchoolGrade sg=null;
		try {
			sg=sgServices.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(sg!=null);
	}

//	@Test
//	public void testGetSchoolGradeBySchoolAndStageStringStage() {
//		
//	}
//
//	@Test
//	public void testGetSchoolGradeBySchoolAndStageString() {
//	}

	@Test
	public void testGetListByOrgClassCount() {
		boolean tag=true;
		try {
			SchoolGrade sg=sgServices.get("123");
			tag=sgServices.getListByOrgClassCount("287f73d4818a4773bae42832a5c5023b");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(!tag);
	}

	@Test
	public void testGetMaxSortNum() {
		int i=0;
		try {
			i=sgServices.getMaxSortNum();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i>0);
	}

}
