<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<a href="http://echarts.baidu.com/echarts2/doc/example.html">echarts参考网站</a>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <!-- 柱状图 -->
    <div id="mainbar" style="height:400px;"></div>
    <!-- 折线图 -->
    <div id="mainline" style="height:400px;"></div>
    <!-- 饼状图 -->
    <div id="mainpie" style="height:400px;"></div>
    <!-- 雷达图 -->
    <div id="mainradar" style="height:400px;"></div>
    <!-- Echarts单文件引入 -->
    <!-- <script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts-all.js"></script> -->
    <script type="text/javascript" src="<%=basePath%>plugins/echarts/echarts-all.js"></script>
    <script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>resources/js/admin/graphic/echart/staticview.js"></script>
</body>
