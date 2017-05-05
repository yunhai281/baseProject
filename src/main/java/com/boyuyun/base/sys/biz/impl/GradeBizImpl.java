package com.boyuyun.base.sys.biz.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.sys.biz.GradeBiz;
import com.boyuyun.base.sys.dao.GradeDao;
import com.boyuyun.base.sys.entity.Grade;
import com.boyuyun.common.json.ByyJsonUtil;
@Service
public class GradeBizImpl   implements GradeBiz {

	@Resource
	private GradeDao gradeDao; 
	
	@Transactional
	public boolean add(Grade grade)throws Exception {
		return gradeDao.insert(grade);
	}

	@Transactional
	public boolean update(Grade grade)throws Exception {
		return gradeDao.update(grade);
	}

	@Transactional
	public boolean delete(Grade grade)throws Exception {
		return gradeDao.delete(grade);
	}

	@Override
	public List<Grade> getListNonePaged(Grade grade)throws Exception {
		return gradeDao.getListNonePaged(grade);
	}

	@Override
	public List<Grade> getListPaged(Grade grade)throws Exception {
		return gradeDao.getListPaged(grade);
	}

	@Override
	public int getListPagedCount(Grade grade)throws Exception {
		return gradeDao.getListPagedCount(grade);
	}

	@Override
	public Grade get(String grade)throws Exception {
		return gradeDao.get(grade);
	}
	@Override
	public Grade getBySortNum(int sortNum) throws Exception {
		return gradeDao.getBySortNum(sortNum);
	}

	@Transactional
	public boolean deleteAll(List<Grade> grades)throws Exception {
		return gradeDao.deleteAll(grades);
	}

	/**
	 * 有关联>0时false,不执行删除
	 */
	@Override
	public boolean getListByOrgGradeCount(String fid) throws SQLException {
		
		return gradeDao.getListByOrgGradeCount(fid)>0?false:true;
	}

	@Override
	public int getMaxSortNum() throws SQLException {
		return gradeDao.getMaxSortNum();
	}

	@Override
	@Transactional
	public int moveGradeSortNum(String id, String type) throws Exception {
		Grade grade = gradeDao.get(id);
		int sortNum = grade.getSortNum();
		int i = 0;
		//String result=ByyJsonUtil.getFailJson("移动失败");
		if("up".equals(type)){
			if(sortNum==1){
				//要上移的是第一条记录，则移动失败
				i=2;
				//result=ByyJsonUtil.getFailJson("已经是第一条记录，移动失败");
			}else {
				//找到要上移的记录，将其前面那条记录的sortNum+1,将该记录的sortNum-1
				Grade preGrade = gradeDao.getBySortNum(sortNum-1);
				preGrade.setSortNum(sortNum);
				grade.setSortNum(sortNum-1);
				gradeDao.update(preGrade);
				gradeDao.update(grade);
				i = 1;
				//result=ByyJsonUtil.getSuccessJson("上移成功");
				
				
			}
			
		}else {
			//向下移动，如果是最后一条记录，则移动失败
			int maxSortNum = gradeDao.getMaxSortNum();
			if(maxSortNum==grade.getSortNum()){
				i = -2;
				//result=ByyJsonUtil.getFailJson("已经是最后一条记录，移动失败");
			}else {
				//找到要下移的记录，将后面的记录的sortnum-1,将该记录的sortnum+1
				Grade postGrade = gradeDao.getBySortNum(sortNum+1);
				postGrade.setSortNum(sortNum);
				grade.setSortNum(sortNum+1);
				gradeDao.update(postGrade);
				gradeDao.update(grade);
				i = -1;
				//result=ByyJsonUtil.getSuccessJson("下移成功");
			}
			
		}
		return i;
	}

	@Override
	public int getSortNum(String sortnum,String id) throws SQLException {
		return gradeDao.getSortNum(sortnum, id);
	}
}
