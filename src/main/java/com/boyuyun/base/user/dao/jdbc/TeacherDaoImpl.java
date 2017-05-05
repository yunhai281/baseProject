package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.TeacherDao;
import com.boyuyun.base.user.entity.Teacher;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;

@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TeacherDaoImpl extends BaseDAO implements TeacherDao {

	@Override
	public boolean insert(Teacher teacher) throws SQLException {
		String sql = " insert into TEACHER (ID, COMPILETYPE, DGREE, DGREENO, EDUCATION, EDUCATIONNO, GRADUATEDATE, GRADUATESCHOOL,"
				+ " HASCOMPILE, HASTEACHERCERTIFICATE, HIREDATE, MARITALSTATUS, POSITIONALTITLE, POSTTYPE, PROFESSION, STARTWORKDATE,"
				+ " STATUS, TEACHERCERTIFICATENO, TEACHERNO, SCHOOLID, SKELETONTYPE, ARCHIVELOCATIONID, WORKSTATUS, DEPTSORTNUM) values("
				+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		Object[] param = new Object[] { teacher.getId(), teacher.getCompileType(), teacher.getDgree(), teacher.getDgreeNo(),
				teacher.getEducation(), teacher.getEducationNo(), teacher.getGraduateDate(), teacher.getGraduateSchool(),
				teacher.isHasCompile(), teacher.isHasTeacherCertificate(), teacher.getHireDate(), teacher.getMaritalStatus(),
				teacher.getPositionalTitle(), teacher.getPostType(), teacher.getProfession(), teacher.getStartWorkDate(),
				teacher.getStatus(), teacher.getTeacherCertificateNo(), teacher.getTeacherNo(), teacher.getSchoolId(),
				teacher.getSkeletonType(), teacher.getArchiveLocationId(), teacher.getWorkStatus(), teacher.getDeptSortNum() };
		int i = this.executeUpdate(sql, param);
		return i > 0;
	}

	@Override
	public boolean update(Teacher teacher) throws SQLException {
		String sql = "update TEACHER set COMPILETYPE = ?,DGREE = ?,DGREENO = ?,EDUCATION =?,EDUCATIONNO = ?,GRADUATEDATE = ?,"
				+ "GRADUATESCHOOL = ?,HASCOMPILE = ?,HASTEACHERCERTIFICATE = ?,HIREDATE = ?,MARITALSTATUS = ?,POSITIONALTITLE = ?,"
				+ "POSTTYPE = ?,PROFESSION = ?,STARTWORKDATE = ?,STATUS = ?,TEACHERCERTIFICATENO = ?,TEACHERNO = ?,SCHOOLID = ?,"
				+ "SKELETONTYPE = ?,ARCHIVELOCATIONID = ?,WORKSTATUS = ?,DEPTSORTNUM = ? where ID = ?";
		Object[] param = new Object[] { teacher.getCompileType(), teacher.getDgree(), teacher.getDgreeNo(),
				teacher.getEducation(), teacher.getEducationNo(), teacher.getGraduateDate(), teacher.getGraduateSchool(),
				teacher.isHasCompile(), teacher.isHasTeacherCertificate(), teacher.getHireDate(), teacher.getMaritalStatus(),
				teacher.getPositionalTitle(), teacher.getPostType(), teacher.getProfession(), teacher.getStartWorkDate(),
				teacher.getStatus(), teacher.getTeacherCertificateNo(), teacher.getTeacherNo(), teacher.getSchoolId(),
				teacher.getSkeletonType(), teacher.getArchiveLocationId(), teacher.getWorkStatus(), teacher.getDeptSortNum() ,teacher.getId()};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(Teacher teacher) throws SQLException {
		String sql = "delete from teacher where id=?";
		int i = this.executeUpdate(sql, new String[]{teacher.getId()});
		return i>0;
	}

	@Override
	public boolean deleteAll(List<Teacher> teachers) throws SQLException {
		boolean result = true;
		if(teachers!=null){
			for (Iterator iterator = teachers.iterator(); iterator.hasNext();) {
				Teacher t = (Teacher) iterator.next();
				result&=this.delete(t);
			}
		}
		return result;
	}

	@Override
	public boolean insertAll(List<Teacher> teachers) throws SQLException {
		boolean result = true;
		if(teachers!=null){
			for (Iterator iterator = teachers.iterator(); iterator.hasNext();) {
				Teacher t = (Teacher) iterator.next();
				result&=this.insert(t);
			}
		}
		return result;
	}
	
	@Override
	public boolean addAll(List<Teacher> teachers) throws SQLException {
		boolean result = true;
		if(teachers!=null){
			for (Iterator iterator = teachers.iterator(); iterator.hasNext();) {
				Teacher t = (Teacher) iterator.next();
				result&=this.insert(t);
			}
		}
		return result;
	}

	@Override
	public List<Teacher> getListNonePaged(Teacher teacher) throws SQLException {
		StringBuffer sql = new StringBuffer("select * from teacher where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(teacher.getSchoolId())){
			sql.append(" and schoolId=? ");
			param.add(teacher.getSchoolId());
		}
		if(!Strings.isNullOrEmpty(teacher.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%')  ");
			param.add(teacher.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(Teacher.class);
		return this.executeQuery(sql.toString(),param.toArray(), handler);
	}

	@Override
	public List<Teacher> getListPaged(Teacher teacher) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT   u.ID, COMPILETYPE, DGREE, DGREENO, EDUCATION, EDUCATIONNO, GRADUATEDATE, "
				+ "GRADUATESCHOOL, HASCOMPILE, HASTEACHERCERTIFICATE, HIREDATE, MARITALSTATUS, POSITIONALTITLE, POSTTYPE, PROFESSION, STARTWORKDATE, STATUS, "
				+ "TEACHERCERTIFICATENO, TEACHERNO, teacher.SCHOOLID, SKELETONTYPE, ARCHIVELOCATIONID, WORKSTATUS, DEPTSORTNUM,AVATAR, u.EMAIL, "
				+ "MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, VALIDATEEMAIL, VALIDATEMOBILE, BIRTHDAY, REALNAME, LOGINNAME,u.pinyin,"
				+ "u.jianpin,d1.name as statusName,d2.name as educationName,d3.name as compileTypeName,d4.name as dgreeName,d5.name as "
				+ "skeletonTypeName,d6.name as workStatusName,d7.name as maritalStatusName,d8.name as positionalTitleName,d9.name as "
				+ "postTypeName,d10.name as sexName,school.name as schoolName FROM TEACHER teacher left join user_base u on u.id=teacher.id left join dictionary_item "
				+ "d1 on d1.id=teacher.STATUS left join dictionary_item d2 on d2.id=teacher.EDUCATION left join dictionary_item d3 on "
				+ "d3.id=teacher.COMPILETYPE left join dictionary_item d4 on d4.id=teacher.DGREE left join dictionary_item d5 on d5.id=teacher.SKELETONTYPE left join dictionary_item d6 on "
				+ "d6.id=teacher.WORKSTATUS left join dictionary_item d7 on d7.id=teacher.MARITALSTATUS left join dictionary_item d8 on "
				+ "d8.id=teacher.POSITIONALTITLE left join dictionary_item d9 on d9.id=teacher.POSTTYPE left join dictionary_item d10 on "
				+ "d10.id=u.sex left join school on school.id=teacher.schoolId  where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(teacher.getSchoolId())){
			if(teacher.getSchoolId().indexOf(",")>-1){
				sql.append(" and teacher.schoolId in ('"+StringUtils.join(teacher.getSchoolId().split(","), "','")+"') ");
			}else{
				sql.append(" and teacher.schoolId=? ");
				param.add(teacher.getSchoolId());
			}
			
		}
		if(!Strings.isNullOrEmpty(teacher.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%') ");
			param.add(teacher.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(Teacher.class);
		List list = this.executeQueryPage(sql.toString(), teacher.getBegin(), teacher.getEnd(), param.toArray(), handler);
		return list;
	}
	
	@Override
	public List<Teacher> getTeacherList(Teacher teacher) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT   u.ID, COMPILETYPE, DGREE, DGREENO, EDUCATION, EDUCATIONNO, GRADUATEDATE, "
				+ "GRADUATESCHOOL, HASCOMPILE, HASTEACHERCERTIFICATE, HIREDATE, MARITALSTATUS, POSITIONALTITLE, POSTTYPE, PROFESSION, STARTWORKDATE, STATUS, "
				+ "TEACHERCERTIFICATENO, TEACHERNO, teacher.SCHOOLID, SKELETONTYPE, ARCHIVELOCATIONID, WORKSTATUS, DEPTSORTNUM,AVATAR, EMAIL, "
				+ "MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, VALIDATEEMAIL, VALIDATEMOBILE, BIRTHDAY, REALNAME, LOGINNAME,u.pinyin,"
				+ "u.jianpin,d1.name as statusName,d2.name as educationName,d3.name as compileTypeName,d4.name as dgreeName,d5.name as "
				+ "skeletonTypeName,d6.name as workStatusName,d7.name as maritalStatusName,d8.name as positionalTitleName,d9.name as "
				+ "postTypeName,d10.name as sexName,d11.name as politicsName,d12.name as nationName FROM TEACHER teacher left join user_base u on u.id=teacher.id left join dictionary_item "
				+ "d1 on d1.id=teacher.STATUS left join dictionary_item d2 on d2.id=teacher.EDUCATION left join dictionary_item d3 on "
				+ "d3.id=teacher.COMPILETYPE left join dictionary_item d4 on d4.id=teacher.DGREE left join dictionary_item d5 on d5.id=teacher.SKELETONTYPE left join dictionary_item d6 on "
				+ "d6.id=teacher.WORKSTATUS left join dictionary_item d7 on d7.id=teacher.MARITALSTATUS left join dictionary_item d8 on "
				+ "d8.id=teacher.POSITIONALTITLE left join dictionary_item d9 on d9.id=teacher.POSTTYPE left join dictionary_item d10 on "
				+ "d10.id=u.sex left join dictionary_item d11 on d11.id=teacher.politics left join dictionary_item d12 on d12.id=u.nation");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(teacher.getSchoolId())){
			if(teacher.getSchoolId().indexOf(",")>-1){
				sql.append(" and teacher.schoolId in ("+StringUtils.join(teacher.getSchoolId().split(","), ",")+") ");
			}else{
				sql.append(" and teacher.schoolId=? ");
				param.add(teacher.getSchoolId());
			}
		}
		if(!Strings.isNullOrEmpty(teacher.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%') ");
			param.add(teacher.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(Teacher.class);
		List<Teacher> list = this.executeQuery(sql.toString(),  param.toArray(), handler);
		return list;
	}

	@Override
	public int getListPagedCount(Teacher teacher) throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from teacher t left join user_base u on u.id = t.id where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(teacher.getSchoolId())){
			if(teacher.getSchoolId().indexOf(",")>-1){
				sql.append(" and t.schoolId in ('"+StringUtils.join(teacher.getSchoolId().split(","), "','")+"') ");
			}else{
				sql.append(" and t.schoolId=? ");
				param.add(teacher.getSchoolId());
			}
		}
		if(!Strings.isNullOrEmpty(teacher.getRealName())){
			sql.append(" and REALNAME LIKE  concat(concat('%',?),'%') escape '/' ");
			param.add(teacher.getRealName());
		}
		return this.executeCount(sql.toString(), param.toArray());
	}

	@Override
	public Teacher get(String id) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT  u.ID, COMPILETYPE, DGREE, DGREENO, EDUCATION, EDUCATIONNO, GRADUATEDATE, GRADUATESCHOOL, HASCOMPILE,"
				+ " HASTEACHERCERTIFICATE, HIREDATE, MARITALSTATUS, POSITIONALTITLE, POSTTYPE, PROFESSION, STARTWORKDATE, STATUS, "
				+ "TEACHERCERTIFICATENO, TEACHERNO, teacher.SCHOOLID, SKELETONTYPE, ARCHIVELOCATIONID, WORKSTATUS, DEPTSORTNUM,AVATAR,"
				+ " u.EMAIL, MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, VALIDATEEMAIL, VALIDATEMOBILE, BIRTHDAY, REALNAME,"
				+ " LOGINNAME,u.pinyin,u.jianpin,d1.name as statusName,d2.name as educationName,d3.name as compileTypeName,d4.name as dgreeName,"
				+ "d5.name as skeletonTypeName,d6.name as workStatusName,d7.name as maritalStatusName,d8.name as positionalTitleName,"
				+ "d9.name as postTypeName,d10.name as sexName,school.id as schoolId,school.name as schoolName FROM TEACHER teacher left join user_base u on u.id=teacher.id left join"
				+ " dictionary_item d1 on d1.id=teacher.STATUS "
				+ "left join dictionary_item d2 on d2.id=teacher.EDUCATION left join dictionary_item d3 on d3.id=teacher.COMPILETYPE "
				+ "left join dictionary_item d4 on d4.id=teacher.DGREE left join dictionary_item d5 on d5.id=teacher.SKELETONTYPE "
				+ "left join dictionary_item d6 on d6.id=teacher.WORKSTATUS left join dictionary_item d7 on d7.id=teacher.MARITALSTATUS "
				+ "left join dictionary_item d8 on d8.id=teacher.POSITIONALTITLE left join dictionary_item d9 on d9.id=teacher.POSTTYPE "
				+ "left join dictionary_item d10 on d10.id=u.sex left join school on school.id=teacher.schoolId  where 1=1");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(id)){
			sql.append(" and u.ID =? ");
			param.add(id);
		}
		ResultSetHandler<Teacher> handler =  new BeanHandler(Teacher.class);
		return (Teacher) executeQueryObject(sql.toString(), param.toArray(), handler);
	}

	@Override
	public int getTeacherNoCount(String teacherNo)throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from teacher where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(teacherNo)){
			sql.append(" and teacherNo=? ");
			param.add(teacherNo);
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}

}
