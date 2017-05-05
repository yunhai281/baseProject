package com.boyuyun.base.org.biz;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchoolClassBizTest extends SpringJunitTest {
	
	private static Logger logger=Logger.getLogger(SchoolClassBizTest.class);
	
	@Resource
	private SchoolClassBiz scServices;
	
	@Test
	public void testAdd() {
		SchoolClass sc=new SchoolClass();
		sc.setId("123");
		sc.setName("123");
		sc.setCreateTime(new Date());
		sc.setGradeId("0a574887447b48628ae0de3186f31409");
		sc.setSchoolId("080754bbb56c472abd5863f7e3db4574");
		sc.setStatus(10098);
		sc.setSortNum(1);
		boolean tag=true;
		try {
			scServices.add(sc);
		} catch (SQLException e) {
			tag=false;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(tag);
	}

	@Test
	public void testY_Update() {
		SchoolClass sc=null;
		try {
			sc=scServices.get("123");
			sc.setName("222");
			scServices.update(sc);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue("222".equals(sc.getName()));
	}

	@Test
	public void testZ_Delete() {
		 SchoolClass sc=null;
		 try {
			sc=scServices.get("123");
			scServices.delete(sc);
			sc=scServices.get("123");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(sc==null);
	}

//	@Test
//	public void testDeleteAll() {
//		fail("Not yet implemented");
//	}

//	@Test
//	public void testGetListNonePaged() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetListPaged() {
		List<SchoolClass> list=null;
		try {
			SchoolClass sc=scServices.get("123");
			list=scServices.getListPaged(sc);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetListPagedCount() {
		int i=0;
		try {
			SchoolClass sc=scServices.get("123");
			i=scServices.getListPagedCount(sc);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}

	@Test
	public void testGet() {
		SchoolClass sc=null;
		try {
			sc=scServices.get("123");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(sc!=null);
	}

	@Test
	public void testGetSchoolClassListBy() {
		List<SchoolClass> list=null;
		try {
			list=scServices.getSchoolClassListBy("080754bbb56c472abd5863f7e3db4574", Stage.小学, "0a574887447b48628ae0de3186f31409");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetMaxSortNum() {
		int i=0;
		try {
			i=scServices.getMaxSortNum();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i>0);
	}

}
