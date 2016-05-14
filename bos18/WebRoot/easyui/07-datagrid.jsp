<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>datagrid数据表格 </title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
	
</head>
<body>
	<h3>方式一：将静态HTML代码渲染为datagrid样式</h3>
	<table class="easyui-datagrid">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>001</td>
				<td>张三</td>
				<td>88</td>
			</tr>
			<tr>
				<td>002</td>
				<td>李四</td>
				<td>2</td>
			</tr>
		</tbody>
	</table>
	
	<h3>方式二：由datagrid控件发送ajax请求，加载远程json数据</h3>
	<table class="easyui-datagrid" data-options="url:'${pageContext.request.contextPath }/json/data.json'">
		<thead>
			<tr>
				<th data-options="field:'id'">编号</th>
				<th data-options="field:'name'">姓名</th>
				<th data-options="field:'age'">年龄</th>
			</tr>
		</thead>
	</table>
	<h3>方式三：通过js代码动态创建datagrid</h3>
	<table id="mytable"></table>
	<script type="text/javascript">
		$(function(){
			$("#mytable").datagrid({
				columns:[[
							{field:'id',title:'编号',width:100,checkbox:true}, 
							{field:'name',title:'姓名',width:100}, 
							{field:'age',title:'年龄',width:100,align:'right'} 
				          ]],
				url:'${pageContext.request.contextPath }/json/data.json',
				rownumbers:true,
				singleSelect:false,
				pagination:true,
				pageList:[15,25,35],
				toolbar:[
				         {id:'add',text:'添加',iconCls:'icon-add',handler:function(){
				        	 alert("add...");
				         }},
				         {id:'edit',text:'修改',iconCls:'icon-edit',handler:function(){
				        	 alert("edit...");
				         }},
				         {id:'delete',text:'删除',iconCls:'icon-remove',handler:function(){
				        	 //获得当前数据选中的行
				        	 var rows = $("#mytable").datagrid("getSelections");
				        	 for(var i=0;i<rows.length;i++){
				        		 alert(rows[i].name);
				        	 }
				         }}
				         ]
			});
		});
	</script>
</body>
</html>