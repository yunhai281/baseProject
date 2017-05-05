package com.boyuyun.base.org.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.boyuyun.base.org.biz.SchoolClassBiz;
import com.boyuyun.base.org.dao.SchoolClassDao;
import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.util.consts.Stage;
@Service
public class SchoolClassBizImpl  implements SchoolClassBiz{
	@Resource
	private SchoolClassDao schoolClassDao;

	@Override
	public boolean add(SchoolClass schoolClass) throws SQLException{
		return schoolClassDao.insert(schoolClass);
	}

	@Override
	public boolean update(SchoolClass schoolClass)throws SQLException {
		return schoolClassDao.update(schoolClass);
	}

	@Override
	public boolean delete(SchoolClass schoolClass) throws SQLException{
		return schoolClassDao.delete(schoolClass);
	}

	@Override
	public boolean deleteAll(List<SchoolClass> schoolClasses) throws SQLException{
		return schoolClassDao.deleteAll(schoolClasses);
	}

	@Override
	public List<SchoolClass> getListNonePaged(SchoolClass schoolClass)throws SQLException {
		return schoolClassDao.getListNonePaged(schoolClass);
	}

	@Override
	public List<SchoolClass> getListPaged(SchoolClass schoolClass)throws SQLException {
		return schoolClassDao.getListPaged(schoolClass);
	}

	@Override
	public int getListPagedCount(SchoolClass schoolClass) throws SQLException{
		return schoolClassDao.getListPagedCount(schoolClass);
	}

	@Override
	public SchoolClass get(String id) throws SQLException{
		return schoolClassDao.get(id);
	}

	@Override
	public List<SchoolClass> getSchoolClassListBy(String schoolId, Stage stage, String gradeId) throws SQLException {
		return schoolClassDao.getSchoolClassListBy(schoolId, stage, gradeId);
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		return schoolClassDao.getMaxSortNum();
	}

}
