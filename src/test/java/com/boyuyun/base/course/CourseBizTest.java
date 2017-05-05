package com.boyuyun.base.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourseBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(CourseBizTest.class);
	@Resource
	private CourseBiz courseService;

	@Test
	public void testAdd() {
		Course course = new Course();
		course.setId("abc");
		course.setName("abc");
		course.setHours(5);
		Exception ex = null;
		try {
			courseService.add(course);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex == null);
	}

	@Test
	public void testC_Update() {
		Course course = null;
		try {
			course = courseService.get("abc");
			course.setHours(6);
			courseService.update(course);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		try {
			Assert.assertTrue(courseService.get("abc").getHours() == 6);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetListPaged() {
		Course app = new Course();
		List<Course> listPaged = new ArrayList<>();
		try {
			listPaged = courseService.getListPaged(app);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPaged.size() > 0);
	}

	@Test
	public void testGetListPagedCount() {
		Course app = new Course();
		int listPagedCount = 0;
		try {
			listPagedCount = courseService.getListPagedCount(app);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCount > 0);
	}

	@Test
	public void testZDelete() {
		Course course = null;
		try {
			course = courseService.get("abc");
			courseService.delete(course);
			course = courseService.get("abc");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(course != null);
	}

	@Test
	public void testB_Get() {
		Course course = null;
		try {
			course = courseService.get("abc");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(course.getName().equals("abc"));
	}

	@Test
	public void testGetListNonePaged() {
		Course app = new Course();
		List<Course> listNonePaged = new ArrayList<>();
		try {
			listNonePaged = courseService.getListNonePaged(app);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listNonePaged.size() > 0);
	}

	@Test
	public void testGetMaxSortNum() {
		int maxSortNum = 0;
		try {
			maxSortNum = courseService.getMaxSortNum();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(maxSortNum > 0);
	}

}
