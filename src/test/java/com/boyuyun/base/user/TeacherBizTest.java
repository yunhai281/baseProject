package com.boyuyun.base.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.biz.TeacherCourseBiz;
import com.boyuyun.base.user.biz.TeacherBiz;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeacherBizTest extends SpringJunitTest {
	private static Logger logger = Logger.getLogger(TeacherBizTest.class);
	@Resource
	private TeacherBiz teacherService;

	@Resource
	private TeacherCourseBiz teacherCourseService;

	@Test
	public void testAdd() {
		Teacher teacher = new Teacher();
		teacher.setId("abcd");
		teacher.setUserName("abcd");
		teacher.setRealName("abcd");
		teacher.setTeacherNo("abcd");
		teacher.setPwd("abcd");
		teacher.setSchoolId("042003ea120946698e61721b839baa90");
		Integer ordinal = UserType.教师.ordinal();
		teacher.setUserType(ordinal.toString());
		Exception ex = null;
		try {
			teacherService.add(teacher);
		} catch (SQLException e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex == null);
	}

	@Test
	public void testXUpdate() {
		Teacher teacher = null;
		try {
			teacher = teacherService.get("abcd");
			teacher.setTeacherNo("aaaa");
			teacherService.update(teacher);
			teacher = teacherService.get("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(teacher.getTeacherNo().equals("aaaa"));
	}

	@Test
	public void testZDelete() {

		Teacher teacher = null;
		try {
			teacher = teacherService.get("abcd");
			teacherService.delete(teacher);
			teacherCourseService.deleteByTeacher("abcd");
			teacher = teacherService.get("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(teacher != null);
	}

	@Test
	public void testB_GetListNonePaged() {

		Teacher teacher = new Teacher();
		teacher.setSchoolId("042003ea120946698e61721b839baa90");
		List<Teacher> listNonePaged = new ArrayList<>();
		try {
			listNonePaged = teacherService.getListNonePaged(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listNonePaged.size() > 0);
	}

	@Test
	public void testC_GetListPaged() {
		Teacher teacher = new Teacher();
		teacher.setSchoolId("042003ea120946698e61721b839baa90");
		List<Teacher> listPaged = new ArrayList<>();
		try {
			listPaged = teacherService.getListPaged(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPaged.size() > 0);
	}

	@Test
	public void testD_GetListPagedCount() {
		Teacher teacher = new Teacher();
		teacher.setSchoolId("042003ea120946698e61721b839baa90");
		int listPagedCount = 0;
		try {
			listPagedCount = teacherService.getListPagedCount(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCount > 0);
	}

	@Test
	public void testE_Get() {
		Teacher teacher = null;
		try {
			teacher = teacherService.get("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(teacher != null);
	}

	@Test
	public void testF_GetTeacherList() {
		Teacher teacher = new Teacher();
		teacher.setSchoolId("042003ea120946698e61721b839baa90");
		List<Teacher> teacherList = new ArrayList<>();
		try {
			teacherList = teacherService.getTeacherList(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(teacherList.size() > 0);
	}

	@Test
	public void testGetTeacherNoCount() {
		int teacherNoCount = 0;
		try {
			teacherNoCount = teacherService.getTeacherNoCount("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(teacherNoCount > 0);
	}

	@Test
	public void testYAddAndUpdate() {

		Teacher teacher = new Teacher();
		teacher.setId("abcd");
		String courseList = "1,2";
		String addAndUpdate = null;
		try {
			addAndUpdate = teacherService.addAndUpdate(teacher, courseList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(addAndUpdate.indexOf("msg") > 0);
	}

}
