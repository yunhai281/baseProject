package com.boyuyun.base.org.biz;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.org.entity.School;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SchoolBizTest extends SpringJunitTest {

	private static Logger logger=Logger.getLogger(SchoolBizTest.class);
	
	@Resource
	private SchoolBiz schoolServices;
	@Test
	public void testAdd() {
		School school=new School();
		school.setId("123");
		school.setName("123");
		school.setParentId("042003ea120946698e61721b839baa90");
		school.setGovernmentId("000026833");
		school.setAreaId("110105");
		school.setBuildDate(new Date());
		school.setSchoolBoardType(0);
		school.setSchoolStation(0);
		school.setSchoolType(0);
		school.setSystemType(0);
		school.setJianpin("xx");
		school.setPinyin("haha");
		school.setSerialNumber("wuxian");
		school.setCode("666");
		boolean tag=true;
		try {
			schoolServices.add(school);
		} catch (Exception e) {
			tag=false;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(tag);
	}

	@Test
	public void testY_Update() {
		School school=null;
		try {
			school=schoolServices.get("123");
			school.setName("222");
			schoolServices.update(school);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue("222".equals(school.getName()));;
	}

	@Test
	public void testZ_Delete() {
		School school=null;
		try {
			school=schoolServices.get("123");
			schoolServices.delete(school);
			school=schoolServices.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(school==null);
	}

	@Test
	public void testGet() {
		School school=null;
		try {
			school=schoolServices.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(school!=null);
	}

	@Test
	public void testGetListPagedCount() {
		int i=0;
		try {
			School school=schoolServices.get("123");
			i=schoolServices.getListPagedCount(school);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}

	@Test
	public void testGetListPaged() {
		List<School> list=null;
		try{
			School school=schoolServices.get("123");
			list=schoolServices.getListPaged(school);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testSelectAll() {
		List<School> list=null;
		try {
			list=schoolServices.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testSelectAllParent() {
		List<School> list=null;
		try {
			list=schoolServices.selectAllParent();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetByParentId() {
		List<School> list=null;
		try {
			list=schoolServices.getByParentId("042003ea120946698e61721b839baa90");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testGetByGovernmentId() {
		List<School> list=null;
		try {
			list=schoolServices.getByGovernmentId("000026833");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

//	@Test
//	public void testGetDynamicGovernmentAndSchoolTree() {
//		
//	}

	@Test
	public void testGetDynamicGovernmentSchoolAndGradeTree() {
		List list1=null;
		List list2=null;
		List list3=null;
		List list4=null;
		try {
			list1=schoolServices.getDynamicGovernmentAndSchoolTree("government", "042003ea120946698e61721b839baa90");
			list2=schoolServices.getDynamicGovernmentAndSchoolTree("school", "042003ea120946698e61721b839baa90");
			list3=schoolServices.getDynamicGovernmentAndSchoolTree("grade", "042003ea120946698e61721b839baa90");
			list4=schoolServices.getDynamicGovernmentAndSchoolTree("", "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list1!=null && list2!=null && list3!=null && list4!=null);
	}

	@Test
	public void testFindByGovIdsMap() {
		List<School> list=null;
		List item=new ArrayList();
		item.add("000026833");
		try {
			list=schoolServices.findByGovIdsMap(item);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testSearchByName() {
		List list1=null;
		List list2=null;
		try {
			list1=schoolServices.searchByName("123");
			list2=schoolServices.searchByName("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list1!=null && list2!=null);	
	}

	@Test
	public void testSelectFullByPrimaryKey() {
		School school=null;
		try {
			school=schoolServices.selectFullByPrimaryKey("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(school!=null);
	}

//	@Test
//	public void testInsertImportExcel() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetSchoolJianPinCount() {
		int i=0;
		try {
			i=schoolServices.getSchoolJianPinCount("xx");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}

	@Test
	public void testGetSchoolPinYinCount() {
		int i=0;
		try {
			i=schoolServices.getSchoolPinYinCount("haha");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}

	@Test
	public void testGetSchoolSerialNumberCount() {
		int i=0;
		try {
			i=schoolServices.getSchoolSerialNumberCount("wuxian", "666", "3222");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}

	@Test
	public void testValidatName() {
		int i=0;
		try {
			i=schoolServices.validatName("123", "323");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(i!=0);
	}
}
