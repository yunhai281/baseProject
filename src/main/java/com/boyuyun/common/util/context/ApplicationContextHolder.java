package com.boyuyun.common.util.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static boolean devMode = true;
	private static String name;

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		applicationContext = ctx;
	}

	public static boolean containBean(String beanName) {
		return applicationContext.containsBean(beanName);
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static <T> T getBean(String beanName, Class<T> type) {
		return applicationContext.getBean(beanName, type);
	}

	public static <T> T getBean(Class<T> cls) {
		return applicationContext.getBean(cls);
	}

	public static boolean isDevMode() {
		return devMode;
	}

	public static void setDevMode(boolean devMode) {
		ApplicationContextHolder.devMode = devMode;
	}

	/**
	 * 应用标识
	 * @return
	 */
	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		ApplicationContextHolder.name = name;
	}

}
