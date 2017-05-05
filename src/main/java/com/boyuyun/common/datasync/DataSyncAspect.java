package com.boyuyun.common.datasync;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.boyuyun.common.annotation.SyncTo;
import com.boyuyun.common.datasync.impl.ByyOADataSyncHandler;
import com.boyuyun.common.datasync.impl.DudaoDataSyncHandler;
import com.boyuyun.common.datasync.impl.KgDataSyncHandler;
import com.boyuyun.common.datasync.impl.WDDataSyncHandler;
import com.boyuyun.common.util.PropertiesUtils;

@Component("DataSyncAspect")
@Aspect
/**
 * 处理多系统同步 
 * @author zhy
 */
public class DataSyncAspect {
	public static boolean enableSyncAspect = false;
	static {
		enableSyncAspect = ("true".equals( PropertiesUtils.getProperties().getProperty("enableSyncAspect")!=null?PropertiesUtils.getProperties().getProperty("enableSyncAspect").trim():"" ))?true:false;
	}
	public static final Logger log = Logger.getLogger(DataSyncAspect.class);

	private Map<String,DataSyncHandler> handlers = new HashMap<String,DataSyncHandler>();
	/**
	 * 组织多个同步处理器
	 */
	public DataSyncAspect(){
		//WDDataSyncHandler wd = new WDDataSyncHandler();
		//ByyOADataSyncHandler oa = new ByyOADataSyncHandler();
		//KgDataSyncHandler kg =  new KgDataSyncHandler();
		//DudaoDataSyncHandler du = new DudaoDataSyncHandler();
		//handlers.put(kg.getSystemName(), kg);
		//handlers.put(wd.getSystemName(), wd);
		//handlers.put(oa.getSystemName(), oa);
		//handlers.put(du.getSystemName(), du);
	}
	@Around(value="execution(* com.boyuyun.base.**.biz.impl.*.*(..))&&@annotation(com.boyuyun.common.annotation.SyncTo) ")
	public Object cut(ProceedingJoinPoint joinPoint)throws Throwable{
		//判断是否开启切面开关
		//先执行
		Object result = joinPoint.proceed(joinPoint.getArgs());
		//获取方法注解参数
		String methodName=joinPoint.getSignature().getName();  
        Class<?> classTarget=joinPoint.getTarget().getClass();  
        Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();  
        Method objMethod=classTarget.getMethod(methodName, par);  
		SyncTo sync = objMethod.getAnnotation(com.boyuyun.common.annotation.SyncTo.class);
		//调用
		handle(sync.system(),sync.dataType(),sync.operateType(),joinPoint.getArgs());
		return result;
	}
	/**
	 * 处理
	 * @param systems
	 * @param dtype
	 * @param otype
	 * @param params
	 */
	private void handle(String[] systems,SyncDataType dtype,SyncOperateType otype,Object[] params){
		//判断是否开启切面开关
		if(enableSyncAspect){
			if(systems.length>0){
				for (int i = 0; i < systems.length; i++) {
					DataSyncHandler handler = handlers.get(systems[i]);
					if(handler!=null){
						try {
							handler.handler(dtype, otype, params);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}else{
			log.error("同步切面已关闭");
		}
	}
}
