package com.boyuyun.base.user.biz.impl;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.base.course.dao.CourseDao;
import com.boyuyun.base.course.dao.TeacherCourseDao;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.base.org.dao.SchoolDao;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.sys.dao.DictionaryItemDao;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.user.biz.TeacherBiz;
import com.boyuyun.base.user.dao.TeacherDao;
import com.boyuyun.base.user.dao.UserDao;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.json.ByyJsonUtil;
import com.boyuyun.common.util.ByyStringUtil;
import com.google.common.base.Strings;
@Service
public class TeacherBizImpl  implements TeacherBiz {

	@Resource
	private TeacherDao teacherDao;
	@Resource
	private TeacherCourseDao teacherCourseDao;

	@Resource
	private DictionaryItemDao dictionaryItemDao;
	
	@Resource 
	private UserDao userDao;
	@Resource 
	private SchoolDao schoolDao;
	@Resource 
	private CourseDao courseDao;
	@Resource
	private TeacherCourseDao teacherCourseMapper;


	@Override
	@Transactional(rollbackFor = Exception.class)
	@SyncTo(dataType=SyncDataType.Teacher,operateType=SyncOperateType.Add,system={"oa","wd"})
	public boolean add(Teacher teacher) throws SQLException {
		userDao.insert(teacher);
		teacherCourseMapper.deleteByTeacher(teacher.getId());
		if(teacher.getCourses()!=null){
			for(TeacherCourse cource:teacher.getCourses()){
				teacherCourseMapper.insert(cource);
			}
		}
		return teacherDao.insert(teacher);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@SyncTo(dataType=SyncDataType.Teacher,operateType=SyncOperateType.Update,system={"oa","wd"})
	public boolean update(Teacher teacher) throws SQLException {
		userDao.update(teacher);
		teacherCourseMapper.deleteByTeacher(teacher.getId());
		if(teacher.getCourses()!=null){
			for(TeacherCourse cource:teacher.getCourses()){
				teacherCourseMapper.insert(cource);
			}
		}
		return teacherDao.update(teacher);
	}

	@Override
	@SyncTo(dataType=SyncDataType.Teacher,operateType=SyncOperateType.Delete,system={"oa","wd"})
	public boolean delete(Teacher teacher) throws SQLException {
		userDao.delete(teacher);
		return teacherDao.delete(teacher);
	}

	@Override
	@SyncTo(dataType=SyncDataType.Teacher,operateType=SyncOperateType.Delete,system={"oa","wd"})
	public boolean deleteAll(List<Teacher> teachers) throws SQLException {
		for (Teacher teacher : teachers) {
			userDao.delete(teacher);
		}
		return teacherDao.deleteAll(teachers);
	}

	@Override
	public boolean addAll(List<Teacher> teachers) throws SQLException {
		for (Teacher teacher : teachers) {
			userDao.insert(teacher);
		}
		return teacherDao.addAll(teachers);
	}

	@Override
	public List<Teacher> getListNonePaged(Teacher teacher) throws SQLException {
		return teacherDao.getListNonePaged(teacher);
	}

	@Override
	public List<Teacher> getListPaged(Teacher teacher) throws SQLException {
		return teacherDao.getListPaged(teacher);
	}

	@Override
	public int getListPagedCount(Teacher teacher) throws SQLException {
		return teacherDao.getListPagedCount(teacher);
	}

	@Override
	public Teacher get(String id) throws SQLException {
		return teacherDao.get(id);
	}

	@Override
	public List<Teacher> getTeacherList(Teacher teacher) throws SQLException {
		return teacherDao.getTeacherList(teacher);
	}

	@Override
	public int getTeacherNoCount(String teacherNo) throws SQLException {
		return teacherDao.getTeacherNoCount(teacherNo);
	}

	private Map<Integer,String> getDictionaryItemArr(String code) throws Exception{
		List<DictionaryItem> list=dictionaryItemDao.getDictionaryItemList(code, "");
		Map<Integer,String> datamMap= new HashMap<>();
		if(null!=list && list.size()>0){
        	for (int i = 0; i < list.size(); i++) {
        		DictionaryItem dictionaryItem = list.get(i);
        		datamMap.put(dictionaryItem.getId(), dictionaryItem.getName());
			}
        }  
        return datamMap;
	}
	
	@Override
	@Transactional
	public String addAndUpdate(Teacher teacher, String courseList) throws SQLException {
		String result = ByyJsonUtil.getFailJson("保存失败");
		
		
		if (teacher.getId() != null && teacher.getId().trim().length() > 0) {
			// 更新
			this.update(teacher);
			result = ByyJsonUtil.getSuccessJson("修改成功");
		} else {
			// 新增教师
			String id = ByyStringUtil.getRandomUUID();
			teacher.setId(id);
			this.add(teacher);
			result = ByyJsonUtil.getSuccessJson("保存成功");
		}
		teacherCourseMapper.deleteByTeacher(teacher.getId());
		
		if (courseList != null && !"".equals(courseList)) {
			for (int i = 0; i < courseList.split(",").length; i++) {
				String courseId = courseList.split(",")[i];
				TeacherCourse teacherCourse = new TeacherCourse();
				teacherCourse.setId(ByyStringUtil.getRandomUUID());
				teacherCourse.setCourseId(courseId);
				teacherCourse.setTeacherId(teacher.getId());
				teacherCourseMapper.insert(teacherCourse);
			}
		}
		return result;
	}
	
	/**
	 * @Description 根绝map的value取得key(字典id,不用多次查询数据库)
	 * @author jms
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
	public String insertImportTeacherExcel(Map<Integer, List> contentMap,List listForIds) throws Exception {
		String msg="";
		String result="";
		Map<String, String> validTeacherNoMap = new HashMap<String, String>();
		// 取得字典
		// 性别
		Map<Integer, String> sexArr=this.getDictionaryItemArr("Gender");
		// 岗位
		Map<Integer, String> teacherPostTypeArr=this.getDictionaryItemArr("TeacherPostType");
		// 学科
		
		// 在职状态
		Map<Integer, String> workStatusArr=this.getDictionaryItemArr("WorkStatus");
		// 民族
		Map<Integer, String> nationalArr=this.getDictionaryItemArr("National");
		//政治面貌
		Map<Integer, String> politicsArr=this.getDictionaryItemArr("Politics");
		// 学历学位 
		Map<Integer, String> educationArr=this.getDictionaryItemArr("Education");
		
		//循环map
		List<Teacher> teacherList = new ArrayList<Teacher>();
		List<TeacherCourse> teacherCourses = new ArrayList<TeacherCourse>();
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,String> mobileMap = new HashMap<String,String>();
		Map<String,String> cardnoMap = new HashMap<String,String>();
		
		for(int i=1;i<=contentMap.size();i++){
			List contentList = contentMap.get(i);
			boolean hasContent = false;
			for (Object object : contentList) {
				String string = (String) object;
				if(!Strings.isNullOrEmpty(string)){
					hasContent = true;
					break;
				}  
			} 
			if (!hasContent) {
				continue;
			}
			//一个List就是一个对象，然后进行保存
			//序号
			String number = (String)contentList.get(0);
			// 学校
			String schoolName = (String)contentList.get(1);
			if(schoolName== null ||  "".equals(schoolName)){
				msg+="第"+(i+1)+"行学校不能为空!<br>";
				flag= false;
			}
			//工号
			String teacherNo = (String)contentList.get(2);
			teacherNo = teacherNo.trim();
			if(null!= teacherNo && "".equals(teacherNo)){
				msg+="第"+(i+1)+"行工号不能为空!<br>";
				flag= false;
			}else {
				if(validTeacherNoMap.get(teacherNo)!=null){
					msg+="第"+(i+1)+"行工号重复!<br>";
					flag= false;
				}
				validTeacherNoMap.put(teacherNo, "true");
				// 工号是否重复
				int count = teacherDao.getTeacherNoCount(teacherNo);
				if(count>0){
					msg+="第"+(i+1)+"行工号重复!<br>";
					flag= false;
				}
			}
			// 学校验证
			String schoolId="";
			String userName="";
			List schoolList= schoolDao.selectSchoolName(schoolName);
			if(schoolList==null || schoolList.size()==0){
				msg+="第"+(i+1)+"行该学校不存在!<br>";
				flag= false;
			}else {
				schoolId = ((School)schoolList.get(0)).getId();
				userName = teacherNo+"@"+((School)schoolList.get(0)).getSerialNumber();
			}
			
			//姓名
			String name = (String)contentList.get(3);
			if(null==name || "".equals(name.trim())){
				msg+="第"+(i+1)+"行姓名不能为空!<br>";
				flag = false;
			}
			//性别
			String sex = (String)contentList.get(4);
			int sexKey=-1;
			if(sex!=null && !"".equals(sex)){ 
				if(sexArr.containsValue(sex)){
					sexKey = this.getKeyByValue(sexArr, sex);
				}else{
					msg+="第"+(i+1)+"行性别格式不正确，请选择下拉框!<br>";
					flag = false;
				}
			}
			//岗位
			String posttype = (String)contentList.get(5);
			int posttypeKey=-1;
			if(null!=posttype && !"".equals(posttype)){ 
				if(teacherPostTypeArr.containsValue(posttype)){
					posttypeKey = this.getKeyByValue(teacherPostTypeArr, posttype);
				}else{
					msg+="第"+(i+1)+"行岗位格式不正确,请重新输入!<br>";
					flag = false;
				}
			}
			// 学科
			String couresName = (String)contentList.get(6);
			String courseId="";
			if(null!=couresName && !"".equals(couresName)){ 

				List<Course> courses= courseDao.getCourseBy(couresName);
				if(courses!=null && courses.size()>0){
					courseId = courses.get(0).getId();
				}else {
					msg+="第"+(i+1)+"行学科名称不正确,请重新输入!<br>";
					flag = false;
				}
			} 
			
			//在职状态	
			String work = (String)contentList.get(7);
			int workKey=-1;
			if(null!=work && !"".equals(work)){ 
				 if(workStatusArr.containsValue(work)){
					 workKey = this.getKeyByValue(workStatusArr, work);
				}else{
					msg+="第"+(i+1)+"行在职状态格式不正确,请重新输入!<br>";
					flag = false;
				}
			}
			
			//手机号码	
			String mobile = (String)contentList.get(8);//手机号
			if(null!=mobile && !"".equals(mobile)){
				mobile = mobile.trim();
				if(mobileMap.get(mobile)!=null){
					msg+="第"+(i+1)+"行手机号重复!<br>";
					flag = false;
				}
				mobileMap.put(mobile, mobile);
				int count= userDao.getMobileCount(mobile);
				if(count>0){
					msg+="第"+(i+1)+"行手机号重复!<br>";
					flag = false;
				}
			}
			//证件号	
			String cardno = (String)contentList.get(9);
			if(cardno!=null && !"".equals(cardno)){
				if(cardnoMap.containsKey(cardno)){
					msg+="第"+(i+1)+"行证件号重复!<br>";
					flag = false;
				} 
				cardnoMap.put(cardno, cardno);
			}
			//出生日期	
			String birthday = (String)contentList.get(10);
			Date sbirthday = null;
			if(null != birthday && !"".equals(birthday)){
				sbirthday = sdf.parse(birthday);
			}
			//民族	
			String national = (String)contentList.get(11);
			int nationalKey=-1;
			if(null!=national && !"".equals(national)){ 
				 if(nationalArr.containsValue(national)){
					 nationalKey = this.getKeyByValue(nationalArr, national);
				}else{
					msg+="第"+(i+1)+"行民族不正确,请重新输入!<br>";
					flag = false;
				}
			}
			//政治面貌	
			String zhengzhi = (String)contentList.get(12);
			int zhengzhiKey=-1;
			if(null!=zhengzhi && !"".equals(zhengzhi)){ 
				 if(politicsArr.containsValue(zhengzhi)){
					 zhengzhiKey =this.getKeyByValue(politicsArr, zhengzhi);
				}else{
					msg+="第"+(i+1)+"行政治面貌不正确,请重新输入!<br>";
					flag = false;
				}
			}
			//毕业学校	
			String schoolnameby = (String)contentList.get(13);
			//学历学位	
			String xuewei = (String)contentList.get(14);
			int xueweiKey=-1;
			if(null!=xuewei && !"".equals(xuewei)){ 
				 if(educationArr.containsValue(xuewei)){
					 xueweiKey = this.getKeyByValue(educationArr, xuewei);
				}else{
					msg+="第"+(i+1)+"行学历学位不正确,请重新输入!<br>";
					flag = false;
				}
			}
			//加入本校时间
			String schoolDate = (String)contentList.get(15);
			Date sbschoolDate = null;
			if(null != schoolDate && !"".equals(schoolDate)){
				sbschoolDate = sdf.parse(schoolDate);
			}
			Teacher tempTeacher = new Teacher();
			tempTeacher.setTeacherNo(teacherNo);
			tempTeacher.setRealName(name); 
			tempTeacher.setSchoolId(schoolId);
			if(posttypeKey!=-1){
				tempTeacher.setPostType(posttypeKey);
			}
			//tempTeacher.setcc
			if(workKey!=-1){
				tempTeacher.setWorkStatus(workKey);
			}
			tempTeacher.setCertificateNo(cardno);
			tempTeacher.setBirthday(sbirthday);
			if(nationalKey!=-1){
				tempTeacher.setNation(nationalKey);
			}
			if(zhengzhiKey!=-1){
				tempTeacher.setPolitics(zhengzhiKey);
			} 
			tempTeacher.setGraduateSchool(schoolnameby);
			if(xueweiKey!=-1){
				tempTeacher.setEducation(xueweiKey);
			}  
			tempTeacher.setHireDate(sbschoolDate);
			String id=ByyStringUtil.getRandomUUID().replace("-", "");
			tempTeacher.setId(id);
			listForIds.add(id);
			// user
			String pwd ="f379eaf3c831b04de153469d1bec345e";
			tempTeacher.setUserName(userName);
			tempTeacher.setPwd(pwd); 
			if(sexKey!=-1){
				tempTeacher.setSex(sexKey);
			}   
			tempTeacher.setEnable(true);
			tempTeacher.setMobile(mobile);  
			tempTeacher.setUserType("教师");
			teacherList.add(tempTeacher);
			
			// 任教
			TeacherCourse teacherCourse = new TeacherCourse();
			teacherCourse.setId(ByyStringUtil.getRandomUUID().replace("-", ""));
			teacherCourse.setCourseId(courseId);
			teacherCourse.setTeacherId(id);
			teacherCourses.add(teacherCourse);
			 
		}
		if(flag){
			for (int j = 0; j < teacherList.size(); j++) {
				Teacher teacher = teacherList.get(j);
				TeacherCourse teacherCourse = teacherCourses.get(j);
				userDao.insert(teacher);
				teacherDao.insert(teacher);
				if(teacherCourse.getCourseId()!=null && !"".equals(teacherCourse.getCourseId())){
					teacherCourseDao.insert(teacherCourse);
				}
				
			} 
		}else {
			result = msg;
		}
		
		return result;
	}   
	
}
