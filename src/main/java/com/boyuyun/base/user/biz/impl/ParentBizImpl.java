package com.boyuyun.base.user.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.user.biz.ParentBiz;
import com.boyuyun.base.user.dao.ParentDao;
import com.boyuyun.base.user.dao.StudentParentDao;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.Parent;
import com.boyuyun.base.user.entity.StudentParentRelation;
import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.ByyStringUtil;
@Service
public class ParentBizImpl  implements ParentBiz {
	@Resource
	private ParentDao parentDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StudentParentDao studentParentDao;
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 添加记录
	 * @return
	 */
	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Parent,operateType=SyncOperateType.Add,system={"wd"})
	public boolean add(Parent parent) throws Exception{
		int result = 0;
		boolean isSu = userDao.insert(parent);
		if(isSu){
			result = parentDao.insert(parent);
			//添加关联学生
			if(parent.getStudents()!=null&&parent.getStudents().size()>0){
				for(StudentParentRelation relation:parent.getStudents()){
					String relationId=  ByyStringUtil.getRandomUUID().replace("-", "");
					relation.setId(relationId);
					relation.setParentId(parent.getId());
					result = studentParentDao.insert(relation);
				}
			}
		}
		return result>0?true:false;
	}
	
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 修改记录
	 * @return
	 */
	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Parent,operateType=SyncOperateType.Update,system={"wd"})
	public boolean update(Parent parent) throws Exception{
		//修改人员基本信息
		int result = 0;
		boolean isSu = userDao.update(parent);
		if(isSu){
			result  = parentDao.update(parent);
			//修改关联学生
			result = studentParentDao.delete(parent.getId());
			if(parent.getStudents()!=null&&parent.getStudents().size()>0){
				for(StudentParentRelation relation:parent.getStudents()){
					relation.setId(UUID.randomUUID().toString());
					relation.setParentId(parent.getId());
					result = studentParentDao.insert(relation);
				}
			}
		}
		return result>0?true:false;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 批量删除记录
	 * @return
	 */
	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Parent,operateType=SyncOperateType.Delete,system={"wd"})
	public boolean delete(List<Parent> parents ) throws Exception{
		int result = 0;
		for(Parent p:parents){
			userDao.delete(p);
			result = parentDao.delete(p.getId());
		}
		return result>0?true:false;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取记录列表
	 * @return
	 */
	@Override
	public List<Parent> getListNonePaged(Parent parent) throws Exception{
		parent.setBegin(-1);
		parent.setEnd(-1);
		return parentDao.getList(parent);
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 分页获取记录列表
	 * @return
	 */
	@Override
	public List<Parent> getListPaged(Parent parent) throws Exception{
		boolean searchStudent = false;
		if(parent.getChildName()!=null && !"".equals(parent.getChildName())){
			searchStudent = parent.getChildName().equals("true")?true:false;
		}
		List<Parent> list = parentDao.getList(parent);
		if(searchStudent){
			if(list!=null&&!list.isEmpty()){
				for(Parent po:list){
					StudentParentRelation studentRelation = new StudentParentRelation();
					studentRelation.setParentId(po.getId());
					List<StudentParentRelation> studentList = studentParentDao.getList(studentRelation);
					List<String> students = new ArrayList<String>();
					for(int i=0;i<studentList.size();i++){
						students.add(studentList.get(i).getStudentName());
					}
					po.setChildName(StringUtils.join(students, ","));
				}
			}
		}
		return list;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取记录个数
	 * @return
	 */
	@Override
	public int getListPagedCount(Parent parent) throws Exception{
		return parentDao.getListPagedCount(parent);
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 根据id获取家长记录
	 * @return
	 */
	@Override
	public Parent get(Parent parent) throws Exception{
		StudentParentRelation relation  = new StudentParentRelation();
		relation.setParentId(parent.getId());
		List<StudentParentRelation> students = studentParentDao.getList(relation);
		parent = parentDao.getParent(parent);
		if(parent!=null){
			parent.setStudents(students);
		}
		return parent;
	}

	
}
