



<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
	 <script type="text/javascript">
	 $(document).ready(function(){
		 
		   $("#btn").click(function(){
			  
			   var saveDataAry={"User":[{"userNmae":"test","address":"gz"},{"userNmae":"ququ","address":"gr"}]};
		     
		        alert(JSON.stringify(saveDataAry));
		        	$.ajax({
		        		
		                type:"POST",
		                url:"/user/addUser",
		                dataType:"json",
		                contentType:"application/json",
		                data:JSON.stringify(saveDataAry),
		                success:function(data){
                                   alert(111);
		                }
		            });
		        	 alert(2);
		        });
		   
	   });
	 </script>
  </head>
  
  <body>


			<input type="button" id="btn" value="登陆" />
欢迎使用中介笔记软件
  </body>
</html>

