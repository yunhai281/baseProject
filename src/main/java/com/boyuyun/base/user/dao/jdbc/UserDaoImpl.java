package com.boyuyun.base.user.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.Student;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.consts.UserType;
import com.dhcc.common.db.BaseDAO;
import com.google.common.base.Strings;
@Repository
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserDaoImpl extends BaseDAO implements UserDao {

	public User selectByUserName(String username,String password,UserType type) throws SQLException {
		String sql = "select * from user_base where  userName =? and pwd = ? and usertype=?";
		return (User) this.executeQueryObject(sql, new Object[]{username,password,type.ordinal()},new BeanHandler(User.class));
	}

	@Override
	public User get(String id) throws SQLException {
		String sql = "select * from user_base where id=?";
		return (User) this.executeQueryObject(sql, new Object[]{id},new BeanHandler(User.class));
	}
	    
	@Override
	public boolean updatePassword(String id,String pwd)throws SQLException 
	{
		String sql="update USER_BASE set PWD =? where ID =?";
		Object[] param = new Object[]{pwd,id};
		return this.executeUpdate(sql, param)>0;
	}

	@Override
	public boolean update(User user) throws SQLException {
		String sql = "update USER_BASE set AVATAR = ?,EMAIL = ?,MOBILE = ?,NICKNAME = ?,pwd=?,SEX = ?,"
				+ "ENABLE = ?,USERNAME = ?,USERTYPE =?,VALIDATEEMAIL = ?"
				+ ",VALIDATEMOBILE = ?,BIRTHDAY =?,REALNAME = ?,LOGINNAME =? ,pinyin = ?,jianpin =?,nation=?,certificateNo=? where ID = ?";
		Object[] param = new Object[]{
				user.getAvatar(),
				user.getEmail(),
				user.getMobile(),
				user.getNickName(),
				user.getPwd(),
				user.getSex(),
				user.isEnable(),
				user.getUserName(),
				user.getUserType(),
				user.isValidateEmail(),
				user.isValidateMobile(),
				user.getBirthday(),
				user.getRealName(),
				user.getLoginName(),
				user.getPinyin(),
				user.getJianpin(),
				user.getNation(),
				user.getCertificateNo(),
				user.getId()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean insert(User user) throws SQLException {
		String sql = " insert into USER_BASE (ID, AVATAR, EMAIL, MOBILE, NICKNAME, PWD, SEX, ENABLE, USERNAME, USERTYPE, "
				+ "VALIDATEEMAIL, VALIDATEMOBILE, BIRTHDAY, REALNAME, LOGINNAME,pinyin,jianpin,nation,certificateNo)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		Object[] param = new Object[]{
				user.getId(),
				user.getAvatar(),
				user.getEmail(),
				user.getMobile(),
				user.getNickName(),
				user.getPwd(),
				user.getSex(),
				user.isEnable(),
				user.getUserName(),
				user.getUserType(),
				user.isValidateEmail(),
				user.isValidateMobile(),
				user.getBirthday(),
				user.getRealName(),
				user.getLoginName(),
				user.getPinyin(),
				user.getJianpin(),
				user.getNation(),
				user.getCertificateNo()
		};
		int i = this.executeUpdate(sql, param);
		return i>0;
	}

	@Override
	public boolean delete(User user) throws SQLException {
		String sql = "delete from user_base where id=?";
		int i = this.executeUpdate(sql, new String[]{user.getId()});
		return i>0;
	}

	/**
	 * Description 手机号是否重复
	 * @param mobile
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.user.dao.UserDao#getMobileCount(java.lang.String)
	 */
	@Override
	public int getMobileCount(String mobile)throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from user_base where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(mobile)){
			sql.append(" and mobile=? ");
			param.add(mobile);
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}
	
	/**
	 * @Description 邮箱是否重复
	 * @author jms
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int getEmailCount(String email)throws SQLException {
		StringBuffer sql = new StringBuffer("select count(1) from user_base where 1=1 ");
		List param = new ArrayList();
		if(!Strings.isNullOrEmpty(email)){
			sql.append(" and email=? ");
			param.add(email);
		} 
		return this.executeCount(sql.toString(), param.toArray());
	}
	
	@Override
	public List<User> getUserList(String schoolId) throws SQLException {
		String sql="select * from user_base where id in (select id FROM student WHERE schoolId = ? UNION select id FROM teacher where id = ?) "; 
		
		List param = new ArrayList();
		param.add(schoolId);
		param.add(schoolId);
		ResultSetHandler handler = new BeanListHandler(User.class);
		return this.executeQuery(sql,param.toArray(), handler);
	} 
	
	/**
	 * Description 查询管理员
	 * @param user
	 * @return
	 * @throws SQLException 
	 * @see com.boyuyun.base.user.dao.UserDao#getAdminList(com.boyuyun.base.user.entity.User)
	 */
	@Override
	public List<User> getListPagedAdmin(User user) throws SQLException {
		String sql="select u.*,d1.name as sexName from user_base u" +
				" left join dictionary_item d1 on d1.id=u.sex  " +
				" where u.userType =5 ";
		List paramList = new ArrayList(); 
		if (!Strings.isNullOrEmpty(user.getUserName())) {
			sql += " and u.userName like concat(concat('%',?),'%') ";
			paramList.add(user.getUserName());
		}
		if (!Strings.isNullOrEmpty(user.getRealName())) {
			sql += " and u.realName like concat(concat('%',?),'%') ";
			paramList.add(user.getRealName());
		}
		ResultSetHandler handler = new BeanListHandler(User.class);
		return this.executeQueryPage(sql.toString(), user.getBegin(), user.getEnd(), paramList.toArray(), handler);
	} 
	
	@Override
	public int getListPagedCountAdmin(User user)throws SQLException {
		String sql="select count(1) from user_base where userType =5 ";
		List paramList = new ArrayList(); 
		if (!Strings.isNullOrEmpty(user.getUserName())) {
			sql += " and userName like concat(concat('%',?),'%') ";
			paramList.add(user.getUserName());
		}
		if (!Strings.isNullOrEmpty(user.getRealName())) {
			sql += " and realName like concat(concat('%',?),'%') ";
			paramList.add(user.getRealName());
		} 
		return this.executeCount(sql.toString(), paramList.toArray());
	}

	@Override
	public boolean deleteAll(List<User> users) throws SQLException {
		boolean result = true;
		if(users.size()>0){
			for (User user : users) {
				result&=delete(user);
			}
		}
		return result;
	} 
	
	public Integer validateUserName(String userName,String id) throws SQLException {
		String sql="select count(*) from user_base where userName=? and id!=?"; 
		
		List param = new ArrayList();
		param.add(userName);  
		param.add(id);  
		return this.executeCount(sql,param.toArray());
	} 
}
