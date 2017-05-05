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
import com.boyuyun.base.sys.biz.GradeBiz;
import com.boyuyun.base.sys.entity.Dictionary;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.sys.entity.Grade;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GradeBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	
	@Resource
	private GradeBiz gradeService;
	
	@Test
	public void testAdd() {
		Grade grade = new Grade();
		grade.setId("123");
		grade.setName("123");
		grade.setShortName("123");
		grade.setSortNum(1);
		grade.setStage(1);
		Exception ex = null;
		try {
			gradeService.add(grade);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

	@Test
	public void testI_Update() {
		Grade grade= new Grade();
		try {
			grade=gradeService.get("123");
			grade.setName("321");
			gradeService.update(grade);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		 try {
			Assert.assertTrue(gradeService.get("123").getName().equals("321"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testJ_Delete() {
		Grade grade= new Grade();
		try {
			grade=gradeService.get("123");
			gradeService.delete(grade);
			grade=gradeService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(grade==null);
	}

	@Test
	public void testC_GetListNonePaged() {
		List<Grade> list =null;
		try {
			Grade grade=new Grade();
			grade.setName("123"); 
			list = gradeService.getListNonePaged(grade);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testD_GetListPaged() {
		List<Grade> list =null;
		try {
			Grade grade=new Grade();
			grade.setName("123");
			grade.setBegin(1);
			grade.setEnd(10);
			list = gradeService.getListPaged(grade);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testE_GetListPagedCount() {
		int num=0;
		try {
			Grade grade=new Grade();
			grade.setName("123"); 
			num = gradeService.getListPagedCount(grade);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(num!=0);
	}

	@Test
	public void testB_Get() {
		Grade grade= null;
		try {
			grade=gradeService.get("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(grade.getId().equals("123"));
	}

	@Test
	public void testF_GetBySortNum() {
		Grade grade=null;
		try {
			grade =gradeService.getBySortNum(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(grade.getSortNum()==1);
	} 
	
	@Test
	public void testGetSortNum() {
		int num=0;
		try {
			num =gradeService.getSortNum("1", "123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(num!=0);
	}

	@Test
	public void testGetListByOrgGradeCount() {
		Boolean flag = false;
		try {
			flag =gradeService.getListByOrgGradeCount("123");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(flag);
	}

	@Test
	public void testGetMaxSortNum() {
		int num=0;
		try {
			num =gradeService.getMaxSortNum();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(num!=0);
	}

	@Test
	public void testH_MoveGradeSortNum() {
		int num=0;
		try {
			num =gradeService.moveGradeSortNum("123","up");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(num!=0);
	}

}
