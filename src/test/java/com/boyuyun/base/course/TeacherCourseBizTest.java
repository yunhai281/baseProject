package com.boyuyun.base.course;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.boyuyun.base.course.biz.TeacherCourseBiz;
import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.base.user.biz.TeacherBiz;
import com.boyuyun.common.junit.SpringJunitTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeacherCourseBizTest extends SpringJunitTest {

	private static Logger logger = Logger.getLogger(TeacherCourseBizTest.class);
	
	@Resource
	private TeacherCourseBiz teacherCourseService;
	
	@Test
	public void testB_GetTeacherCourse() {
		List<TeacherCourse> list = null;
		try {
			list = teacherCourseService.getTeacherCourse("003");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(list!=null);
	}

	@Test
	public void testC_DeleteByTeacher() {
		Exception ex = null;
		try {
			teacherCourseService.deleteByTeacher("003");
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);			
		}
		Assert.assertTrue(ex == null);
	}

	@Test
	public void testA_Insert() {
		TeacherCourse tcourse = new TeacherCourse();
		tcourse.setId("001");
		tcourse.setCourseId("002");
		tcourse.setTeacherId("003");
		Exception ex = null;
		try {
			teacherCourseService.insert(tcourse);
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
			logger.error(e);
		}
		Assert.assertTrue(ex==null);
	}

//	@Test
//	public void testAddAll() {
//		fail("Not yet implemented");
//	}

}
