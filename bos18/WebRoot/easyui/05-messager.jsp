<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>messager消息提示控件</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript">
		$(function(){
			//$.messager.alert("标题内容","提示信息内容！","question");
			
			/**
			window.setTimeout(function(){
				$.messager.show({  	
					  title:'My Title',  	
					  msg:'Message will be closed after 5 seconds.',  	
					  timeout:5000,  	
					  showType:'slide'
				});
			}, 3000);
			**/
			
			/**
			$.messager.confirm("提示信息","你确定删除当前数据吗？",function(r){
				if(r){
					//执行删除逻辑
				}
			});
			**/
			
			/**
				$.messager.prompt("提示信息","请输入一个名字？",function(r){
					alert(r);
				});
			**/
			
			$.messager.progress();
			
			window.setTimeout(function(){
				$.messager.progress("close");
			}, 3000);
			
		});
	</script>
</head>
<body >
</body>
</html>