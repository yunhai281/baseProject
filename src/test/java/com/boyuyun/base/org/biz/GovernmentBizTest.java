package com.boyuyun.base.org.biz;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.org.entity.Government;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GovernmentBizTest extends SpringJunitTest {
	
	private static Logger logger=Logger.getLogger(GovernmentBizTest.class);
	
	@Resource
	private GovernmentBiz govServices;

	@Test
	public void testB_Get() {
		Government gov=null;
		try {
			gov=govServices.get("123");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(gov!=null);
		
	}

	@Test
	public void testAdd() {
		Government gov=new Government();
		gov.setId("123");
		gov.setLevelType("3");
		gov.setName("123");
		gov.setParentId("004502323");
		gov.setAreaId("110108");
		boolean tag=true;
		try {
			govServices.add(gov);
		} catch (SQLException e) {
			tag=false;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(tag);
		
	}

	@Test
	public void testY_Update() {
		Government gov=null;
		try {
			gov=govServices.get("123");
			gov.setName("222");
			govServices.update(gov);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue("222".equals(gov.getName()));
		
	}

	@Test
	public void testZ_Delete() {
		Government gov=null;
		try {
			gov=govServices.get("123");
			govServices.delete(gov);
			gov=govServices.get("123");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(gov==null);
	}

	@Test
	public void testListRoot() {
		List<Government> list=null;
		try {
			list=govServices.listRoot();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetByParentId() {
		List<Government> list=null;
		try {
			list=govServices.getByParentId("111");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list != null);
	}

	@Test
	public void testValidateGov() {
		Government gov=null;
		List<Government> list=null;
		try {
			gov=govServices.get("123");
			list=govServices.validateGov(gov);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testSelectFullById() {
		Government gov=null;
		try {
			gov=govServices.selectFullById("123");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(gov!=null);
	}

	@Test
	public void testGetDynamicGovernmentTree() {
		List list1=null;
		List list2=null;
		try {
			list1=govServices.getDynamicGovernmentTree("004502323");
			list2=govServices.getDynamicGovernmentTree("");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list1!=null && list2!=null);
	}

	@Test
	public void testB_SearchByName() {
		List<Government> list=null;
		try {
			list=govServices.searchByName("2");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
		
	}

//	@Test
//	public void testInsertImportExcel() {
//		fail("Not yet implemented");
//	}

}
