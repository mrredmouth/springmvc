<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
    <a href="mine/helloWorld">hello world</a><br>
    
    <form action="mine/testMethod" method="post">
    	<input type="submit" value="testMethod">
    </form>
    
    <a href="mine/testParamsAndHeaders?username=ccg&age=11">testParamsAndHeaders</a><br>
    
    <a href="mine/testPathVariable/101">testPathVariable</a><br>
    
    <a href="mine/testRest/222">testRest GET</a><br>
    <form action="mine/testRest/222" method="post">
    	<input type="submit" value="testRest POST">
    </form>
    <form action="mine/testRest/222" method="post">
    	<input type="hidden" name="_method" value="DELETE">
    	<input type="submit" value="testRest DELETE">
    </form>
    <form action="mine/testRest" method="post">
    	<input type="hidden" name="_method" value="PUT">
    	<input type="submit" value="testRest PUT">
    </form>
    
    <a href="mine/testRequestParam?username=ccg">testRequestParam</a><br>
    
    <a href="mine/testRequestHeader">testRequestHeader</a><br>
    
    <a href="mine/testCookieValue">testCookieValue</a><br>
    
    <form action="mine/testPOJO" method="post">
    	username:<input type="text" name="username" value=""><br>
    	age:<input type="text" name="age" value=""><br>
    	province:<input type="text" name="address.province" value=""><br>
    	city:<input type="text" name="address.city" value=""><br>
    	<input type="submit" value="testPOJO SUBMIT"><br>
    </form>
    
    <a href="mine/testServletAPI">testServletAPI</a><br>
    
    <a href="mine/testServletAPIWriter">testServletAPIWriter</a><br>
    
    <a href="mine/testModelAndView">testModelAndView</a><br>
    
    <a href="mine/testMap">testMap</a><br>
    
    <a href="mine/testSessionAttributes">testSessionAttributes</a><br>
    
    
    <form action="mine/testModelAttribute" method="post">
    	id:<input type="text" name="id" value="1"><br>
    	username:<input type="text" name="username" value="Tom"><br>
    	age:<input type="text" name="age" value="29"><br>
    	<input type="submit" value="testModelAttribute SUBMIT"><br>
    </form>
  </body>
</html>
