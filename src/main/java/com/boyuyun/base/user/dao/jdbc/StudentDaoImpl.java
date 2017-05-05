package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.StudentDao;
import com.boyuyun.base.user.entity.Student;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StudentDaoImpl extends BaseDAO implements StudentDao {

	@Override
	public boolean insert(Student student) throws SQLException {
		String sql ="insert into STUDENT (ID, ENTRYTYPE, LEAVINGDATE,STATUS, STUDENTNO,STUDYTYPE,belongClassId, belongGradeId, "
				+ "ENTRYSCORE,XUEJINO, ENTRYDATE,schoolId) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[] {student.getId(),student.getEntryType(),student.getLeavingDate(),student.getStatus(),student.getStudentNo(),
				student.getStudyType(),student.getBelongClassId(),student.getBelongGradeId(),student.getEntryScore(),student.getXueJiNo(),
				student.getEntryDate(),student.getSchoolId()};
		int i = executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean update(Student student) throws SQLException {
		String sql ="update STUDENT set ENTRYTYPE = ?,LEAVINGDATE = ?,STATUS = ?,STUDENTNO = ?,STUDYTYPE = ?,belongClassId = ?,belongGradeId = ?,"
				+ "ENTRYSCORE= ?,XUEJINO = ?, ENTRYDATE = ?,schoolId = ? where ID = ? ";
		
		Object[] param = new Object[] {student.getEntryType(),student.getLeavingDate(),student.getStatus(),student.getStudentNo(),
				student.getStudyType(),student.getBelongClassId(),student.getBelongGradeId(),student.getEntryScore(),student.getXueJiNo(),
				student.getEntryDate(),student.getSchoolId(),student.getId()};
		int i = executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(Student student) throws SQLException {
		String sql = "delete from student where id=?";
		int i = this.executeUpdate(sql, new String[]{student.getId()});
		return i>0;
	}

	@Override
	public boolean deleteAll(List<Student> students) throws SQLException {
		boolean result = true;
		if(students.size()>0){
			for (Student student : students) {
				result&=delete(student);
			}
		}
		return result;
	}

	@Override
	public List<Student> getListNonePaged(Student student) throws SQLException {
		StringBuffer sql = new StringBuffer("select t.*,u.realName from student t left join user_base u on u.id=t.id where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(student.getSchoolId())){
			sql.append(" and schoolId=? ");
			param.add(student.getSchoolId());
		}
		
		if(!Strings.isNullOrEmpty(student.getBelongClassId())){
			sql.append(" and belongClassId=? ");
			param.add(student.getBelongClassId());
		}
		if(!Strings.isNullOrEmpty(student.getRealName())){
			sql.append(" and u.REALNAME LIKE  concat(concat('%',?),'%') escape '/' ");
			param.add(student.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(Student.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}

	@Override
	public List<Student> getListPaged(Student student) throws SQLException {
		StringBuffer sql = new StringBuffer("select u.ID, ENTRYTYPE, LEAVINGDATE, STATUS, STUDENTNO, STUDYTYPE, belongClassId, belongGradeId,ENTRYSCORE,"
				+ "XUEJINO, ENTRYDATE,AVATAR, u.EMAIL, MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, VALIDATEEMAIL,"
				+ "std.schoolId,school.Name as SCHOOLNAME,VALIDATEMOBILE, BIRTHDAY, REALNAME, LOGINNAME,u.pinyin,u.jianpin,d1.name as SEXNAME,"
				+ "d2.name as ENTRYTYPENAME,d3.name as STATUSNAME,d4.name as STUDYTYPENAME from STUDENT std left join user_base u "
				+ "on u.id=std.id left join dictionary_item d1 on d1.id=u.sex left join dictionary_item d2 on d2.id=std.ENTRYTYPE "
				+ "left join dictionary_item d3 on d3.id=std.STATUS left join dictionary_item d4 on d4.id=std.STUDYTYPE left join "
				+ "school on school.id=std.schoolId where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(student.getSchoolId())){
			if(student.getSchoolId().indexOf(",")>-1){
				sql.append(" and std.schoolId in ('"+StringUtils.join(student.getSchoolId().split(","), "','")+"') ");
			}else{
				sql.append(" and std.schoolId=? ");
				param.add(student.getSchoolId());
			}
			
		}
		if(!Strings.isNullOrEmpty(student.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%')");
			param.add(student.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(Student.class);
		List list = this.executeQueryPage(sql.toString(), student.getBegin(), student.getEnd(), param.toArray(), handler);
		return list;
	}

	@Override
	public int getListPagedCount(Student student) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from student s left join user_base u on u.id=s.id where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(student.getSchoolId())){
			if(student.getSchoolId().indexOf(",")>-1){
				sql.append(" and s.schoolId in ('"+StringUtils.join(student.getSchoolId().split(","), "','")+"') ");
			}else{
				sql.append(" and s.schoolId=? ");
				param.add(student.getSchoolId());
			}
			
		}
		if(!Strings.isNullOrEmpty(student.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%')  ");
			param.add(student.getRealName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public Student get(String id) throws SQLException {
		StringBuffer sql =new StringBuffer("select u.ID, ENTRYTYPE, LEAVINGDATE, std.STATUS, STUDENTNO, STUDYTYPE, belongClassId, belongGradeId,ENTRYSCORE,XUEJINO, ENTRYDATE,AVATAR, u.EMAIL, MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, VALIDATEEMAIL,std.schoolId,school.Name as SCHOOLNAME,VALIDATEMOBILE, BIRTHDAY, REALNAME, LOGINNAME,u.pinyin,u.jianpin,d1.name as SEXNAME,d2.name as ENTRYTYPENAME,d3.name as STATUSNAME,d4.name as STUDYTYPENAME,  ");
		sql.append(" school_class.name  as belongClassName,school_grade.name as belongGradeName , school_grade.shortName as  belongGradeShortName, sys_grade.stage as belongGradeStage ");
		sql.append(" from STUDENT std ");
		sql.append(" left join user_base u on u.id=std.id ");
		sql.append(" left join dictionary_item d1 on d1.id=u.sex ");
		sql.append(" left join dictionary_item d2 on d2.id=std.ENTRYTYPE ");
		sql.append(" left join dictionary_item d3 on d3.id=std.STATUS ");
		sql.append(" left join dictionary_item d4 on d4.id=std.STUDYTYPE ");
		sql.append(" left join school on school.id=std.schoolId ");
		sql.append(" left join school_grade on std.belongGradeId=school_grade.id ");
		sql.append(" left join school_class on std.belongClassId=school_class.id ");
		sql.append(" left join sys_grade on school_grade.sysGradeId=sys_grade.id ");
		sql.append(" where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and u.ID=? ");
			param.add(id);
		}
		ResultSetHandler<Student> handler =  new BeanHandler(Student.class);
		Student result =(Student) executeQueryObject(sql.toString(), param.toArray(), handler);
		return result;
	}

}
