package com.boyuyun.common.autolog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.boyuyun.base.sys.biz.OperateLogBiz;
import com.boyuyun.base.sys.entity.OperateLog;
import com.boyuyun.base.user.entity.User;
import com.boyuyun.base.util.UserUtil;
import com.boyuyun.base.util.base.BaseAction;
import com.boyuyun.common.annotation.LogThisOperate;
import com.boyuyun.common.util.ByyStringUtil;
import com.boyuyun.common.util.context.ApplicationContextHolder;
import com.dhcc.common.util.BeanUtils;
import com.google.common.base.Strings;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 自动日志
 * @author zhy
 */
@Component("AutoLogAspect")
@Aspect
public class AutoLogAspect {
	@Around(value="execution(* com.boyuyun.base.**.action.*.*(..))&&@annotation(com.boyuyun.common.annotation.LogThisOperate) ")
	public Object cut(ProceedingJoinPoint joinPoint)throws Throwable{
		List<OperateLog> logs = new ArrayList<OperateLog>();
		OperateLog log = new OperateLog();
		log.setId(ByyStringUtil.getRandomUUID());
		boolean singly = true;
		Object result = null;
		try {
			//获取方法注解参数
			String methodName=joinPoint.getSignature().getName();  
	        Class<?> classTarget=joinPoint.getTarget().getClass();  
	        Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();  
	        Method objMethod=classTarget.getMethod(methodName, par);  
	        LogThisOperate lto = objMethod.getAnnotation(com.boyuyun.common.annotation.LogThisOperate.class);
	        log.setModuleName(lto.module());
	        log.setOperateResult("成功");
	        log.setOperateTime(new Date());
	        HttpServletRequest request = ServletActionContext.getRequest();
	        log.setIp(this.getIp(request));
	        String id = request.getParameter("id");
	        if(OperateType.新增或修改.equals(lto.operateType())){
	        	if(Strings.isNullOrEmpty(id)||"0".equals(id)){
	        		log.setOperateType(OperateType.新增.name());
	        	}else{
	        		log.setOperateType(OperateType.修改.name());
	        		log.setOperateDataId(id);
	        	}
	        }else{
	        	log.setOperateType(lto.operateType().name());
	        	log.setOperateDataId(id);
	        }
			//调用
			result = joinPoint.proceed(joinPoint.getArgs());
			User user = UserUtil.getUser(request);
	        if(user!=null){
	        	log.setOperateUserName(user.getRealName());
	 	        log.setOperateUserId(user.getId());
	        }
			//非导入时，处理操作结果
			if(joinPoint.getThis() instanceof BaseAction&&!OperateType.导入.name().equals(log.getOperateType())){
				BaseAction action = ((BaseAction)joinPoint.getThis());
				if(action.isExecuteSuccess()){
					log.setOperateResult("成功");
				}else{
					log.setOperateResult("失败");
				}
				log.setRemark(action.getFailMessage());
			}
			if(OperateType.新增.name().equals(log.getOperateType())||OperateType.登录.name().equals(log.getOperateType())){
				String nid = null;
	        	if((joinPoint.getThis() instanceof ModelDriven)){
	        		nid = getModelDrivenId((ModelDriven) joinPoint.getThis());
	        	}else if(joinPoint.getThis() instanceof BaseAction){
	        		nid = ((BaseAction)joinPoint.getThis()).getNewAddId();
	        	}
	        	log.setOperateDataId(nid);
	        }else if(OperateType.导入.name().equals(log.getOperateType())
	        		||OperateType.删除.name().equals(log.getOperateType())
	        		||OperateType.重置密码.name().equals(log.getOperateType())
	        		||OperateType.用户状态修改.name().equals(log.getOperateType())){
	        	if(joinPoint.getThis() instanceof BaseAction){
	        		List ids  = ((BaseAction)joinPoint.getThis()).getBatchListId();
	        		if(ids!=null&&ids.size()>0){
	        			for (Iterator iterator = ids.iterator(); iterator
								.hasNext();) {
							Object key = (Object) iterator.next();
							OperateLog nlog = new OperateLog();
		        			BeanUtils.copyProperties(nlog, log);
		        			nlog.setOperateTime(new Date());
		        			nlog.setId(ByyStringUtil.getRandomUUID());
		        			nlog.setOperateDataId(key.toString());
		        			logs.add(nlog);
						}
	        			singly = false;
	        		}
	        	}
	        }
		} catch (Exception e) {
			log.setOperateResult("异常");
			String message = e.getMessage();
			log.setRemark(message.length()>250?message.substring(0,250):message);
			throw e;
		}finally{
			if(singly){
				((OperateLogBiz)ApplicationContextHolder.getBean(OperateLogBiz.class)).add(log);
			}else{
				((OperateLogBiz)ApplicationContextHolder.getBean(OperateLogBiz.class)).add(logs);
			}
		}
		return result;
	}
	/**
	 * 从model中取得id
	 * @param obj
	 * @return
	 */
	
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>...parameterTypes){
		Method method = null;
		for(Class<?> clazz = object.getClass();clazz!=Object.class;clazz=clazz.getSuperclass()){
			try{
				method = clazz.getDeclaredMethod(methodName, parameterTypes);
			}catch(Exception e){}
		}
		return method;
	}
			

	private String getModelDrivenId(ModelDriven obj){
		Object model = obj.getModel();
		try {
			Method method = getDeclaredMethod(model,"getId");
			if(method!=null){
				Object ret = method.invoke(model);
				if(ret!=null)return ret.toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	private  String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(!Strings.isNullOrEmpty(ip)&& !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(!Strings.isNullOrEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
