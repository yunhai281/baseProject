package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.StudentParentDao;
import com.boyuyun.base.user.entity.StudentParentRelation;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StudentParentDaoImpl extends BaseDAO implements StudentParentDao {
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 插入家长和学生关联信息
	 * @return
	 */
	@Override
	public int insert(StudentParentRelation po) throws SQLException {
		String sql = "insert into student_parent_relation(id,parentId,studentId,relation)values(?,?,?,?)" ;
		List params = new  ArrayList();
		params.add(po.getId());
		params.add(po.getParentId());
		params.add(po.getStudentId());
		params.add(po.getRelation());
		int result = super.executeUpdate(sql, params.toArray());
		return result;
	}
	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 删除家长和学生关联信息
	 * @return
	 */
	@Override
	public int delete(String parentId) throws SQLException {
		String sql = " delete from student_parent_relation where parentId=? ";
		List params = new  ArrayList();
		params.add(parentId);
		int result = super.executeUpdate(sql, params.toArray());
		return result;
	}

	/**
	 * @author caoyy
	 * @date 2017-03-21
	 * @see 获取家长和学生关联信息列表
	 * @return
	 */
	@Override
	public List<StudentParentRelation> getList(StudentParentRelation po) throws SQLException {
		List paramList = new ArrayList();
		String sql = " select " +
						 " t.studentId,t.relation,u.realName as studentName,d1.name as relationName," +
						 " sc.id schoolId,sc.name schoolName," +
						 " grade.id gradeId,grade.name gradeName," +
						 " class.id classId,class.name className,sgrade.stage as stageId "+
						 " from student_parent_relation t " +
						 " left join user_base u on u.id=t.studentId "+
						 " left join dictionary_item d1 on d1.id=t.relation " +
						 " left join student s on s.id = t.studentId" +
						 " left join SCHOOL_CLASS class on class.id=s.belongClassId "+
						 " left join school_grade grade on grade.id=class.gradeId" + 
						 " left join sys_grade sgrade on grade.sysGradeId=sgrade.id" + 
						 " left join school sc on sc.id=grade.schoolId" +
						
						
						 " where 1=1 ";
		if (!Strings.isNullOrEmpty(po.getParentId())) {
			sql += " and t.parentId= ?  ";
			paramList.add(po.getParentId());
		}
		Object [] param = paramList.toArray();
		return super.executeQuery(sql, param, new BeanListHandler(po.getClass()));
	}

}
