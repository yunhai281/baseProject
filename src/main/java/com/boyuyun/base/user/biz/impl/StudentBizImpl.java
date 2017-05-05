package com.boyuyun.base.user.biz.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.org.dao.SchoolClassDao;
import com.boyuyun.base.org.dao.SchoolDao;
import com.boyuyun.base.org.dao.SchoolGradeDao;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolClass;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.dao.GradeDao;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.sys.entity.Grade;
import com.boyuyun.base.user.biz.StudentBiz;
import com.boyuyun.base.user.dao.StudentDao;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.Student;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.consts.Stage;
import com.boyuyun.base.util.consts.UserType;
import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.ByyStringUtil;
import com.dhcc.common.util.StringUtil;
import com.google.common.base.Strings;
@Service
public class StudentBizImpl  implements StudentBiz {

	@Resource
	private DictionaryItemDao dictionaryItemDao;
	@Resource
	private StudentDao studentDao;
	@Resource 
	private UserDao userDao;
	@Resource 
	private SchoolDao schoolDao;
	@Resource
	private SchoolGradeDao schoolGradeDao;
	@Resource
	private SchoolClassDao schoolClassDao;
	@Resource
	private GradeDao gradeDao;
	
	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Student,operateType=SyncOperateType.Add,system={"wd"})
	public boolean add(Student student) throws SQLException {
		userDao.insert(student);
		return studentDao.insert(student);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Student,operateType=SyncOperateType.Update,system={"wd"})
	public boolean update(Student student) throws SQLException {
		userDao.update(student);
		return studentDao.update(student);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Student,operateType=SyncOperateType.Delete,system={"wd"})
	public boolean delete(Student student) throws SQLException {
		userDao.delete(student);
		return studentDao.delete(student);
	}

	@Override
	@Transactional
	@SyncTo(dataType=SyncDataType.Student,operateType=SyncOperateType.Delete,system={"wd"})
	public boolean deleteAll(List<Student> students) throws SQLException {
		for (Student student : students) {
			userDao.delete(student);
		}
		return studentDao.deleteAll(students);
	}

	@Override
	public List<Student> getListNonePaged(Student student) throws SQLException {
		return studentDao.getListNonePaged(student);
	}

	@Override
	public List<Student> getListPaged(Student student) throws SQLException {
		return studentDao.getListPaged(student);
	}

	@Override
	public int getListPagedCount(Student student) throws SQLException {
		return studentDao.getListPagedCount(student);
	}

	@Override
	public Student get(String id) throws SQLException {
		return studentDao.get(id);
	}

	/**
	 * 根据code值获取字典值
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, String> getDictionaryItemArr(String code) throws Exception {
		List<DictionaryItem> list = dictionaryItemDao.getDictionaryItemList(code, "");
		Map<Integer, String> datamMap = new HashMap<>();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				DictionaryItem dictionaryItem = list.get(i);
				// stringArr[i] = dictionaryItem.getName();
				datamMap.put(dictionaryItem.getId(), dictionaryItem.getName());
			}
		}
		return datamMap;
	}
	
	/**
	 * @Description 根据map的value取得key(字典id,不用多次查询数据库)
	 * @param map
	 * @param value
	 * @return
	 */
	 public Integer getKeyByValue(Map<Integer,String> map, String value)throws Exception  {  
        int keys=-1;  
        Set<Integer>kset=map.keySet();
        for(Integer ks:kset){
            if(value.equals(map.get(ks))){
            	keys=ks;
            	break;
            }
        } 
        return keys;  
     }	
	 
	 
	@Override
	@Transactional
	public String insertImportStudentExcel(Map<Integer, List> contentMap,List listForIds) throws Exception {
		String msg="";
		String result="";	
		
		// 取得字典
		// 性别
		Map<Integer, String> sexArr = getDictionaryItemArr("Gender");
		// 民族
		Map<Integer, String> nationalArr = getDictionaryItemArr("National");
        //入学方式
		Map<Integer, String> entryTypeArr = getDictionaryItemArr("EntryType");
		//就读方式
		Map<Integer, String> studyTypeArr = getDictionaryItemArr("StudyType");
		//学习状态
		Map<Integer, String> studentStatusArr = getDictionaryItemArr("StudentStatus");
		
		// 循环map
		List<Student> studentList = new ArrayList<Student>();
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map mobileMap = new HashMap();
		for (int i = 1; i <= contentMap.size(); i++) {
			List contentList = contentMap.get(i);
			boolean hasContent = false;
			for (Object object : contentList) {
				String string = (String) object;
				if (!Strings.isNullOrEmpty(string)) {
				  hasContent = true; 
				  break; 
				  }
			}
			if (!hasContent) {
				continue;
			}
			// 一个List就是一个对象，然后进行保存
			//学校名
			String schoolName = (String)contentList.get(0);
			if(schoolName== null ||  "".equals(schoolName)){
				msg+="第"+(i+1)+"行学校不能为空!<br>";
				flag= false;
			}	
			// 学号
			String studentNo = (String) contentList.get(1);
			if(studentNo== null ||  "".equals(studentNo)){
				msg+="第"+(i+1)+"行学号不能为空!<br>";
				flag= false;
			}			
			//学籍号
			String xueJiNo = (String)contentList.get(2);
			if(xueJiNo== null ||  "".equals(xueJiNo)){
				msg+="第"+(i+1)+"行学籍号不能为空!<br>";
				flag= false;
			}
			//真实姓名
			String realName = (String)contentList.get(3);
			if(null==realName || "".equals(realName.trim())){
				msg+="第"+(i+1)+"行姓名不能为空!<br>";
				flag = false;
			}
			//性别
			String sex = (String)contentList.get(4);
			if(sex== null ||  "".equals(sex)){
				msg+="第"+(i+1)+"行性别不能为空!<br>";
				flag= false;
			}
			int sexKey=-1;
			if(null==sex){ 
				msg+="第"+(i+1)+"行性别格式不正确，请选择下拉框!<br>";
				flag = false;
			}else {
				if(sexArr.containsValue(sex)){
					sexKey = this.getKeyByValue(sexArr, sex);
				}else{
					msg+="第"+(i+1)+"行性别格式不正确，请选择下拉框!<br>";
					flag = false;
				}
			}
			//学段
			String stage = (String)contentList.get(5);
			if(stage== null ||  "".equals(stage)){
				msg+="第"+(i+1)+"行学段不能为空!<br>";
				flag= false;
			}			
			//年级
			String gradeName = (String)contentList.get(6);
			//班级
			String className = (String)contentList.get(7);
			//手机号码
			String mobile = (String)contentList.get(8);
			//证件号码
			String certificateNo = (String)contentList.get(9);
			// 出生日期
			String birthday = (String) contentList.get(10);
			Date sbirthday = null;
			if (null != birthday && !"".equals(birthday)) {
				sbirthday = sdf.parse(birthday);
			}
			// 民族
			String national = (String) contentList.get(11);
			int nationalKey=-1;
			if(null!=national){ 
				 if(nationalArr.containsValue(national)){
					 nationalKey = this.getKeyByValue(nationalArr, national);
				}else{
					msg+="第"+(i+1)+"行民族不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//入学日期
			String entryDate = (String) contentList.get(12);
			Date sentryDate = null;
			if (null != entryDate && !"".equals(entryDate)) {
				sentryDate = sdf.parse(entryDate);
			}
			//入学方式
			String entryType = (String) contentList.get(13);
			int entryTypeKey=-1;
			if(null!=entryType){ 
				 if(entryTypeArr.containsValue(entryType)){
					 entryTypeKey = this.getKeyByValue(entryTypeArr, entryType);
				}else{
					msg+="第"+(i+1)+"行入学方式不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//就读方式
			String studyType = (String) contentList.get(14);
			int studyTypeKey=-1;
			if(null!=studyType){ 
				 if(studyTypeArr.containsValue(studyType)){
					 studyTypeKey = this.getKeyByValue(studyTypeArr, studyType);
				}else{
					msg+="第"+(i+1)+"行就读方式不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//学习状态
			String status = (String) contentList.get(15);
			int statusKey=-1;
			if(null!=status){ 
				 if(studentStatusArr.containsValue(status)){
					 statusKey = this.getKeyByValue(studentStatusArr, status);
				}else{
					msg+="第"+(i+1)+"行学习状态不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//处理获取信息
			// 学校验证
			String schoolId="";
			String userName="";
			List schoolList= schoolDao.selectSchoolByName(schoolName);
			if(schoolList==null || schoolList.size()==0){
				msg+="第"+(i+1)+"行该学校不存在!<br>";
				flag= false;
			}else {
				//所属学校
				schoolId = ((School)schoolList.get(0)).getId();
				//用户名
				userName = studentNo+"@"+((School)schoolList.get(0)).getSerialNumber();
			}	
			//处理枚举获取gradeId和classId
			// 学段
			Stage stages = Stage.valueOf(stage);
			Integer bstage = stages.ordinal();
//			Student stu = new Student();
//			stu.setSchoolId(schoolId);
//			stu.setBelongGradeName(gradeName);
//			SchoolGrade grade = studentDao.get(stu, bstage);
			//处理所属年级 根据学校和Excel中的年级
			SchoolGrade grade =schoolGradeDao.get(gradeName, schoolId);
			String belonggradeId = null;
			if(grade==null){
				msg+="第"+(i+1)+"行学段年级对应错误，请重新检查!<br>";
				flag = false;
			}else{
				belonggradeId = grade.getId();
			}
			
			SchoolClass clasz = new SchoolClass();
			clasz.setSchoolId(schoolId);
			clasz.setGradeId(belonggradeId);
			clasz.setName(className);
			SchoolClass clazz = schoolClassDao.get(clasz);
			String belongclassId = null;
			if (clazz == null) {
				msg+="第"+(i+1)+"行年级班级对应错误，请重新检查!<br>";
				flag = false;
			}else{
				belongclassId = clazz.getId();
			}
			//执行导入
			Student student =  new Student();
			String id = ByyStringUtil.getRandomUUID().replace("-", "");
			student.setId(id);//主键
			listForIds.add(id);//用于返回
			student.setUserName(userName);//登录名
			student.setRealName(realName);//真名
			Integer usertype = UserType.学生.ordinal();
			student.setUserType(usertype.toString());//用户类型
			student.setSchoolId(schoolId);//学校
			student.setBelongGradeId(belonggradeId);//年级
			student.setBelongClassId(belongclassId);//班级
			student.setStudentNo(studentNo);//学号
			student.setXueJiNo(xueJiNo);//学籍号
			student.setBirthday(sbirthday);//生日
			student.setEntryDate(sentryDate);//入学日期
			student.setStudyType(studyTypeKey);//就读方式
			student.setEntryType(entryTypeKey);//入学方式
			student.setStatus(statusKey);//学习状态
			student.setNation(nationalKey);//民族
			student.setSex(sexKey);//性别
			student.setMobile(mobile);//手机号
			student.setCertificateNo(certificateNo);//证件号
			String pwd = StringUtil.encryptMd5(ConstantUtil.USER_DEFAULT_PASSWORD);//默认密码666666
		    student.setPwd(pwd);//密码
			studentList.add(student);
		}
		if(flag){
			for (Iterator iterator = studentList.iterator(); iterator.hasNext();) {
				Student student = (Student) iterator.next();
				userDao.insert(student);
				studentDao.insert(student);
			} 
			result = "导入成功!";//ByyJsonUtil.getSucessJson("导入成功!");
		}else {
			result = msg;//ByyJsonUtil.getFailJson(msg);
		}	
		return result;
	}
	/**
	 * 国家学籍数据模板
	 * 根据学校的机构代码获取学校
	 * 学习状态模板中没有，默认为字典中的第一个
	 * */
	@Override
	@Transactional
	public String importNationalStudentExcel(Map<Integer, List> contentMap,List listForIds) throws Exception {
		String msg="";
		String result="";	
		// 是否给学校创建对应于Excel中的年级和班级
		boolean create = true;
		// 取得字典
		// 性别
		Map<Integer, String> sexArr = getDictionaryItemArr("Gender");
		// 民族
		Map<Integer, String> nationalArr = getDictionaryItemArr("National");
        //入学方式
		Map<Integer, String> entryTypeArr = getDictionaryItemArr("EntryType");
		//就读方式
		Map<Integer, String> studyTypeArr = getDictionaryItemArr("StudyType");
		//学习状态
//		Map<Integer, String> studentStatusArr = getDictionaryItemArr("StudentStatus");
		List<DictionaryItem> studentStatusList = dictionaryItemDao.getDictionaryItemList("StudentStatus", "");
		//班级状态，创建班级时默认为第一个状态
		List<DictionaryItem> classStatusList = dictionaryItemDao.getDictionaryItemList("ClassStatus", "");
		
		// 循环map
		List<Student> studentList = new ArrayList<Student>();
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map mobileMap = new HashMap();
		for (int i = 1; i <= contentMap.size(); i++) {
			List contentList = contentMap.get(i);
			boolean hasContent = false;
			for (Object object : contentList) {
				String string = (String) object;
				if (!Strings.isNullOrEmpty(string)) {
				  hasContent = true; 
				  break; 
				  }
			}
			if (!hasContent) {
				continue;
			}
			// 一个List就是一个对象，然后进行保存
			//学校名
//			String schoolName = (String)contentList.get(8);
//			if(schoolName== null ||  "".equals(schoolName)){
//				msg+="第"+(i+1)+"行学校不能为空!<br>";
//				flag= false;
//			}	
			//学校机构代码
			String schoolCode = (String) contentList.get(9);
			
			
			// 学号 模板中没有数据
			String studentNo = (String) contentList.get(37);
			if(studentNo== null ||  "".equals(studentNo)){
				msg+="第"+(i+1)+"行学号不能为空!<br>";
				flag= false;
			}			
			//学籍号
			String xueJiNo = (String)contentList.get(1);
			if(xueJiNo== null ||  "".equals(xueJiNo)){
				msg+="第"+(i+1)+"行学籍号不能为空!<br>";
				flag= false;
			}
			//真实姓名
			String realName = (String)contentList.get(0);
			if(null==realName || "".equals(realName.trim())){
				msg+="第"+(i+1)+"行姓名不能为空!<br>";
				flag = false;
			}
			//性别
			String sex = (String)contentList.get(3);
			int sexKey = -1;
			if (null == sex || "".equals(sex)) {
				msg += "第" + (i + 1) + "行性别格式不正确，请选择下拉框!<br>";
				flag = false;
			} else {
				if(sexArr.containsValue(sex)){
					sexKey = this.getKeyByValue(sexArr, sex);
				}else{
					msg+="第"+(i+1)+"行性别格式不正确，请选择下拉框!<br>";
					flag = false;
				}
			}
//			学段 根据年级的名称来判断学段
			String stage = (String)contentList.get(14);
			Stage[] stageValues = Stage.values();
			for (int s = 0; s < stageValues.length; s++) {
				String name = stageValues[s].name();
				if (stage.indexOf(name) > -1) {
					stage = name;
					break;
				}
			}
//			 学段
			Stage stages = Stage.valueOf(stage);
			Integer bstage = stages.ordinal();
			
			//年级
			String gradeName = (String)contentList.get(14);
			//班级
			String className = (String)contentList.get(15);
			//手机号码有一个家长1电话，家长2电话和联系电话，目前用的是联系电话
			String mobile = (String)contentList.get(7);
			//证件号码
			String certificateNo = (String)contentList.get(2);
			// 出生日期
			String birthday = (String) contentList.get(10);
			Date sbirthday = null;
			if (null != birthday && !"".equals(birthday)) {
				sbirthday = sdf.parse(birthday);
			}
			// 民族
			String national = (String) contentList.get(5);
			int nationalKey=-1;
			if(null!=national){ 
				 if(nationalArr.containsValue(national)){
					 nationalKey = this.getKeyByValue(nationalArr, national);
				}else{
					msg+="第"+(i+1)+"行民族不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//入学日期
//			String entryDate = (String) contentList.get(21);
//			Date sentryDate = null;
//			if (null != entryDate && !"".equals(entryDate)) {
//				sentryDate = sdf.parse(entryDate);
//			}
			//入学方式
			String entryType = (String) contentList.get(22);
			int entryTypeKey=-1;
			if(null!=entryType){ 
				 if(entryTypeArr.containsValue(entryType)){
					 entryTypeKey = this.getKeyByValue(entryTypeArr, entryType);
				}else{
					msg+="第"+(i+1)+"行入学方式不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//就读方式
			String studyType = (String) contentList.get(23);
			int studyTypeKey=-1;
			if(null!=studyType){ 
				 if(studyTypeArr.containsValue(studyType)){
					 studyTypeKey = this.getKeyByValue(studyTypeArr, studyType);
				}else{
					msg+="第"+(i+1)+"行就读方式不正确,请重新输入!<br>";
					flag = false;
				}
			}			
			//学习状态 模板中没有
			int statusKey=studentStatusList.get(0).getId();
			//处理学校
			String schoolId="";
			String userName="";
			schoolDao.getSchoolByCode(schoolCode);
			School schoolByCode = schoolDao.getSchoolByCode(schoolCode);
			if (schoolByCode == null) {
				msg+="第"+(i+1)+"行该学校不存在!<br>";
				flag= false;
			}else {
				schoolId=schoolByCode.getId();
				userName = studentNo+"@"+schoolByCode.getSerialNumber();
			}
			

			//处理所属年级 根据学校和Excel中的年级
			SchoolGrade grade =schoolGradeDao.get(gradeName, schoolId);
			String belonggradeId = null;
			if(grade==null){
				//根据Excel，新建一个SchoolGrade并插入到数据库中
				if(create){
					belonggradeId=ByyStringUtil.getRandomUUID();
					grade=new SchoolGrade();
					grade.setId(belonggradeId);
					grade.setName(gradeName);
					grade.setShortName(gradeName);
					grade.setSchoolId(schoolId);
					Grade sysGrade = gradeDao.get(gradeName, bstage);
					grade.setSysGradeId(sysGrade.getId());
					schoolGradeDao.insert(grade);
				}else {
					msg+="第"+(i+1)+"行学段年级对应错误，请重新检查!<br>";
					flag = false;
				}
			}else{
				belonggradeId = grade.getId();
			}
			
			SchoolClass clasz = new SchoolClass();
			clasz.setSchoolId(schoolId);
			clasz.setGradeId(belonggradeId);
			clasz.setName(className);
			SchoolClass clazz = schoolClassDao.get(clasz);
			String belongclassId = null;
			if(clazz == null){
				if (create) {
					belongclassId=ByyStringUtil.getRandomUUID();
					clazz=new SchoolClass();
					clazz.setId(belongclassId);
					clazz.setSchoolId(schoolId);
					clazz.setGradeId(belonggradeId);
					clazz.setName(className);
					clazz.setShortName(className);
					clazz.setStageId(bstage.toString());
					clazz.setStatus(classStatusList.get(0).getId());
					schoolClassDao.insert(clazz);
				} else {
					msg += "第" + (i + 1) + "行年级班级对应错误，请重新检查!<br>";
					flag = false;
				}
			}else{
				belongclassId = clazz.getId();
			}
			//执行导入
			Student student =  new Student();
			String id = ByyStringUtil.getRandomUUID().replace("-", "");
			student.setId(id);//主键
			listForIds.add(id);//设置上id
			student.setUserName(userName);//登录名
			student.setRealName(realName);//真名
			Integer usertype = UserType.学生.ordinal();
			student.setUserType(usertype.toString());//用户类型
			student.setSchoolId(schoolId);//学校
			student.setBelongGradeId(belonggradeId);//年级
			student.setBelongClassId(belongclassId);//班级
			student.setStudentNo(studentNo);//学号
			student.setXueJiNo(xueJiNo);//学籍号
			student.setBirthday(sbirthday);//生日
//			student.setEntryDate(sentryDate);//入学日期
			student.setStudyType(studyTypeKey);//就读方式
			student.setEntryType(entryTypeKey);//入学方式
			student.setStatus(statusKey);//学习状态
			student.setNation(nationalKey);//民族
			student.setSex(sexKey);//性别
			student.setMobile(mobile);//手机号
			student.setCertificateNo(certificateNo);//证件号
			String pwd = StringUtil.encryptMd5(ConstantUtil.USER_DEFAULT_PASSWORD);//默认密码666666
		    student.setPwd(pwd);//密码
			studentList.add(student);
		}
		if(flag){
			for (Iterator iterator = studentList.iterator(); iterator.hasNext();) {
				Student student = (Student) iterator.next();
				userDao.insert(student);
				studentDao.insert(student);
			} 
			result = "导入成功!";//ByyJsonUtil.getSucessJson("导入成功!");
		}else {
			result = msg;//ByyJsonUtil.getFailJson(msg);
		}	
		return result;
	}
}
