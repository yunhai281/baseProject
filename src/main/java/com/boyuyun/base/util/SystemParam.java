package com.boyuyun.base.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.boyuyun.base.sys.biz.SysParamBiz;
import com.boyuyun.common.util.context.ApplicationContextHolder;

public class SystemParam {
	private static final int refreshMinates = 30;
	public static Date lastUpdate= new Date();//上次更新时间，默认为系统启动时
	public static String copyRightInfo;//版权信息
	public static String picPath;//logo地址
	public static String sysName;//系统名称
	public static String tel;//联系电话
	public static String icp;//ICP信息	
	public static boolean needRefresh(){
		return ((System.currentTimeMillis()-lastUpdate.getTime())/1000*60)>refreshMinates;
	}
	/**
	 *取值 
	 */
	public static String get(String key){
		try {
			if(needRefresh()){
				refresh();
			}
			Object o = SystemParam.class.newInstance();
			Field field = SystemParam.class.getDeclaredField(key);
			if(field!=null){
				return (String) field.get(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 刷新
	 */
	public static void refresh(){
		SysParamBiz biz = ApplicationContextHolder.getBean(SysParamBiz.class);
		Map map = null;
		try {
			map = biz.getAllAsMap();
			refreshParam(map);
			lastUpdate = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 刷新值
	 * @param map
	 */
	public static void refreshParam(Map map){
		Object o = null;
		try {
			o = SystemParam.class.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			try {
				Field field = SystemParam.class.getDeclaredField(name);
				if(field!=null){
					field.set(o, map.get(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	public static void main(String[] args) {
		Field [] fields = SystemParam.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i].getName());
		}
	}
}
