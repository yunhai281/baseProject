package com.boyuyun.common.datasync.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boyuyun.base.course.biz.CourseBiz;
import com.boyuyun.base.course.entity.Course;
import com.boyuyun.base.course.entity.TeacherCourse;
import com.boyuyun.base.org.biz.GovernmentBiz;
import com.boyuyun.base.org.biz.SchoolGradeBiz;
import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.org.entity.SchoolGrade;
import com.boyuyun.base.sys.biz.AreaBiz;
import com.boyuyun.base.sys.entity.Area;
import com.boyuyun.base.user.entity.Parent;
import com.boyuyun.base.user.entity.Student;
import com.boyuyun.base.user.entity.StudentParentRelation;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.common.datasync.DataSyncHandler;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.datasync.wdcloud.common.BYYClientWdToken;
import com.boyuyun.common.util.PropertiesUtils;
import com.boyuyun.common.util.context.ApplicationContextHolder;
import com.google.common.base.Strings;
import com.wdcloud.openapi.OpenApi;
import com.wdcloud.openapi.OpenApiException;
import com.wdcloud.openapi.WdcloudToken;

public class WDDataSyncHandler implements DataSyncHandler {
	public static String serverName = null; // 服务地址
	public static String appId = null; // 应用ID
	public static String appSecret = null; // 应用密钥
	public static boolean enableSync = true;
	public static BYYClientWdToken byyClientWdToken = null;

	public static final Logger log = Logger.getLogger(WDDataSyncHandler.class);

	static {
		enableSync = ("true".equals( PropertiesUtils.getProperties().getProperty("wdcloud.enableSync")!=null?PropertiesUtils.getProperties().getProperty("wdcloud.enableSync").trim():"" ))?true:false;
		serverName = PropertiesUtils.getProperties().getProperty("wdcloud.serverName");
		appId = PropertiesUtils.getProperties().getProperty("wdcloud.appId");
		appSecret = PropertiesUtils.getProperties().getProperty("wdcloud.appSecret");
	}
	@Override
	public String getSystemName() {
		return "wd";
	}

