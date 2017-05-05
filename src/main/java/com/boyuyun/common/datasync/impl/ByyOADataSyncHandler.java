package com.boyuyun.common.datasync.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.user.entity.Teacher;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.common.datasync.DataSyncHandler;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.PropertiesUtils;
import com.boyuyun.common.util.WebServiceClientHelper;

public class ByyOADataSyncHandler implements DataSyncHandler {
	/** 请求成功 */
	public static final String SUCCESS = "1";
	/** 请求失败 */
	public static final String FAIL = "0";
	
	private static final Logger log = Logger.getLogger(ByyOADataSyncHandler.class);
	static{
		ConstantUtil.BYY_OA_URL = PropertiesUtils.getProperties().getProperty("BYY_OA_URL");
		ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL = ConstantUtil.BYY_OA_URL+PropertiesUtils.getProperties().getProperty("BYYOA_GOVERNMENT_WSDL_URL");
		ConstantUtil.BYYOA_USER_WSDL_URL = ConstantUtil.BYY_OA_URL+PropertiesUtils.getProperties().getProperty("BYYOA_USER_WSDL_URL");
	}
	@Override
	public String getSystemName() {
		return "oa";
	}

	@Override
	public void handler(SyncDataType dtype, SyncOperateType otype, Object [] obj) {
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			addSchool((School) obj[0]);
		}
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			updateSchool((School) obj[0]);
		}
		if(SyncDataType.School.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			School sch = (School) obj[0];
			if(sch!=null){
				String []  ids = new String[]{sch.getId()};
				deleteGovernmentOrSchool(ids);
			}
		}
		
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			addTeacher((Teacher) obj[0]);
		}
		
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			updateTeacher((Teacher) obj[0]);
		}
		if(SyncDataType.Teacher.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			deleteTeacher((List<Teacher>) obj[0]);
		}
		
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			addGovernment((Government) obj[0]);
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			updateGovernment((Government) obj[0]);
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			Government sch = (Government) obj[0];
			if(sch!=null){
				String []  ids = new String[]{sch.getId()};
				deleteGovernmentOrSchool(ids);
			}
		}
		
	}
	
	/**
	 * 添加机构
	 * @param government
	 * @return
	 * @author XHL
	 * @date 2016-8-31 下午2:12:36
	 */
	public static String addGovernment(Government government){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-创建机构接口");
		}
		try {
			Assert.notNull(government);
			Assert.notNull(government.getId());
			
			JSONObject paramObject = _government2JsonObject(government);
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL, "addOrgGovernmentByJson", paramObject.toString());
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，创建机构失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，创建机构失败!返回响应码："+statusCode);
		}
		return statusCode;
	}

	private static JSONObject _government2JsonObject(Government government) {
		JSONObject paramObject = new JSONObject();
		paramObject.put("id", government.getId());
		paramObject.put("code", government.getCode()!=null?government.getCode():"");
		paramObject.put("levelType", government.getLevelType());
		paramObject.put("orgName", government.getName());
		paramObject.put("shortName", government.getShortName());
		paramObject.put("area_id", government.getAreaId()!=null?government.getAreaId():"");
		paramObject.put("parent_id", government.getParentId()!=null?government.getParentId():"");
		paramObject.put("isSchool", false);
		paramObject.put("seq", government.getSeq());
		return paramObject;
	}
	
	/**
	 * 更新机构信息
	 * @param government
	 * @return
	 * @author XHL
	 * @date 2016-8-31 下午2:14:03
	 */
	public static String updateGovernment(Government government){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-修改机构接口");
		}
		try {
			Assert.notNull(government);
			Assert.notNull(government.getId());
			
			JSONObject paramObject = _government2JsonObject(government);
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL, "updateOrgGovernmentByJson", paramObject.toString());
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，修改机构失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，修改机构失败!返回响应码："+statusCode);
		}
		return statusCode;
	}
	
	
	/**
	 * 删除机构
	 * @param ids 机构id
	 * @return
	 * @date 2016-8-31 下午2:14:58
	 */
	public static String deleteGovernmentOrSchool(Serializable[] ids){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-删除机构或学校接口");
		}
		try {
			Assert.notNull(ids);
			String id = "";
			for(Serializable idItem:ids){
				id += idItem.toString()+",";
			}
			if(id.lastIndexOf(",")>-1){
				id = id.substring(0, id.lastIndexOf(","));
			}
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL, "deleteOrgGovernmentByOrgGovernmentId", id);
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，删除机构或学校[ids="+ids.toString()+"]失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，删除机构或学校失败!返回响应码："+statusCode);
		}
		return statusCode;
	}
	
	/**
	 * 修改学校
	 * @param school
	 * @return
	 * @author XHL
	 * @date 2016-8-31 下午4:17:46
	 */
	public static String updateSchool(School school){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-修改学校接口");
		}
		try {
			Assert.notNull(school);
			Assert.notNull(school.getId());
			
			JSONObject paramObject = _school2JsonObject(school);
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL, "updateOrgGovernmentByJson", paramObject.toString());
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，修改学校失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，修改学校失败!返回响应码："+statusCode);
		}
		return statusCode;
	}
	/**
	 * 添加学校
	 * @param school
	 * @return
	 * @author XHL
	 * @date 2016-8-31 下午4:17:46
	 */
	public static String addSchool(School school){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-创建学校接口");
		}
		try {
			Assert.notNull(school);
			Assert.notNull(school.getId());
			
			JSONObject paramObject = _school2JsonObject(school);
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_GOVERNMENT_WSDL_URL, "addOrgGovernmentByJson", paramObject.toString());
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，创建学校失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			log.error("调用办公系统接口，创建学校失败!返回响应码："+statusCode);
			throw new RuntimeException("调用办公系统接口，创建学校失败!返回响应码："+statusCode);
		}
		return statusCode;
	}
	private static JSONObject _school2JsonObject(School school) {
		JSONObject paramObject = new JSONObject();
		paramObject.put("id", school.getId());
		paramObject.put("code", school.getId());
		paramObject.put("orgName", school.getName());
		paramObject.put("shortName", school.getShortName());
		if(school.getAreaId()!=null){
			paramObject.put("area_id",school.getAreaId());
		}else{
			paramObject.put("area_id", "");
		}
		if(school.getGovernmentId()!=null){
			paramObject.put("parent_id", school.getGovernmentId());
		}else{
			paramObject.put("parent_id", "");
		}
		paramObject.put("isSchool", true);
		paramObject.put("Seq", 99);
		return paramObject;
	}
	/**
	 * 添加用户(校园端在职教师用户)
	 * @param teacher
	 * @return
	 */
	public static String addTeacher(Teacher teacher){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-新增教师用户接口");
		}
		statusCode = FAIL;
		try {
			Assert.notNull(teacher);
			Assert.notNull(teacher.getId());
			
			JSONObject rootJson = new JSONObject();
			JSONArray rowsArray = new JSONArray();
			
			JSONObject paramObject = _teacher2JsonObject(teacher);
			rowsArray.add(paramObject);
			rootJson.put("rows", rowsArray);
			rootJson.put("statusCode","1");
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_USER_WSDL_URL, "addUserByJson", rootJson.toString(),true);
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，新增教师用户失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，新增教师用户失败!返回响应码："+statusCode);
		}
	
		return statusCode;
	}
	
	/**
	 * 修改用户(校园端在职教师用户)
	 * @param teacher
	 * @return
	 * @date 2016-8-31 下午2:41:13
	 */
	public static String updateTeacher(Teacher teacher){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-修改教师用户基本信息接口");
		}
		try {
			Assert.notNull(teacher);
			Assert.notNull(teacher.getId());
			
			JSONObject rootJson = new JSONObject();
			JSONArray rowsArray = new JSONArray();
			
			JSONObject paramObject = _teacher2JsonObject(teacher);
			rowsArray.add(paramObject);
			rootJson.put("rows", rowsArray);
			rootJson.put("statusCode","1");
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_USER_WSDL_URL, "updateUserByJson", rootJson.toString(),true);
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，修改教师用户基本信息失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("调用办公系统接口，修改教师用户基本信息失败!返回响应码："+statusCode);
		}
		return statusCode;
	}

	/**
	 * 删除用户(校园端在职教师用户)
	 * @param ids
	 * @return
	 */
	public static String deleteTeacher(List<Teacher> list){
		String statusCode = FAIL;
		if(log.isInfoEnabled()){
			log.info("开始调用办公系统-删除教师用户接口");
		}
		try {
			Assert.notNull(list);
			String id = "";
			for (Teacher entity : list) {
				id += entity.getId()+",";
		    }
			if(id.lastIndexOf(",")>-1){
				id = id.substring(0, id.lastIndexOf(","));
			}
			Object obj = WebServiceClientHelper.callService(ConstantUtil.BYYOA_USER_WSDL_URL, "deleteUserByUserIds", id);
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					statusCode = resultObj.get("statusCode")!=null?resultObj.get("statusCode").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用办公系统接口，删除教师用户失败!",e);
			e.printStackTrace();
		}
		if(FAIL.equals(statusCode)){
			throw new RuntimeException("删除教师用户时,调用办公系统接口删除教师用户失败");
		}
		return statusCode;
	}
	
	//将teacher转jsonobject
	private static JSONObject _teacher2JsonObject(Teacher teacher) {
		JSONObject paramObject = new JSONObject();
		paramObject.put("id", teacher.getId());
		paramObject.put("deptCode", teacher.getId());
		paramObject.put("realName", teacher.getRealName());
		paramObject.put("userName", teacher.getUserName());
		paramObject.put("password", teacher.getPwd());
		paramObject.put("email", teacher.getEmail()!=null?teacher.getEmail():"");
		DateFormat t_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paramObject.put("levelTime", t_dateFormat.format(teacher.getHireDate()));
		
		//政治面貌
		paramObject.put("politicallandscape", transPoliticallandscape(teacher.getPoliticsName()));
		
		paramObject.put("locked", teacher.getStatus()>0 ?"Y":"N");
		paramObject.put("mobile", teacher.getMobile()!=null?teacher.getMobile():"");
		paramObject.put("phone", teacher.getMobile()!=null?teacher.getMobile():"");
		
		paramObject.put("seq", 99);
		paramObject.put("sex", teacher.getSex());
		paramObject.put("status", teacher.getStatus());
	//	paramObject.put("deptId", teacher.getDept()!=null&&teacher.getDept().getId()!=null?teacher.getDept().getId():"");
		//岗位类型
		paramObject.put("postType", teacher.getPostType());
		
		String schoolId = teacher.getSchoolId();
		if(schoolId!=null){
			paramObject.put("Org_government_id",  teacher.getSchoolId());
		}else{
			paramObject.put("Org_government_id", "");
		}
		paramObject.put("deptId", "");
		paramObject.put("State", 1);
		return paramObject;
	}
	
	public static String transPoliticallandscape(String politics) {
		if(politics==null){
			return "";
		}
		if(politics.indexOf("中共党员")>=0){
			return "HR-007-001";
		}else if(politics.indexOf("中共党员")>=0){
			return "HR-007-001";
		}else if(politics.indexOf("中共预备党员")>=0){
			return "HR-007-002";
		}else if(politics.indexOf("共青团员")>=0){
			return "HR-007-003";
		}else if(politics.indexOf("民革党员")>=0){
			return "HR-007-004";
		}else if(politics.indexOf("民盟盟员")>=0){
			return "HR-007-005";
		}else if(politics.indexOf("民建会员")>=0){
			return "HR-007-006";
		}else if(politics.indexOf("民进会员")>=0){
			return "HR-007-007";
		}else if(politics.indexOf("农工党党员")>=0){
			return "HR-007-008";
		}else if(politics.indexOf("致公党党员")>=0){
			return "HR-007-009";
		}else if(politics.indexOf("九三学社社员")>=0){
			return "HR-007-010";
		}else if(politics.indexOf("台盟盟员")>=0){
			return "HR-007-011";
		}else if(politics.indexOf("无党派人士")>=0){
			return "HR-007-012";
		}else if(politics.indexOf("群众")>=0){
			return "HR-007-013";
		}else if(politics.indexOf("其他")>=0){
			return "HR-007-013";
		}
		return "";
	}
	
}
