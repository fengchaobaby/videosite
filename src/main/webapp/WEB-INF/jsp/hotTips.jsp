<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<% 
	String contextPath = request.getContextPath();

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
div.box{width:300px;padding:20px;margin:20px;border:4px dashed #ccc;}
div.box span{color:#999;font-style:italic;}
div.content{width:250px;margin:10px 0;padding:20px;border:2px solid #ff6666;}
input[type='checkbox']{margin:5px;}
input[type='button']{height:30px;margin:10px;padding:5px 10px;}
</style>
<script src="<%=contextPath%>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.cookie.js"></script>
<script type="text/javascript">

$(document).ready(function(){	
	// 设置属性值
	
	var user = $.cookie("username");
	$("#fillMan").val(user);
	$("#fillMana").text(user);
	}) ;

function selectNoCheckBox(){
	
	var actor_config = '';
	var unCheckedBoxs = $("input[name='contextAndanswer']").not("input:checked");
	unCheckedBoxs.each(function(i){ //循环拼装被选中项的值
		actor_config =$(this).val()+','+ actor_config;
	}); 

	$("#unCheckedBoxs").val(actor_config);
	$("#tempForm").submit();

}
</script>
<style type="text/css"> 
.divcss5-left{float:left;} 
td{margin-left: 20px;margin-top: 20px;}
</style>
</head>
<body>
	<form id="tempForm" action="<%=contextPath%>/play/addhotTips" Method="POST" >
	
	  <table style="">
		<input type="hidden" name="hotTipsName" value="${HottipsResult.hotTipsName}">
		<input type="hidden" name="hotTipsTitle" value="${HottipsResult.hotTipsTitle }">
		<input type="hidden" name="unCheckedBoxs" id="unCheckedBoxs">
		<input type="hidden" name="fillMan" id="fillMan">
		<tr style="{margin-left: 20px;}">
			<td class="divcss5-left">投票人</td>
			<td class="divcss5-left"><a id="fillMana"> </a>	</td>
		</tr>
		<tr style="{margin-left: 20px;}">
			<td class="divcss5-left">标题</td>
			<td class="divcss5-left">${HottipsResult.hotTipsTitle }</td>
		</tr>
		<tr style="{margin-left: 20px;}">
			<td rowspan="2" class="divcss5-left">
				<div class="box">
					<div class="content">
					<c:forEach items="${HottipsResult.contextAndanswer}" var="mymap" >					
					 	<input type="checkbox" name="contextAndanswer" value="${mymap.key}"
					 	<c:if test="${mymap.value == 1}">checked</c:if>
					 	 />${mymap.key}
					 	 
					 　</c:forEach>
					 						
					</div>						
				</div>
			</td>
		</tr>
		<tr>
			<td rowspan="2" style="text-align:right;">
				<input type="submit" value="提   交" onclick="selectNoCheckBox()" style="{margin-right: 50px;}"">
			</td>
		</tr>
		
	  </table >
	  <br>
	  <br>
	  <br>
			<div style="{margin-top: 20px;}">
				<c:forEach items="${answer}" var="answer1" >					
					 	<div style="float:left; height:50px;width:100px">${answer1.key}</div>
						<div style="float:left; height:50px;width:100px">${answer1.value}</div>
						<div style="clear:both"></div>
				</c:forEach>
		</div>
	</form>
</body>
</html>