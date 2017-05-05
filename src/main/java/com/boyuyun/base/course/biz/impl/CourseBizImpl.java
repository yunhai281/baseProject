package com.boyuyun.base.course.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.dao.CourseDao;
import com.boyuyun.base.course.entity.Course;
@Service
public class CourseBizImpl implements CourseBiz {

    @Resource
    private CourseDao courseDao;

	@Override
	@Transactional
	public boolean add(Course course)throws Exception {
		return this.courseDao.insert(course);
	}

	@Transactional
	public boolean update(Course course)throws Exception {
		return this.courseDao.update(course);
	}

	
	public List<Course> getListPaged(Course app)throws Exception {

		return this.courseDao.getListPaged(app);
	}

	@Override
	public int getListPagedCount(Course app)throws Exception {

		return this.courseDao.getListPagedCount(app);
	}

	@Transactional
	public boolean delete(Course course)throws Exception {

		return this.courseDao.delete(course);
	}

	@Transactional
	public boolean deleteAll(List<Course> list)throws Exception {
		return this.courseDao.deleteAll(list);
	}

	@Override
	public Course get(String id)throws Exception {
		return this.courseDao.get(id);
	}

	@Override
	public List<Course> getListNonePaged(Course app)throws Exception {
		return this.courseDao.getListNonePaged(app);
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		return courseDao.getMaxSortNum();
	}
}
