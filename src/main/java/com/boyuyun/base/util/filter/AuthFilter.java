package com.boyuyun.base.util.filter;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boyuyun.base.util.ConstantUtil;
/**
 * 登录过滤器
 * @author zhy
 */
public class AuthFilter implements Filter {
	private String noFilterURLs = "";
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		Object user = request.getSession(true).getAttribute(ConstantUtil.SESSSION_USER_ATTR_NAME);
		if (user == null) {
			String contextPath = request.getContextPath();
			String requestURL = request.getRequestURI();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ contextPath + "/login_toLogin.do";
			if (isFilter(requestURL, contextPath)) {
				response.getWriter().print("<script>window.top.location.href='"+basePath+"';</script>");
				response.getWriter().flush();
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		noFilterURLs = config.getInitParameter("noAuthURLs");
	}

	private boolean isFilter(String requestURL, String contextPath) {
		StringTokenizer st = new StringTokenizer(noFilterURLs, ";");
		while (st.hasMoreTokens()) {
			String url = contextPath + "/" + st.nextToken();
			if (requestURL.equals(url))
				return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		
	}
}
