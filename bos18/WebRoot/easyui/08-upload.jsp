<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
<%--
	<form target="myIframe1" action="dfsdf" method="post" enctype="multipart/form-data">
		<input type="file" name="abc">
		<input type="submit" value="upload">
	</form>
	<iframe style="display: none" name="myIframe1"></iframe>
 --%>
 
 <input id="but1" type="button" value="使用一键上传">
 <script type="text/javascript">
 	$("#but1").upload({
 		action: 'XXAction_upload.action',  //表单提交的地址
        name: 'myFile'//输入框名称
 	});
 </script>
 
</body>
</html>