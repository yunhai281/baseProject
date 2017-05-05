<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.boyuyun.base.util.ConstantUtil,com.boyuyun.base.common.util.UserBehaviorConstant" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	//80端口的时候，不需要带端口号
	if(request.getServerPort()==80||request.getServerPort()==443){
	    basePath = request.getScheme()+"://"+request.getServerName()+path;
	}else {
	    basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	}
	String default_page_rows = "";
	Map<String ,String> behaviorMap = (Map<String,String>)request.getSession().getAttribute(ConstantUtil.SESSSION_USER_BEHAVIOR_NAME);
	if(behaviorMap!=null){
		String default_page_rows_key = UserBehaviorConstant.behaviorTypeMap.get("1");
		default_page_rows = behaviorMap.get(default_page_rows_key);
	}
	
	
%>
<script type="text/javascript">
	var base = '<%=basePath%>';
	var commonPageArray = [10,50,100,200,500,1000];
	var behavior_page_rows = '<%=default_page_rows%>';
</script>