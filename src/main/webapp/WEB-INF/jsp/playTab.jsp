<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>播放页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


<% 
	String contextPath = request.getContextPath();
	
%>
<link href="<%=contextPath%>/static/css/skin/blue.monday/jplayer.blue.monday.css" rel="stylesheet" type="text/css" />
<link href="<%=contextPath%>/static/css/easyui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/dist/jplayer/jquery.jplayer.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/dist/add-on/jplayer.playlist.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.cookie.js"></script>

<style type="text/css">
html,body{ margin:0px; padding:0px; height: 100%; } 
.All{ height: 100%; width: 100%; } 
.leftside{ background: #E6E6E6; float: left; height: 100%; width: 600px; margin: 0px; overflow-y:auto; } 
.rightside{ background: white; height: 100%; width:100%; align:right; } 
a{text-decoration: none;}
a:visited {color:blue;}
a:hover   {color:red;}
a:active  {color:yellow;}

</style>


<script type="text/javascript">

$(document).ready(function(){

	new jPlayerPlaylist({
		jPlayer: "#jquery_jplayer_1",
		cssSelectorAncestor: "#jp_container_1"
	}, [
		{
			title:'${videoname}',
			artist:"",
			free:true,
			m4v: 'http://localhost:8088/videoSite${videopath}',
			poster:"http://www.jplayer.org/video/poster/Big_Buck_Bunny_Trailer_480x270.png"
		}
	], {
		playlistOptions: {    autoPlay: true,    enableRemoveControls: true  },
		swfPath: "../../dist/jplayer",
		supplied: "webmv, ogv, m4v",
		useStateClassSkin: true,
		autoBlur: false,
		smoothPlayBar: true,
		keyEnabled: true
	});
	 //  mp3: "media/mysound.mp3",  m4a: "media/mysound.mp4",  oga: "media/mysound.ogg"
	 
	
	$("#queryTable").on('click', 'a[name^=clickObj]', function (e) { 
		var v_id = $(this).attr('id'); 
        addCommentWindow(v_id);
    });

    
				
      
});




function addCommentWindow(commentId) {
	
	$('#contentWinMain').dialog('open').dialog('move', {
		top : 65
	});
	$('#commentId').val(commentId);
}



function submitDialog(){
	//$('#commentId')不为空证明此次提交的是回复的评论
	var commurl = '<%=contextPath%>/play/addComment';
	var commentId = $('#commentId').val();
	var content = $('#content').val();
	
	if(commentId!=''){
		
		$("#tmpForm").ajaxSubmit({
	        type: 'get',
	        url: commurl, 
	        data: {
	        	commentId: '',
	        	commenter: '',
	        	commentContent:content,
	        	parentId:commentId,
	        	videoId:'${videoname}',
	        	commentDate:''
	        },
	        success: function(data) {
			
				$('#queryTable').empty();
				$('#queryTable').append("<table>");
				//回调函数
				$.each(data.comments, function(i, item) {
					if(item.parentId==''){
					  $('#queryTable').append(
						"<tr><td>"
						+"<a name='clickObj' href=\'#\' id='"+ item.commentId +"' >"
						+"<h3 style='color:blue;'>"+ item.commentContent + "</h3></a>"	
						+"<div>"				
					  );
					
					   $.each(data.comments, function(ii, items) { 
							if (items.parentId==item.commentId ){
								$('#queryTable').append("<h4>回复:"+ items.commentContent+ "</h4>");
							}
					   }); 
					  $('#queryTable').append("</div></td></tr>");
					}
				}); 

			$('#queryTable').append("</table>");
	        }
	    });
		
	}else{
		
		$("#tmpForm").ajaxSubmit({
	        type: 'get',
	        url: commurl, 
	        data: {
	        	commentId: '',
	        	commenter: '',
	        	commentContent:content,
	        	parentId:'',
	        	videoId:'${videoname}',
	        	commentDate:''
	        },
	        success: function(data) {
			
				$('#queryTable').empty();
				$('#queryTable').append("<table>");
				//回调函数
				$.each(data.comments, function(i, item) {
					if(item.parentId==''){
					  $('#queryTable').append(
						"<tr><td>"
						+"<a name='clickObj' href=\'#\' id='"+ item.commentId +"' >"
						+"<h3 style='color:blue;'>"+ item.commentContent + "</h3></a>"	
						+"<div>"				
					  );
					
					   $.each(data.comments, function(ii, items) { 
							if (items.parentId==item.commentId ){
								$('#queryTable').append("<h4>回复:"+ items.commentContent+ "</h4>");
							}
					   }); 
					  $('#queryTable').append("</div></td></tr>");
					}
				}); 

			$('#queryTable').append("</table>");
	        }
	    });
		
	}
	
	//关闭窗口
	$('#content').val('');
	$('#contentWinMain').dialog('close');
	
}


function addhotTips(hotTipsName){
	var user = $.cookie("username");
	if(user==null){
		alert("请先登录");
		return false;
	}
	var url = '<%=contextPath%>/play/gethotTips?hotTipsName='+hotTipsName;
	window.open (url,'tipswindow','height=500px,width=500px,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	
	
}
</script>
</head>
<body>

<div class="All"> 
<div class="leftside">

	<div id="jp_container_1" class="jp-video jp-video-270p" role="application" aria-label="media player">
	<div class="jp-type-playlist">
		<div id="jquery_jplayer_1" class="jp-jplayer"></div>
		<div class="jp-gui">
			<div class="jp-video-play">
				<button class="jp-video-play-icon" role="button" tabindex="0">play</button>
			</div>
			<div class="jp-interface">
				<div class="jp-progress">
					<div class="jp-seek-bar">
						<div class="jp-play-bar"></div>
					</div>
				</div>
				<div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
				<div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
				<div class="jp-controls-holder">
					<div class="jp-controls">
						<button class="jp-previous" role="button" tabindex="0">previous</button>
						<button class="jp-play" role="button" tabindex="0">play</button>
						<button class="jp-next" role="button" tabindex="0">next</button>
						<button class="jp-stop" role="button" tabindex="0">stop</button>
					</div>
					<div class="jp-volume-controls">
						<button class="jp-mute" role="button" tabindex="0">mute</button>
						<button class="jp-volume-max" role="button" tabindex="0">max volume</button>
						<div class="jp-volume-bar">
							<div class="jp-volume-bar-value"></div>
						</div>
					</div>
					<div class="jp-toggles">
						<button class="jp-repeat" role="button" tabindex="0">repeat</button>
						<button class="jp-shuffle" role="button" tabindex="0">shuffle</button>
						<button class="jp-full-screen" role="button" tabindex="0">full screen</button>
					</div>
				</div>
				<div class="jp-details">
					<div class="jp-title" aria-label="title">&nbsp;</div>
				</div>
			</div>
		</div>
		<div class="jp-playlist">
			<ul>
				<!-- The method Playlist.displayPlaylist() uses this unordered list -->
				<li>&nbsp;</li>
			</ul>
		</div>
		<input type="hidden"  id="commentId" name="commentId" value="">
		<input type="hidden"  id="videoId" name="videoId" value="">
			
		<div><input onclick="addCommentWindow('')" type="button" value="Comment" /></div>
	
		<div id="queryTable">
			<table>
			 
			 <c:forEach items="${comments}" var="list" varStatus="s">
				<c:if test="${list.parentId=='' }">
				<tr>
					<td>						
						<a href="#" onclick="addCommentWindow('${list.commentId}')"><h3 style="color:blue;">${list.commentContent}</h3>						 
						</a>
						<div>
							
							<c:forEach items="${comments}" var="sonlist" varStatus="ss">
									<c:if test="${sonlist.parentId==list.commentId }">
										<h4>回复:${sonlist.commentContent}</h4>
									</c:if>									
							</c:forEach>											
						</div>
					</td>
				</tr>
				</c:if>
				</c:forEach>
			</table>
			 
			<!-- 此处放评论 --> 
			</div>
		</div>
	</div>

			<div id="contentWinMain" class="easyui-dialog" title="评论" closed="true" modal="true"  style="height: 230px; width: 600px;">
				
					<textarea cols="80" id="content" rows="10"></textarea>
					<input type="button" onclick="submitDialog()" value="submit" />
			
			</div>

	</div> 

	<div class="rightside" style="background-color: rgb(88,87,86);" >		
		<br>
		<div><a href="#" style="color:white;" >热帖投票:</a></div>
		<br>
		<div><a href="#" style="color:white;" onclick="addhotTips('football')">中国足球是否能出线</a></div>  
		<div style="margin-top:20px;margin-bottom:20px;" onclick="addhotTips('game')"><a href="#" style="color:white;">大家都爱玩什么游戏</a></div>  
		<div><a href="#" style="color:white;" onclick="addhotTips('film')">大家都喜欢看什么电影</a></div>  
	</div> 

</div> 
<form action="" method="post" id="tmpForm" style="display: none"></form>
</body>

</html>
