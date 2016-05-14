<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表格编辑功能使用</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<table id="mytable"></table>
	<script type="text/javascript">
		var rowIndex = -1;
		$(function(){
			$("#mytable").datagrid({
				onAfterEdit:function(index,data,changes){
					//发送一个ajax请求，将修改后的数据提交到服务端，完成数据库操作
				},
				columns:[[
							{field:'id',title:'编号',width:100,checkbox:true}, 
							{field:'name',title:'姓名',width:100,
								editor:{//指定当前列可以编辑
									type:'validatebox',
									options:{
										required:true
									}
								}}, 
							{field:'age',title:'年龄',width:100,align:'right',
									editor:{//指定当前列可以编辑
										type:'validatebox',
										options:{}
									}		
							} 
				          ]],
				url:'${pageContext.request.contextPath }/json/data.json',
				rownumbers:true,
				singleSelect:false,
				pagination:true,
				pageList:[15,25,35],
				toolbar:[
				         {id:'add',text:'添加',iconCls:'icon-add',handler:function(){
				        	 $("#mytable").datagrid("insertRow",{
				        		 index:0,//在第一行插入
				        		 row:{}//空行
				        	 });
				        	 $("#mytable").datagrid("beginEdit",0);//开启编辑状态
				        	 rowIndex = 0;
				         }},
				         {id:'edit',text:'修改',iconCls:'icon-edit',handler:function(){
				        	 //获取当前哪一行被选中了
				        	 var rows = $("#mytable").datagrid("getSelections");
				        	 if(rows.length == 1){
					        	 var row = rows[0];
					        	 var index = $("#mytable").datagrid("getRowIndex",row);
					        	 //开启第一行的编辑状态
					        	 $("#mytable").datagrid("beginEdit",index);
					        	 rowIndex = index;
				        	 }
				         }},
				         {id:'save',text:'保存',iconCls:'icon-save',handler:function(){
				        	 //结束编辑状态
				        	 $("#mytable").datagrid("endEdit",rowIndex);
				         }},
				         {id:'delete',text:'删除',iconCls:'icon-remove',handler:function(){
				        	 var rows = $("#mytable").datagrid("getSelections");
				        	 for(var i=0;i<rows.length;i++){
				        		 var row = rows[i];
					        	 var index = $("#mytable").datagrid("getRowIndex",row);
					        	 $("#mytable").datagrid("deleteRow",index);
				        	 }
				         }}
				         ]
			});
		});
	</script>
</body>
</html>