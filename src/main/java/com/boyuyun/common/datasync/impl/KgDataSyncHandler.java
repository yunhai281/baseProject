package com.boyuyun.common.datasync.impl;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.boyuyun.base.org.entity.Government;
import com.boyuyun.base.org.entity.School;
import com.boyuyun.base.sys.biz.DictionaryItemBiz;
import com.boyuyun.base.sys.entity.DictionaryItem;
import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.common.datasync.DataSyncHandler;
import com.boyuyun.common.datasync.SyncDataType;
import com.boyuyun.common.datasync.SyncOperateType;
import com.boyuyun.common.util.PropertiesUtils;
import com.boyuyun.common.util.WebServiceClientHelper;
import com.boyuyun.common.util.context.ApplicationContextHolder;

public class KgDataSyncHandler implements DataSyncHandler{
	private static final Logger log = Logger.getLogger(KgDataSyncHandler.class);
	static{
		ConstantUtil.KGGRADING_URL = PropertiesUtils.getProperties().getProperty("KGGRADING_URL");
	}
	@Override
	public String getSystemName() {
		return "kg";
	}

	@Override
	public void handler(SyncDataType dtype, SyncOperateType otype, Object[] obj) {
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
				deleteSchool(ids);
			}
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Add.equals(otype)){
			addInstitution((Government) obj[0]);
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Update.equals(otype)){
			updateInstitution((Government) obj[0]);
		}
		if(SyncDataType.Government.equals(dtype)&&SyncOperateType.Delete.equals(otype)){
			Government sch = (Government) obj[0];
			if(sch!=null){
				String []  ids = new String[]{sch.getId()};
				deleteInstitution(ids);
			}
			
		}
	}
	public static boolean addSchool(School school){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-创建学校接口");
		}
		try {
			Assert.notNull(school);
			Assert.notNull(school.getId());
			String schoolId= school.getId();
			String schoolName= school.getName();
			String schoolNo = school.getCode();
			String associatedSchoolId=school.getSerialNumber()+"";
			String remarks="";
			int schoolTypeId=school.getSystemType();
			String schoolType = "";
			if(schoolTypeId>0){
				DictionaryItem dic = ApplicationContextHolder.getBean(DictionaryItemBiz.class).get(schoolTypeId); 
				if(dic!=null){
					schoolType = dic.getName();
				}
			}
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "addSchool",schoolId,schoolName,schoolNo,associatedSchoolId,school.getGovernmentId(),schoolType,remarks );
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，创建学校失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，创建学校失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，创建学校失败!返回响应码："+msg);
		}
		return success;
	}
	
	
	
	public static boolean updateSchool(School school){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-更新学校接口");
		}
		try {
			Assert.notNull(school);
			Assert.notNull(school.getId());
			
			String schoolId= school.getId();
			String schoolName= school.getName();
			String schoolNo = school.getCode();
			String associatedSchoolId=school.getSerialNumber()+"";
			int schoolTypeId=school.getSystemType();
			String schoolType = "";
			if(schoolTypeId>0){
				DictionaryItem dic = ApplicationContextHolder.getBean(DictionaryItemBiz.class).get(schoolTypeId); 
				if(dic!=null){
					schoolType = dic.getName();
				}
			}
			String remarks="";
			
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "updateSchool", schoolId,schoolName,schoolNo,associatedSchoolId,school.getGovernmentId(),schoolType,remarks );
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，更新学校失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，更新学校失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，更新学校失败!返回响应码："+msg);
		}
		return success;
	}
	public static boolean deleteSchool(Serializable[] ids){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-删除学校接口");
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
			JSONObject paramObject=new JSONObject();
			paramObject.put("id", id);
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "deleteSchool",id );
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，删除学校失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，删除学校失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，删除学校失败!返回响应码："+msg);
		}
		return success;
	}

	public static boolean addInstitution(Government gov){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-创建机构接口");
		}
		try {
			Assert.notNull(gov);
			Assert.notNull(gov.getId());
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "addInstitution", gov.getId(),gov.getName(),gov.getParentId()==null?"":gov.getParentId(),gov.getAreaId()==null?"":gov.getAreaId(),"");
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，创建机构失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，创建机构失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，创建机构失败!返回响应码："+msg);
		}
		return success;
	}
	public static boolean updateInstitution(Government gov){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-更新机构接口");
		}
		try {
			Assert.notNull(gov);
			Assert.notNull(gov.getId());
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "updateInstitution", gov.getId(),gov.getName(),gov.getParentId()==null?"":gov.getParentId(),gov.getAreaId()==null?"":gov.getAreaId(),"");
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，更新机构失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，更新机构失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，更新机构失败!返回响应码："+msg);
		}
		return success;
	}
	
	public static boolean deleteInstitution(Serializable[] ids){
		boolean success=false;
		String msg="";
		if(log.isInfoEnabled()){
			log.info("开始调用幼儿园定级系统-删除机构接口");
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
			JSONObject paramObject=new JSONObject();
			paramObject.put("id", id);
			Object obj = WebServiceClientHelper.callService(ConstantUtil.KGGRADING_URL, "deleteInstitution",id );
			if(obj!=null){
				JSONObject resultObj =null;
				try {
					resultObj = JSONObject.fromObject(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(resultObj!=null){
					success=resultObj.get("success")!=null?(Boolean)resultObj.get("success"):false;
					msg = resultObj.get("msg")!=null?resultObj.get("msg").toString():"";
				}
			}
		} catch (Exception e) {
			log.error("调用幼儿园定级系统接口，删除机构失败!",e);
		}
		if(!success){
			log.error("调用幼儿园定级系统接口，删除机构失败!返回响应码："+msg);
			throw new RuntimeException("调用幼儿园定级系统接口，删除机构失败!返回响应码："+msg);
		}
		return success;
	}
}
