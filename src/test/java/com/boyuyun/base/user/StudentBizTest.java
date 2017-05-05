package com.boyuyun.base.user;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.user.biz.StudentBiz;
import com.boyuyun.base.user.entity.Student;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(StudentBizTest.class);
	@Resource
	private StudentBiz studentService;

	@Test
	public void testAdd() {
		Student student = new Student();
		student.setId("abcd");
		student.setRealName("abcd");
		student.setStudentNo("abcd");
		student.setUserName("abcd");
		student.setSchoolId("042003ea120946698e61721b839baa90");
		student.setBelongClassId("005185b24dcf4b92999aa1c8d51f032d");
		Exception exception = null;
		try {
			studentService.add(student);
		} catch (SQLException e) {
			exception = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(exception == null);
	}

	@Test
	public void testYUpdate() {
		try {
			Student student = studentService.get("abcd");
			student.setStudentNo("aaaa");
			studentService.update(student);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		try {
			Assert.assertTrue(studentService.get("abcd").getStudentNo().equals("aaaa"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testZDelete() {
		Student student = null;
		try {
			student = studentService.get("abcd");
			studentService.delete(student);
			student = studentService.get("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertFalse(student != null);
	}

	@Test
	public void testGetListNonePaged() {
		Student student = new Student();
		student.setSchoolId("042003ea120946698e61721b839baa90");
		student.setBelongClassId("005185b24dcf4b92999aa1c8d51f032d");
		List<Student> listNonePaged = null;
		try {
			listNonePaged = studentService.getListNonePaged(student);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listNonePaged.size() > 0);
	}

	@Test
	public void testGetListPaged() {

		Student student = new Student();
		List<Student> listPaged = null;
		try {
			listPaged = studentService.getListPaged(student);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPaged.size() > 0);
	}

	@Test
	public void testGetListPagedCount() {

		Student student = new Student();
		int listPagedCount = 0;
		try {
			listPagedCount = studentService.getListPagedCount(student);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(listPagedCount > 0);
	}

	@Test
	public void testGet() {
		Student student = null;
		try {
			student = studentService.get("abcd");
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(student != null);
	}

}
