<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生列表</title>
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠的 
	        fit: true,//自动大小 
	        method: "post",
	       url:"StudentServlet?method=StudentList&t="+new Date().getTime(),
	       // idField:'id', //指示哪个字段是标识字段。
	        singleSelect:false,//是否单选 
	        pagination:false,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'department',//定义可以排序的列。
	        sortOrder:'DESC', //排序方式
	        remoteSort: false,//定义是否从服务器排序数据。
	         //动态添加列
	        columns: [[  
				{field:'chk',checkbox: true,width:50},
 		        //{field:'id',title:'',width:50, sortable: true},    
 		        {field:'stu_id',title:'学号',width:200},    
 		        {field:'stu_name',title:'姓名',width:200},
 		        {field:'sex',title:'性别',width:100},
 		        {field:'telephone',title:'电话',width:150},
 		        {field:'department',title:'专业',width:150,sortable: true},
 		        {field:'clazz',title:'班级',width:150}
 		        
	 		]], 
	        toolbar: "#toolbar",
	        //数据网格（datagrid）面板的头部工具栏。可能的值：1、数组，每个工具选项与链接按钮（linkbutton）一样。2、选择器，只是工具栏。
	        //事件名称：onBeforeLoad参数：param说明：发出请求加载数据之前触发。返回 false 就取消这个动作。
	        
	        
	    }); 
	    //设置分页控件 
	    /*
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    */
	    //设置工具类按钮
	    $("#add").click(function(){
	    //开启会话
	    	$("#addDialog").dialog("open");
	    });
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");//取得所有选中行的数据
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
            } else{           	
            	//each方法可以遍历数组和json或者dom元素，两个参数，第一个参数表示下标，第二个参数表示一维数组中的每一个数组         
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.stu_id;
            	});
            	$.messager.confirm("消息提醒", "将删除与学生相关的所有数据，确认继续？", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "StudentServlet?method=DeleteStudent",
							data: { ids: ids},
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	   
	  	
	  	//设置添加学生窗口
	    $("#addDialog").dialog({
	    	title: "添加学生",
	    	width: 650,
	    	height: 460,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,//定义是否显示折叠按钮。
	    	minimizable: false,//定义是否显示最小化按钮。
	    	maximizable: false,//定义是否显示最大化按钮。
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");//方法名称:validate参数:none说明:进行表单字段验证，当全部字段都有效时返回 true 。这个方法和 validatebox 插件一起使用。
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							
							$.ajax({
								type: "post",
								url: "StudentServlet?method=AddStudent",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功!","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_stuId").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_telephone").textbox('setValue', "");
										$("#add_department").textbox('setValue', "");
										$("#add_clazzList").textbox('setValue', "");
										$("#add_IDcard").textbox('setValue', "");			
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");						  			
										
									} else{
										$.messager.alert("消息提醒","添加失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_stuId").textbox('setValue', "");
						$("#add_name").textbox('setValue', "");
						$("#add_sex").textbox('setValue', "男");
						$("#add_telephone").textbox('setValue', "");
						$("#add_department").textbox('setValue', "");
						$("#add_clazzList").textbox('setValue', "");
						$("#add_IDcard").textbox('setValue', "");
				}}
			]
	    });
	  	
	  	//设置编辑学生窗口
	    $("#editDialog").dialog({
	    	title: "修改学生信息",
	    	width: 650,
	    	height: 460,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");					
						if(!validate){
						//validate可以检验输入的值是否符合规则
							$.messager.alert("消息提醒","请检查你输入的数据!","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "StudentServlet?method=EditStudent&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","更新成功!","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
										
							  		
									} else{
										$.messager.alert("消息提醒","更新失败!","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						//清空表单
						$("#add_name").textbox('setValue', "");
						$("#add_sex").textbox('setValue', "男");
						$("#add_telephone").textbox('setValue', "");
						$("#add_department").textbox('setValue', "");
						$("#add_clazzList").textbox('setValue', "");
						$("#add_IDcard").textbox('setValue', "");
					}
				}
			],
			
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值
				$("#edit_name").textbox('setValue', selectRow.stu_name);
				$("#edit_sex").textbox('setValue', selectRow.sex);
				$("#edit_telephone").textbox('setValue', selectRow.telephone);
				$("#edit_department").textbox('setValue', selectRow.department);
				$("#edit_clazzList").textbox('setValue', selectRow.clazz);
				$("#edit_IDcard").textbox('setValue', selectRow.IDcard);
				$("#edit-stuId").val(selectRow.stu_id);				
			}
	    });
	  //搜索按钮监听事件
	  	$("#search-btn").click(function(){
	  		$('#dataList').datagrid('load',{
	  			stu_name: $('#search_student_name').val(),
	  			clazz: $('#clazzList').val(),
	  		});
	  	});
	});
	  $('dataList').datagrid('loadData',ret);
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList" cellsp acing="0" cellpadding="0"> <!-- 单元格间隔 -->
	    
	</table> 
	<!-- 工具栏 --><!-- easyui链接按钮 -->
	<div id="toolbar">
	      <!-- 添加按钮 -->
		<div style="float: left;">		
		  <a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<!-- 修改按钮 -->
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
		</div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<!-- 删除按钮 -->
		<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
		</div>
		<!-- 查找按钮 -->
		<div style="float: left;margin-top:4px;" class="datagrid-btn-separator" >
		&nbsp;&nbsp;姓名：<input id="search_student_name" class="easyui-textbox" name="stu_name" />
		</div>
		<div style="margin-left: 10px;margin-top:4px;" >
		班级：<input id="clazzList" class="easyui-textbox" name="clazz" />
			<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	
	</div>
	
	<!-- 添加学生窗口 -->
	<div id="addDialog" style="padding: 10px">  
		<%--
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    	<img alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="PhotoServlet?method=getPhoto" />
	    </div> 
	     --%>
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >	    		
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stu_name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>学号:</td>
	    			<td>
	    				<input id="add_stuId"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="stu_id" data-options="required:true, missingMessage:'请填写学号'" />
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="add_telephone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="telephone" validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td><input id="add_department" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="department"  data-options="required:true, missingMessage:'请填写专业'"/></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input id="add_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazz" data-options="required:true, missingMessage:'请填写班级'"/></td>
	    		</tr>
	    		<tr>
	    			<td>身份证号码:</td>
	    			<td><input id="add_IDcard" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="IDcard" validType="number" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改学生窗口 -->
	<div id="editDialog" style="padding: 10px">
		<%-- 
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
	    	<img id="edit_photo" alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="" />
	    	<form id="uploadForm" method="post" enctype="multipart/form-data" action="PhotoServlet?method=SetPhoto" target="photo_target">
	    		<!-- StudentServlet?method=SetPhoto -->
	    		<input type="hidden" name="sid" id="set-photo-id">
		    	<input class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
		    	<input id="upload-photo-btn" onClick="uploadPhoto()" class="easyui-linkbutton" style="width: 50px; height: 24px;" type="button" value="上传"/>
		    </form>
	    </div>  
	     --%>
    	<form id="editForm" method="post">
	    	<input type="hidden" name="stu_id" id="edit-stuId">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="stu_name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td><select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话:</td>
	    			<td><input id="edit_telephone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="telephone" validType="mobile" /></td>
	    		</tr>
	    		<tr>
	    			<td>身份证号:</td>
	    			<td><input id="edit_IDcard" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="IDcard" validType="number" /></td>
	    		</tr>
	    		<tr>
	    			<td>专业:</td>
	    			<td><input id="edit_department" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="department" data-options="required:true, missingMessage:'请填写专业'" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级:</td>
	    			<td><input id="edit_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazz" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
<!-- 提交表单处理iframe框架 -->
	<!--  <iframe id="photo_target" name="photo_target"></iframe>  -->
</body>
</html>