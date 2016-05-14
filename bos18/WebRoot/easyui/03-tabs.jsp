<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs选项卡面板</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<!-- 每个div是一个区域 -->
	<div title="XXX管理系统" style="height: 100px" data-options="region:'north'">北部区域</div>
	<div title="系统菜单" style="width: 220px" data-options="region:'west'">
		<!-- 折叠面板 -->
		<div class="easyui-accordion"  data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div data-options="iconCls:'icon-add'"  title="面板一">
				<a id="baidulink" class="easyui-linkbutton">百度</a>
				<script type="text/javascript">
					$(function(){
						//为按钮绑定事件
						$("#baidulink").click(function(){
							//判断当前选项卡是否存在
							var ex = $("#mytabs").tabs("exists","百度");
							if(ex){
								//存在了,选中
								$("#mytabs").tabs("select","百度");
							}else{
								//动态打开一个选项卡面板
								$("#mytabs").tabs("add",{
									title:'百度',
									iconCls:'icon-search',
									closable:true,
									content:'<iframe frameborder="0" width="100%" height="100%" src="http://www.baidu.com"></iframe>'
								});
							}
						});
					});
				</script>
			</div>
			
			<div data-options="iconCls:'icon-edit'" title="面板二">面板二内容</div>
			<div data-options="iconCls:'icon-help'" title="面板三">面板三内容</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 选项卡面板 -->
		<div id="mytabs" class="easyui-tabs"  data-options="fit:true">
			<!-- 每个子div是其中的一个面板 -->
			<div data-options="iconCls:'icon-edit',closable:true" title="面板一">
				sdfsdfsdf
			</div>
			<div title="面板二">
				dfsdfsdf
			</div>
			<div title="面板三">
				gdsgfsdgfsd
			</div>
		</div>
	</div>
	<div style="width: 120px" data-options="region:'east'">东部区域</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>