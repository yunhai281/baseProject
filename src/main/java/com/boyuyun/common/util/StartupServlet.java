package com.boyuyun.common.util;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.boyuyun.base.util.ConstantUtil;
import com.boyuyun.base.util.SystemParam;
/**
 * 依赖servlet启动
 * @author zhy
 */
public class StartupServlet extends HttpServlet {
	public static ServletContext application;
	@Override
	public void init(ServletConfig config) throws ServletException {
		application = config.getServletContext(); 
		String systemName = ResourceBundle.getBundle("config").getString("system.name");
		String systemVersion = ResourceBundle.getBundle("config").getString("system.version");
		ConstantUtil.SYSTEM_NAME = systemName;
		ConstantUtil.SYSTEM_VERSION = systemVersion;
		application.setAttribute("systemName", systemName);
		application.setAttribute("systemVersion", systemVersion);
		//更新系统配置
		SystemParam.refresh();
		super.init(config);
	}
}
