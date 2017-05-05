package com.boyuyun.base.org.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.org.biz.SchoolGradeBiz;
import com.boyuyun.base.org.dao.GovernmentDao;
import com.boyuyun.base.org.dao.SchoolDao;
import com.boyuyun.base.org.dao.SchoolGradeDao;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.util.consts.Stage;

@Service
public class SchoolGradeBizImpl  implements SchoolGradeBiz {
	@Resource
	private SchoolDao schoolDao;
	@Resource
	private GovernmentDao governmentDao;
	@Resource
	private SchoolGradeDao schoolGradeDao;
	
	
	public List<School> getByGovernmentId(String id) throws Exception{
		return schoolDao.getByGovernmentId(id);
	}


	public List<School> findByGovIdsMap(List item) throws Exception{
		return this.schoolDao.findByGovIdsMap(item);
	}
	public List<School> getByParentId(String id) throws Exception{
		return schoolDao.getByParentId(id);
	}

	@Override
	public List<SchoolGrade> getListNonePaged(SchoolGrade grade) throws Exception{
		return schoolGradeDao.getListNonePaged(grade);
	}


	@Override
	public List<SchoolGrade> getListPaged(SchoolGrade grade)throws Exception {
		return schoolGradeDao.getListPaged(grade);
	}


	@Override
	public int getListPagedCount(SchoolGrade grade) throws Exception{
		return schoolGradeDao.getListPagedCount(grade);
	}


	@Transactional
	public boolean add(SchoolGrade grade) throws Exception{
		return schoolGradeDao.insert(grade);
	}


	@Transactional
	public boolean update(SchoolGrade grade) throws Exception{
		return schoolGradeDao.update(grade);
	}


	@Transactional
	public boolean delete(SchoolGrade grade) throws Exception{
		return schoolGradeDao.delete(grade);
	}


	@Transactional
	public boolean deleteAll(List<SchoolGrade> grades) throws Exception{
		return schoolGradeDao.deleteAll(grades);
	}


	@Override
	public SchoolGrade get(String id) throws Exception{
		return schoolGradeDao.get(id);
	}

	@Override
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId, Stage stage) throws Exception {
		return schoolGradeDao.getSchoolGradeBySchoolAndStage(schoolId, stage);
	}


	@Override
	public List<SchoolGrade> getSchoolGradeBySchoolAndStage(String schoolId) throws Exception {
		return schoolGradeDao.getSchoolGradeBySchoolAndStage(schoolId);
	}

	/**
	 * 外键能查到结果就false,不予执行,查不到就是true,可以执行
	 */
	public boolean getListByOrgClassCount(String fid) throws Exception {
		
		return schoolGradeDao.getListByOrgClassCount(fid)>0?false:true;
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		return schoolGradeDao.getMaxSortNum();
	}
}