	@Override
	public void handler(SyncDataType dtype, SyncOperateType otype, Object [] obj) {
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			syncSchool((School)obj[0],"A");
		}
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			syncSchool((School)obj[0],"U");
		}
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			syncSchool((School)obj[0],"D");
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			syncGovernment((Government)obj[0],"A");
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			syncGovernment((Government)obj[0],"U");
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			syncGovernment((Government)obj[0],"D");
		}
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			syncTeacher((Teacher)obj[0],"A");
		}
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			syncTeacher((Teacher)obj[0],"U");
		}
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			if (obj[0] instanceof List) {
				for(Teacher t:(List<Teacher>)obj[0]){
					syncTeacher(t,"D");
				}
			}else{
				syncTeacher((Teacher)obj[0],"D");
			}
		}
		
		if(SyncDataType.Student.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			syncStudent((Student)obj[0],"A");
		}
		if(SyncDataType.Student.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			syncStudent((Student)obj[0],"U");
		}
		if(SyncDataType.Student.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			if (obj[0] instanceof List) {
				for(Student t:(List<Student>)obj[0]){
					syncStudent(t,"D");
				}
			}else{
				syncStudent((Student)obj[0],"D");
			}
		}
		
		if(SyncDataType.Parent.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			syncParent((Parent)obj[0],"A");
		}
		if(SyncDataType.Parent.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			syncParent((Parent)obj[0],"U");
		}
		if(SyncDataType.Parent.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			if (obj[0] instanceof List) {
				for(Parent t:(List<Parent>)obj[0]){
					syncParent(t,"D");
				}
			}else{
				syncParent((Parent)obj[0],"D");
			}
		}
	}
	
	
	/**
	 * 同步机构信息
	 * @param government
	 * @param czbs  操作标示(A-新增;U-修改;D-删除)
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午3:04:14
	 */
	public static void syncGovernment(Government government,String czbs)throws OpenApiException{
		if(!enableSync){
			log.error("与伟东云同步接口已关闭");
			return;
		}
		if(government==null){
			return;
		}
		Assert.notNull(czbs);
		String apiName = "12study/syncOrg";
		Map<String, String> params = new HashMap<String, String>();
		params.put("jgmc", government.getName());	//机构名称
		try{
			if(!Strings.isNullOrEmpty(government.getAreaId())){
				Area area = ApplicationContextHolder.getBean(AreaBiz.class).get(government.getAreaId());
				params.put("xzqh", area!=null?area.getId():"");	//行政区划code
				params.put("jglb", area!=null?("0"+area.getLevelType()):"");	//机构类型(01-省;02-市;03-区)
			}else{
				params.put("xzqh", "");	//行政区划code
				params.put("jglb", "");	//机构类型(01-省;02-市;03-区)
			}
			
			//params.put("xzqh", government.getAreaId()!=null?government.getArea().getId():"");	//行政区划code
			//params.put("jglb", government.getArea()!=null?("0"+government.getArea().getLevelType()):"");	//机构类型(01-省;02-市;03-区)
			if(!Strings.isNullOrEmpty(government.getParentId())){
				Government t = ApplicationContextHolder.getBean(GovernmentBiz.class).get(government.getParentId());
				if(t!=null){
					Area area = ApplicationContextHolder.getBean(AreaBiz.class).get(t.getAreaId());
					params.put("sjjgxzqh", area!=null? area.getId():"");	//上级机构行政区划code
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		params.put("czbs", czbs);	//操作标示(A-新增;U-修改;D-删除)
		params.put("appid", appId);
		
		System.out.println("params:"+params);
		if(log.isDebugEnabled()){
			log.debug("params:"+params);
		}
		JSONObject resultObject = requestApi(apiName, params,JSONObject.class);
		if(log.isDebugEnabled()){
			log.debug("resultObject:"+resultObject);
		}
		if (resultObject != null) {
			if(!resultObject.getBooleanValue("isSuccess")){
				String errormsg = "与伟东云同步机构信息失败,操作标识:"+czbs+",机构id="+government.getId()+",机构名称="+government.getName()+",错误信息:"+resultObject.getString("msgCode");
				log.error(errormsg);
				if(!(resultObject.getString("msgCode")!=null && resultObject.getString("msgCode").indexOf("已同步")>-1)){
					throw new RuntimeException(errormsg);
				}
			}
		}else{
			throw new RuntimeException("与伟东云同步机构信息失败,操作标识:"+czbs+",机构id="+government.getId()+",机构名称="+government.getName());
		}
	}
	
	/**
	 * 同步学校
	 * @param school
	 * @param czbs  操作标示(A-新增;U-修改;D-删除)
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午3:04:14
	 */
	public static void syncSchool(School school,String czbs)throws OpenApiException{
		Assert.notNull(czbs);
		if("0".equals(school.getSerialNumber())){
			log.error("学校id="+school.getId()+"序列号为0");
			throw new RuntimeException("学校id="+school.getId()+"序列号为0");
		}
		Map<String, String> params = new HashMap<String, String>();
		String areaId = "";
		if(!Strings.isNullOrEmpty(school.getAreaId())){
			areaId = school.getAreaId();
		}else if(!Strings.isNullOrEmpty(school.getGovernmentId())){
			try {
				Government gov = ApplicationContextHolder.getBean(GovernmentBiz.class).get(school.getGovernmentId());
				if(gov!=null&&gov.getAreaId()!=null){
					areaId = gov.getAreaId();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String apiName = "12study/syncSchool";
		params.put("xxmc", school.getName()); // 学校名称
		params.put("jglb", "05"); // 机构类别(04-学校<上级机构是区>;05-直属学校<上级机构是市>),儋州直接传05
		params.put("sjjgxzqh", areaId); // 上级机构行政区划code
		params.put("xxxz", "校长"); // 学校校长
		params.put("xxgk", StringUtils.isNotEmpty(school.getDescription()) ? school.getDescription() : "学校概况"); // 学校概况
		params.put("schoolid", school.getId()); // 学校唯一标示(唯一能确定该学校)
		
		params.put("czbs", czbs);	//操作标示(A-新增;U-修改;D-删除)
		params.put("appid", appId);
		if(log.isDebugEnabled()){
			log.debug("params:"+params);
		}
		
		JSONObject resultObject = requestApi(apiName, params,JSONObject.class);
		if(log.isDebugEnabled()){
			log.debug(resultObject);
		}
		if (resultObject != null) {
			if(!resultObject.getBooleanValue("isSuccess")){
				String errormsg = "与伟东云同步学校信息失败,操作标识:"+czbs+",学校id="+school.getId()+",学校名称="+school.getName()+",错误信息:"+resultObject.getString("msgCode");
				log.error(errormsg);
				if(!(resultObject.getString("msgCode")!=null && resultObject.getString("msgCode").indexOf("已同步")>-1)){
					throw new RuntimeException(errormsg);
				}
			}
		}else{
			throw new RuntimeException("与伟东云同步学校信息失败,操作标识:"+czbs+",学校id="+school.getId()+",学校名称="+school.getName());
		}
	}
	
	/**
	 * 同步教师
	 * @param government
	 * @param czbs  操作标示(A-新增;U-修改;D-删除)
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午3:04:14
	 */
	public static void syncTeacher(Teacher teacher,String czbs)throws OpenApiException{
		if(!enableSync){
			log.error("与伟东云同步接口已关闭");
			return;
		}
		Assert.notNull(czbs);
		Map<String, String> params = new HashMap<String, String>();
		
		String apiName = "12study/syncTeacher";
		params.put("schoolid", teacher.getSchoolId()); //教师所在学校唯一标示
		params.put("xm", teacher.getRealName()); //姓名
		params.put("xb", teacher.getSex()+""); //性别(1-男;2女)
		//博育云教师任教科目与伟东云对应：Course实体添加伟东云编码映射字段
		params.put("km", getWdCourseCode(teacher.getCourses())); /*教师教的科目(
																	11-品德与生活（社会）;
																	12-思想品德（政治）;
																	13-语文;
																	14-数学;
																	15-科学;
																	16-物理;
																	17-化学;
																	18-生物;
																	19-历史与社会;
																	20-地理;
																	21-历史;
																	22-体育与健康;
																	23-艺术;
																	24-音乐;
																	25-美术;
																	26-信息技术;
																	27-通用技术;
																	41-英语;
																	42-日语;
																	43-俄语;
																	49-其他外国语;
																	62-劳动与技术;
																	28-安全教育;
																	29-心理健康;
																	30-理科综合;
																	31-文科综合;
																	60-综合实践活动;
																	98-地校课程)
		*/
		params.put("userid", teacher.getId()); //唯一标示该用户(与学生,家长,教研员等也不能重复例如登入账号)
		
		params.put("czbs", czbs);	//操作标示(A-新增;U-修改;D-删除)
		params.put("appid", appId);
		if(log.isDebugEnabled()){
			log.debug("params:"+params);
		}
		JSONObject resultObject = requestApi(apiName, params,JSONObject.class);
		if(log.isDebugEnabled()){
			log.debug(resultObject);
		}
		if (resultObject != null) {
			if(!resultObject.getBooleanValue("isSuccess")){
				String errormsg = "与伟东云同步教师信息失败,操作标识:"+czbs+",教师id="+teacher.getId()+",教师姓名="+teacher.getRealName()+",错误信息:"+resultObject.getString("msgCode");
				log.error(errormsg);
				if(!(resultObject.getString("msgCode")!=null && resultObject.getString("msgCode").indexOf("已同步")>-1)){
					throw new RuntimeException(errormsg);
				}
			}
		}else{
			throw new RuntimeException("与伟东云同步教师信息失败,操作标识:"+czbs+",教师id="+teacher.getId()+",教师姓名="+teacher.getRealName());
		}
	}

	/**
	 * 同步学生
	 * @param government
	 * @param czbs  操作标示(A-新增;U-修改;D-删除)
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午3:04:14
	 */
	public static void syncStudent(Student student,String czbs)throws OpenApiException{
		if(!enableSync){
			log.error("与伟东云同步接口已关闭");
			return;
		}
		Assert.notNull(czbs);
		Map<String, String> params = new HashMap<String, String>();
		
		String apiName = "12study/syncStudent";
		String schoolId = null;
		String gradeId = student.getBelongGradeId();
		if(gradeId!=null){
			try {
				SchoolGrade schoolGrade = schoolGrade = ApplicationContextHolder.getBean(SchoolGradeBiz.class).get(gradeId);
				if(schoolGrade!=null){
					schoolId = schoolGrade.getSchoolId();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(schoolId==null){
			log.error("学生id="+student.getId()+"没有找到所在学校");
			//throw new RuntimeException("学生id="+student.getId()+"没有找到所在学校");
			return;
		}
		params.put("schoolid", schoolId); //学生所在学校唯一标示
		params.put("xm", student.getRealName()); //姓名
		params.put("xb", student.getSex()+""); //性别(1-男;2女)
		params.put("userid", student.getId()); //唯一标示该用户(与学生,家长,教研员等也不能重复例如登入账号)
		
		params.put("czbs", czbs);	//操作标示(A-新增;U-修改;D-删除)
		params.put("appid", appId);
		if(log.isDebugEnabled()){
			log.debug("params:"+params);
		}
		JSONObject resultObject = requestApi(apiName, params,JSONObject.class);
		if(log.isDebugEnabled()){
			log.debug(resultObject);
		}
		if (resultObject != null) {
			if(!resultObject.getBooleanValue("isSuccess")){
				String errormsg = "与伟东云同步学生信息失败,操作标识:"+czbs+",学生id="+student.getId()+",学生姓名="+student.getRealName()+",错误信息:"+resultObject.getString("msgCode");
				log.error(errormsg);
				if(!(resultObject.getString("msgCode")!=null && resultObject.getString("msgCode").indexOf("已同步")>-1)){
					throw new RuntimeException(errormsg);
				}
			}
		}else{
			throw new RuntimeException("与伟东云同步学生信息失败,操作标识:"+czbs+",学生id="+student.getId()+",学生姓名="+student.getRealName());
		}
	}
	
	/**
	 * 同步家长
	 * @param government
	 * @param czbs  操作标示(A-新增;U-修改;D-删除)
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午3:04:14
	 */
	public static void syncParent(Parent parent,String czbs)throws OpenApiException{
		if(!enableSync){
			log.error("与伟东云同步接口已关闭");
			return;
		}
		Assert.notNull(czbs);
		Map<String, String> params = new HashMap<String, String>();
		
		String apiName = "12study/syncParents";
		params.put("xm", parent.getRealName()); //姓名
		params.put("xb", parent.getSex()+""); //性别(1-男;2女)
		String childrenUserNames = "";
		List<StudentParentRelation> parentRelationList = parent.getStudents();
		if(parentRelationList!=null && !parentRelationList.isEmpty()){
			for(int i = 0;i<parentRelationList.size();i++){
				StudentParentRelation pr = parentRelationList.get(i);
				String studentId = pr.getStudentId();
				if(!Strings.isNullOrEmpty(studentId)){
					childrenUserNames += studentId+",";
				}
			}
		}
		if(childrenUserNames!=null && childrenUserNames.lastIndexOf(",")>-1){
			childrenUserNames = childrenUserNames.substring(0,childrenUserNames.length()-1);
		}
		if(childrenUserNames==null || "".equals(childrenUserNames)){
			return;
		}
		params.put("xsuserid", childrenUserNames); //学生同步接口中userid
		params.put("userid", parent.getId()); //唯一标示该用户(与学生,家长,教研员等也不能重复例如登入账号)
		params.put("czbs", czbs);	//操作标示(A-新增;U-修改;D-删除)
		params.put("appid", appId);
		if(log.isDebugEnabled()){
			log.debug("params:"+params);
		}
		JSONObject resultObject = requestApi(apiName, params,JSONObject.class);
		if(log.isDebugEnabled()){
			log.debug(resultObject);
		}
		if (resultObject != null) {
			if(!resultObject.getBooleanValue("isSuccess")){
				String errormsg = "与伟东云同步家长信息失败,操作标识:"+czbs+",家长id="+parent.getId()+",家长姓名="+parent.getRealName()+",错误信息:"+resultObject.getString("msgCode");
				log.error(errormsg);
				if(!(resultObject.getString("msgCode")!=null && resultObject.getString("msgCode").indexOf("该家长已同步")>-1)){
					throw new RuntimeException(errormsg);
				}
			}
		}else{
			throw new RuntimeException("与伟东云同步家长信息失败,操作标识:"+czbs+",家长id="+parent.getId()+",家长姓名="+parent.getRealName());
		}
	}
	
	
	//博育云科目与伟东云科目映射
	private static String getWdCourseCode(List<TeacherCourse> courseList) {
		
		
		String result = "";
		if(courseList==null || courseList.isEmpty()){
			//伟东教师必须有任教,没有任教的传语文
			result = "13";
			return result;
		}
		for(int i = 0;i <courseList.size();i++){
			TeacherCourse tcourse = courseList.get(i);
			Course course = null;
			try {
				course = ApplicationContextHolder.getBean(CourseBiz.class).get(tcourse.getCourseId());
			} catch (Exception e) {
			}
			if(null!=course && course.getWdCode()!=null && !"".equals(course.getWdCode())){
				result += course.getWdCode()+",";
			}
		}
		if(result.indexOf(",")>-1){
			result = result.substring(0,result.length()-1);
		}
		if("".equals(result)){
			//伟东教师必须有任教,没有任教的传语文
			result = "13";
		}
		return result;
	}
	
	
	/**
	 * 向接口发送请求
	 * @param apiName 
	 * @param params 参数
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午2:57:32
	 */
	public static Object requestApi(String apiName,Map<String, String> params)throws OpenApiException{
		Object result = null;
		OpenApi sdk = new OpenApi(serverName, appId, appSecret);
		WdcloudToken token = getToken();
		String resp = "";
		resp = sdk.execute(apiName, params, token.getValue());
		if (resp != null) {
			try {
				result = JSON.parseObject(resp);
			} catch (Exception e) {
				try {
					result = JSON.parseArray(resp);
				} catch (Exception e2) {
					
				}
			}
		}
		return result;
	}

	/**
	 * 向接口发送请求
	 * @param apiName 
	 * @param params 参数
	 * @return
	 * @throws Exception
	 * @date 2016-11-24 下午2:57:32
	 */
	public static <T> T requestApi(String apiName,Map<String, String> params,Class<T> clazz)throws OpenApiException{
		System.out.println("params:"+params);
		T result = null;
		OpenApi sdk = new OpenApi(serverName, appId, appSecret);
		WdcloudToken token = getToken();
		String resp = sdk.execute(apiName, params, token.getValue());
		if (resp != null) {
			result = JSON.parseObject(resp,clazz);
		}
		return result;
	}
	/**
	 * 获取伟东云应用Token，如果Token不超期则直接使用已经有的，如果Token为空或者超期则调用接口获取新的Token
	 * 
	 * @return
	 * @date 2016-11-24 下午2:06:12
	 */
	public static WdcloudToken getToken()throws OpenApiException {
		//如果token为空或者超期则调用获取token
		if (byyClientWdToken == null || byyClientWdToken.isExpired()) {
			OpenApi sdk = new OpenApi(serverName, appId, appSecret);
			// 获取应用token
			try {
				WdcloudToken wdcloudToken = sdk.getApplicationTokenSSL();
				byyClientWdToken = new BYYClientWdToken(wdcloudToken);
			} catch (OpenApiException e) {
				e.printStackTrace();
				log.error(e.getErrorCode() + ":" + e.getMessage(), e);
			}
			if(log.isDebugEnabled()){
				log.debug(byyClientWdToken.getStatus() + "  " + byyClientWdToken.getValue() + " " + byyClientWdToken.getExpiresIn());
			}
		}
		return byyClientWdToken;
	}
	
	public static void main(String []args){
		WdcloudToken token = getToken();
	}
}
