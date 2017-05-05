package com.boyuyun.common.json;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;

import com.dhcc.common.json.JsonUtil;

/**
 * JSON操作类。
 * 
 * @author 贺波
 * 
 */

public class ByyJsonUtil extends JsonUtil {
	// 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 
	 * @param obj 任意对象
	 * @param formatter 日期格式
	 * @param filterFields 忽略转json字段
	 * @return 返回obj -> json 
	 */
	public static String serializeAllExcept(Object obj,SimpleDateFormat formatter, String... filterFields) 
    { 
		ObjectMapper mapper = new ObjectMapper();
        try { 
        	if(null == filterFields){
        		filterFields = new String[]{""};
        	}
        	FilterProvider filters = new SimpleFilterProvider().addFilter("jsonFilter", 
                    SimpleBeanPropertyFilter.serializeAllExcept(filterFields)); 
        	mapper.setDateFormat(formatter);
        	mapper.setFilters(filters); 
            mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() { 
				private static final long serialVersionUID = 5848702269956278537L;
                public Object findFilterId(AnnotatedClass ac) 
                { 
					@SuppressWarnings("deprecation")
					Object id = super.findFilterId(ac);
			        if (id == null) {
			           id = ac.getName();
			        }
                    return id; 
                } 
            }); 
            return mapper.writeValueAsString(obj); 
        } catch (Exception e) { 	
        	e.printStackTrace();
            throw new RuntimeException("Json.format error:" + obj, e); 
        } 
    }
	
	/**
	 * 
	 * @param obj 任意对象
	 * @param formater 日期格式 默认yyyy-MM-dd HH:mm:ss
	 * @param filterFields 忽略转json字段
	 * @return 返回json字符串
	 */
	public static String renderJson(final Object obj,String formater,String... filterFields){
		if(null== formater || "".equals(formater.trim())){
			formater = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(formater);
		return serializeAllExcept(obj,formatter, filterFields);
	}
	
	public static String renderJson(long total,List<?> rows,String formater,String... filterFields){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", total);
        jsonMap.put("rows", rows);
		return renderJson(jsonMap, formater, filterFields);
	}
	
	
	
    /**
     * 将list直接转换成json串
     * @param rows
     * @return
     */
    public static String serialize(Object obj) {
    	//如果是JSONObject/JSONArray/Map/等还是调用原来的？
        return serialize(obj, null, null);
    }
    	//如果是JSONObject/JSONArray/Map/等还是调用原来的？
     
    /**
     * 将list直接转换成json串，并且指定过滤掉的字段
     * @param rows
     * @param ignoreFields
     * @return
     */
    public static String serialize(Object obj, String[] ignoreFields) {
        return serialize(obj, ignoreFields, null);
//    	return renderJson(obj, null, ignoreFields);
    }
    /**
     * 将list直接转换成json串，并且指定日期的转换格式
     * @param rows
     * @param dateFormat
     * @return
     */
    public static String serialize(Object obj, String dateFormat) {
        return serialize(obj, null, dateFormat);
//    	return renderJson(obj, dateFormat, null);
    }
    /**
     * 将list直接转换成json串，并且指定过滤掉的字段、日期的转换格式
     * @param rows
     * @param ignoreFields
     * @param dateFormat
     * @return
     */
    public static String serialize(Object obj, String[] ignoreFields, String dateFormat) {
        JsonConfig jsonConfig = new JsonConfig();
        //1 处理日期格式
        if(dateFormat==null || dateFormat.equals("")) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";//默认为年月日时分秒
        }
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTransform(dateFormat));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateTransform(dateFormat));
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //2 处理需要过滤掉的字段
        if(ignoreFields!=null && ignoreFields.length>0) {
            Set<String> ignoreSet = new HashSet<String>();
            CollectionUtils.addAll(ignoreSet, ignoreFields);
            
            //下面这两个过滤必须添加
            ignoreSet.add("handler");
            ignoreSet.add("hibernateLazyInitializer");
            
            Object[] tempArr = ignoreSet.toArray();
            ignoreFields = new String[tempArr.length];
            for(int i=0; i<tempArr.length; i++) {
                ignoreFields[i] = tempArr[i].toString();
            }
        }else {
            ignoreFields = new String[]{"handler","hibernateLazyInitializer"};
        }
        jsonConfig.setExcludes(ignoreFields);
        
        //3 判断obj 是不是一个集合
        if(obj instanceof List) {
            return JSONArray.fromObject(obj, jsonConfig).toString();
        }else {
            return JSONObject.fromObject(obj, jsonConfig).toString();
        }
    }
    
    /**
     * 根据对象获取JSONObject对象
     * @param obj 如果为list类型,则返回null
     * @param ignoreFields
     * @param dateFormat
     * @return
     * @date 2015-9-24 下午3:17:51
     */
    public static JSONObject serializeToJSONObject(Object obj, String[] ignoreFields, String dateFormat) {
        JsonConfig jsonConfig = new JsonConfig();
        //1 处理日期格式
        if(dateFormat==null || dateFormat.equals("")) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";//默认为年月日时分秒
        }
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTransform(dateFormat));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateTransform(dateFormat));
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //2 处理需要过滤掉的字段
        if(ignoreFields!=null && ignoreFields.length>0) {
            Set<String> ignoreSet = new HashSet<String>();
            CollectionUtils.addAll(ignoreSet, ignoreFields);
            
            //下面这两个过滤必须添加
            ignoreSet.add("handler");
            ignoreSet.add("hibernateLazyInitializer");
            
            Object[] tempArr = ignoreSet.toArray();
            ignoreFields = new String[tempArr.length];
            for(int i=0; i<tempArr.length; i++) {
                ignoreFields[i] = tempArr[i].toString();
            }
        }else {
            ignoreFields = new String[]{"handler","hibernateLazyInitializer"};
        }
        jsonConfig.setExcludes(ignoreFields);
        
        //3 判断obj 是不是一个集合
        if(obj instanceof List) {
            return null;
        }else {
            return JSONObject.fromObject(obj, jsonConfig);
        }
    }
    
    /**
     * 根据对象获取JSONObject对象
     * @param obj 如果为list类型,则返回null
     * @param ignoreFields
     * @param dateFormat
     * @return
     * @date 2015-9-24 下午3:17:51
     */
    public static JSONArray serializeToJSONArray(Object obj, String[] ignoreFields, String dateFormat) {
        JsonConfig jsonConfig = new JsonConfig();
        //1 处理日期格式
        if(dateFormat==null || dateFormat.equals("")) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";//默认为年月日时分秒
        }
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTransform(dateFormat));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateTransform(dateFormat));
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //2 处理需要过滤掉的字段
        if(ignoreFields!=null && ignoreFields.length>0) {
            Set<String> ignoreSet = new HashSet<String>();
            CollectionUtils.addAll(ignoreSet, ignoreFields);
            
            //下面这两个过滤必须添加
            ignoreSet.add("handler");
            ignoreSet.add("hibernateLazyInitializer");
            
            Object[] tempArr = ignoreSet.toArray();
            ignoreFields = new String[tempArr.length];
            for(int i=0; i<tempArr.length; i++) {
                ignoreFields[i] = tempArr[i].toString();
            }
        }else {
            ignoreFields = new String[]{"handler","hibernateLazyInitializer"};
        }
        jsonConfig.setExcludes(ignoreFields);
        
        //3 判断obj 是不是一个集合
        if(obj instanceof List) {
            return JSONArray.fromObject(obj, jsonConfig);
        }else {
        	return null;
        }
    }
    
    
    
    /**
     * 将list直接转换成json串，含分页
     * @param total
     * @param rows
     * @return
     */
    public static String serialize(long total, List rows) {
        return serialize(total, rows, null, null);
//    	return renderJson(total, rows, null, null);
    }

    /**
     * 将list直接转换成json串，含分页。 并且指定过滤掉的字段
     * @param total
     * @param rows
     * @param ignoreFields
     * @return
     */
    public static String serialize(long total, List rows, String[] ignoreFields) {
        return serialize(total, rows, ignoreFields, null);
//    	return renderJson(total, rows, null, ignoreFields);
    }
    
    
    
    /**
     * 将list直接转换成json串，含分页。 并且指定过滤掉的字段
     * @param total
     * @param rows
     * @param ignoreFields
     * @return
     */
    public static String serialize(long total, List rows, String dateFormat) {
        return serialize(total, rows, null, dateFormat);
//    	return renderJson(total, rows, dateFormat, null);
    }
    
    
    /**
     * 将list直接转换成json串，含分页。 并且指定过滤掉的字段、日期的转换格式
     * @param total
     * @param rows
     * @param ignoreFields
     * @return
     */
    public static String serialize(long total, List rows, String[] ignoreFields, String dateFormat) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("total", total);
        jsonMap.put("rows", rows);
        
        JsonConfig jsonConfig = new JsonConfig();
        //1 处理日期格式
        if(dateFormat==null || dateFormat.equals("")) {
            dateFormat = "yyyy-MM-dd";//默认为年月日
        }
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTransform(dateFormat));
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateTransform(dateFormat));
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        //2 处理需要过滤掉的字段
        if(ignoreFields!=null && ignoreFields.length>0) {
            Set<String> ignoreSet = new HashSet<String>();
            CollectionUtils.addAll(ignoreSet, ignoreFields);

            //下面这两个过滤必须添加
            ignoreSet.add("handler");
            ignoreSet.add("hibernateLazyInitializer");
            
            Object[] tempArr = ignoreSet.toArray();
            ignoreFields = new String[tempArr.length];
            for(int i=0; i<tempArr.length; i++) {
                ignoreFields[i] = tempArr[i].toString();
            }
        }else {
            ignoreFields = new String[]{"handler","hibernateLazyInitializer"};
        }
        jsonConfig.setExcludes(ignoreFields);
        
        return JSONObject.fromObject(jsonMap, jsonConfig).toString();
    }
    
    
    /**
     * 将Map集合转换成Json字符串(Date类型转换为通用格式[yyyy-MM-dd HH:mm:ss])
     * @param jsonMap 
     * @return 
     * @date 2015-8-29 下午12:00:57 
     */
    public static String serialize(Map<?,?> jsonMap){
        return serialize(jsonMap,null,null,"yyyy-MM-dd HH:mm:ss",null);
//    	return renderJson(jsonMap, null, null);
    }
    /**
     * 将Map集合转换成Json字符串(Date类型转换为通用格式[yyyy-MM-dd HH:mm:ss])
     * @param jsonMap 
     * @param filterFields 要过滤的字段数组
     * @return 
     * @date 2015-8-29 下午12:01:08 
     */
    public static String serialize(Map<?,?> jsonMap,String []filterFields){
        return serialize(jsonMap,filterFields,null,"yyyy-MM-dd HH:mm:ss",null);
//    	return renderJson(jsonMap, null, filterFields);
    }
    /**
     * 转换Map对象为Json字符串
     * @param jsonMap 要转换的Map对象
     * @param filterFields 要过滤的字段
     * @param enclosedType 要过滤的类型
     * @param dateFormat 日期转换格式
     * @param timeFormat 时间转换格式
     * @return JSON字符串
     * @date 2015-8-29 上午11:34:56
     */
    public static String serialize(Map<?,?> jsonMap,String[] filterFields, Class enclosedType,String dateFormat,String timeFormat){
        // 通过jsonconfig，过滤指定的的属性filterFields
        JsonConfig jsonConfig = new JsonConfig();
        if(filterFields!=null){
            jsonConfig.setExcludes(filterFields);
        }
        // 通过jsonconfig，过滤指定的的属性filterFields
        if(enclosedType!=null){
            jsonConfig.setEnclosedType(enclosedType);
        }
        if(dateFormat!=null && !"".equals(dateFormat)){
            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateTransform(dateFormat));
        }
        if(timeFormat!=null && !"".equals(timeFormat)){
            jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonDateTransform(timeFormat));
        }
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONObject.fromObject(jsonMap, jsonConfig).toString();
    }
    
    public static String getSuccessJson(String msg) {
        return "{\"success\":\"true\",\"msg\":\"" + msg + "\"}";
    }

    public static String getFailJson(String msg) {
        return "{\"success\":\"false\",\"msg\":\"" + msg + "\"}";
    }
    
    public static String getNoDataJson(String msg) {
        return "{\"success\":\"false\",\"msg\":\"" + msg + "\",\"total\":0,\"rows\":[]}";
    }
    
    public static String getDeleteHtml(String title,String msg){
    	return "<html><meta charset=\"utf-8\"><title>"+title+"</title><body><div style=\"position:absolute;top:50%;text-align:center;width:100%;\">"+msg+"</div></body></html>";
    }

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}
