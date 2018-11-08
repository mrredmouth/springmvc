<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>统一认证中心</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<h1>统一认证中心</h1>
  	${errorMsg }
  	<form action="/sso/login" method="post">
  		<input type="hidden" name="redirectUrl" value="${redirectUrl }">
  		<label>username</label><input type="text" name="username" placeholder="账号"><br/>
  		<label>password</label><input type="password" name="password" placeholder="密码">
  		<input type="submit" value="登录">
  	</form>
  </body>
</html>
