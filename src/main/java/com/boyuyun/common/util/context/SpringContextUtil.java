package com.boyuyun.common.util.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class SpringContextUtil{
	
	public static Object getBean(String name) throws BeansException {
	    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	    return webApplicationContext.getBean(name);
	}
	
	public static Object getBean1(String name) throws BeansException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/resources/spring-hibernate.xml"); 
		return applicationContext.getBean(name);
	}
	
}
