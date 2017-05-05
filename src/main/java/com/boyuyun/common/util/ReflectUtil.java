package com.boyuyun.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author zhy
 */
public class ReflectUtil {
	/**
	 * 获取属性值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object getPropertyByField(Object obj,String field){
		if(obj==null||field==null)return null;
		try {
			Field f = obj.getClass().getDeclaredField(field);
			if(f!=null){
				f.setAccessible(true);
				return f.get(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过getter获取属性值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object getPropertyByGetter(Object obj,String field){
		if(obj==null||field==null)return null;
		try {
			Field f = obj.getClass().getDeclaredField(field);
			String prefix = "get";
			if(f!=null&&f.getType().equals(Boolean.class)){
				prefix = "is";
			}
			String methodName = prefix+field.substring(0,1).toUpperCase()+field.substring(1);
			Method method = obj.getClass().getDeclaredMethod(methodName);
			return method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
