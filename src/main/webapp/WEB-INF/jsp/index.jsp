<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Century</title>
    <link rel="stylesheet" href="<%=contextPath%>/static/css/normalize.css" type="text/css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/easyui.css" type="text/css" />
    <link rel="stylesheet" href="<%=contextPath%>/static/css/style.css" type="text/css"/>
    <script type="text/javascript" src="<%=contextPath%>/static/js/audioPlayer.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/jquery.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/jquery.cookie.js"></script>
<% 
	String contextPath = request.getContextPath();

%>
<style type="text/css">
.logoMy{
    background: url('<%=contextPath%>/static/img/mytitle.png') no-repeat;
	width:350px;
	 height:100px;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	
});
	 
function playVideo(videoname,videopath){
	
	var url = '<%=contextPath%>/play/toPlayTab?videoname='+videoname+"&videopath="+videopath;
	window.open (url,'newwindow','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	
	
}

function switchcolumn(column){
	
	var url = '<%=contextPath%>/play/getVideo?column='+column;
	window.location.href= url;
}

function userlogin(){
		
	$('#contentWinMain').dialog('open').dialog('move', {
			top : 65
	});
		
}

function submitDialog(){
		
	var currentUser = $("#currentUser").val();
	if(currentUser.length == 0 ){
		alert("登录名称不能为空！！");
		return false;
	}	
	var COOKIE_NAME = 'username';  	
	$.cookie(COOKIE_NAME, $("#currentUser").val() , { path: '/' });  			
	
	if(currentUser=='fengchao'){
		if($('#guanliyuan').text()=='登录'){
			alert("管理员出场");
			$('#guanliyuan').text("管理员已登录");
			$('a[name=delDiv]').toggle();
		}else {
			alert("管理员退场");
			$('#guanliyuan').text("登录");
			$('a[name=delDiv]').toggle();
		}	
	}
	
	$('#currentUser').val('');
	$('#contentWinMain').dialog('close');
	
}






function deleteVideo(videoName,column){
	 
	 var url = '<%=contextPath%>/play/deleteVideo';
	 var param = {};
	 param.videoName = videoName;	 
	 param.column = column;	 
	jQuery.ajax( {  
      type : 'GET',  
      contentType : 'application/json', 
      data : param,
      url : url,  
      dataType : 'json',  
      success : function(data) {
	    	  if(data.returnDel=='OK'){
	    	  	var vid = '#'+videoName;
	    	  	$(vid).empty();
	        	alert("删除影片成功");
	        }
      },  
      error : function() {  
        alert("error")  
      }  
    });		
}
function zan(videoName,column){
	 
	 var url = '<%=contextPath%>/play/zan';
	 var param = {};
	 param.videoName = videoName;	 
	 param.column = column;	 
	jQuery.ajax( {  
       type : 'GET',  
       contentType : 'application/json', 
       data : param,
       url : url,  
       dataType : 'json',  
       success : function(data) {  
    	   var id = 'zan-'+videoName
           $("#"+id).empty();
           $("#"+id).append('赞'+data.returnZan);
          
       },  
       error : function() {  
         alert("error")  
       }  
     });		
}	 
	 
</script> 
    
    
</head>


<body>

    <header>
    		<br>
		<br>
        <a class="logoMy" style="display:block" href="#">.</a>
        <p class="logoText"></p>
        <ul class="mainMenu">
            <li><a href="#" onclick="switchcolumn('gaoxiao')">搞笑</a></li>
            <li><a href="#" onclick="switchcolumn('tiyu')">体育</a></li>
            <li><a href="#" onclick="switchcolumn('dianying')">电影</a></li>       
            <li><a href="#" id="guanliyuan" onclick="userlogin()">登录</a></li>
        </ul>
    </header>
    <div class="copyrights">Collect from <a href="http://www.cssmoban.com/" >Website Template</a></div>
    <div class="wrapMain">
        <div role="main" class="main">             
               <c:forEach items="${videoResult}" var="list" varStatus="s">
               <div class="postExcerpt video" id='${list.videoName}'>
                    <div class="postExcerptInner">
                        <div class="titleAndCat">
                            <h2>${list.videoName}</h2>
                            <h2>${list.releaseTime}</h2>
                            <a href="#" id="zan-${list.videoName}" onclick="zan('${list.videoName}','${list.column}')">赞${list.zanNum}</a>
                            <a style="display:none;" name="delDiv" href="#" onclick="deleteVideo('${list.videoName}','${list.column}')">删除</a>                           
                        </div>
                        <a id="${list.videoName}" class="playVideo" href="#" onclick="playVideo('${list.videoName}','${list.videoPath}')">Play</a>
                    </div>
               </div>
               </c:forEach>
               
         
            </div>
        </div>
    </div>
    <div id="contentWinMain" class="easyui-dialog" title="登录" closed="true" modal="true"  style="height: 160px; width: 360px;">
			<div style="margin:30px" >
					<span>登录人姓名:</span><input id="currentUser" type="text"  value="">
					<input type="button" onclick="submitDialog()" value="submit" />
			</div>
	</div>
    <footer>
       
       
    </footer>


</body>
</html>