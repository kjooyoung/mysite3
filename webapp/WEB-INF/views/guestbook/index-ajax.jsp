<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-ajax.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#dialog-delete-form p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form input[type="password"] {
	padding: 5px;
	outline: none;
	width: 180px;
	border: 1px solid #888;
}

</style>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
// jquery plug-in 만들기
(function($){
	$.fn.hello = function(){
		console.log($(this).attr("id")+"----> hello");
	}
})(jQuery);

var page = 0;
var isEnd = false;

// dialog
var messageBox = function(el,title, message){
	console.log($("#dialog-message"));
	$("#dialog-message").attr("title",title);
	$("#dialog-message p").text(message);
	$("#dialog-message").dialog({
		modal: true,
		buttons:{
			"확인": function(){
				console.log($(this));
				$(this).dialog("close");
				el.focus();
			}
		}
	});
}

var render = function(vo, mode){
	// 현업에 가면 이렇게 안한다. -> js template library 를 사용
	// ex) ejs, underscore, mustache
	var htmls = "<li data-no='"+vo.no+"'><strong>"+vo.name+"</strong>"+
				"<p>"+vo.message.replace(/\n/g,"<br>")+"</p>"+
				"<strong></strong><a href='' data-no='"+vo.no+"'>삭제</a></li>";
	if(mode == true){
		$("#list-guestbook").prepend(htmls);
	} else{
		$("#list-guestbook").append(htmls);
	}
	
}

var fetchList = function(){
	if(isEnd == true){
		return;
	}
	++page;
	$.ajax({
		url: "${pageContext.servletContext.contextPath }/api/guestbook?a=ajax-list&p="+page,
		type: "get",
		dataType: "json",
		data: "",
		success: function(response){
			if(response.result == "fail"){
				console.warn(response.data);
				return
			}
			console.log(response.data);
			// 페이지 끝 검출
			if(response.data.length < 5){
				isEnd = true;
				$("#btn-next").prop("disabled", true);
			}
			
			// rendering
			$(response.data).each(function(index, vo){
				render(vo, false);
			});
			
		},
		error: function(xhr, status, e){
			console.error(status+":"+e);
		}
	});
}

var insert = function(){
	$.ajax({
		url: "${pageContext.servletContext.contextPath }/api/guestbook?a=ajax-insert",
		type: "post",
		dataType: "json",
		data: {
			"name":$("#input-name").val(),
			"password":$("#input-password").val(),
			"message":$("#tx-content").val()
		},
		success: function(response){
			render(response, true);
			$("#input-name").val("");
			$("#input-password").val("");
			$("#tx-content").val("");
		},
		error: function(xhr, status, e){
			console.error(status+":"+e);
		}
	});
}

$(function(){
	var $name = $("#input-name");
	var $password = $("#input-password");
	var $message = $("#tx-content");
	
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			"삭제": function(){
				//ajax 삭제
				console.log("ajax 삭제 작업");
				$.ajax({
					url: "${pageContext.servletContext.contextPath }/api/guestbook?a=ajax-delete",
					type: "post",
					dataType: "json",
					data: {
						"no" : $(this).data('no'),
						"password" : $("#password-delete").val()
					},
					success: function(response){
						console.log(response.result);
						if(response.result == false){
							$("#error").show();
							$("#password-delete").val("");
						} else{
							$("li[data-no="+response.result+"]").remove();
							dialogDelete.dialog("close");
						}
					},
					error: function(xhr, status, e){
						console.error(status+":"+e);
					}
				});
			},
			"취소": function(){
				dialogDelete.dialog("close");
			}
		},
		close: function(){
			console.log("close 후 뒤처리");
			$("#password-delete").val("");
			$("#error").hide();
		}
	});
	
	// live event (미래에 동적으로 생성될 엘리먼트의 이벤트)
	$(document).on("click", "#list-guestbook li a", function(event){
		event.preventDefault();
		$("#hidden-no").val($(this).data("no"));
		dialogDelete.data('no',$(this).data("no")).dialog("open");
	});
	
	
	// 메세지 등록
	$("#add-form").submit(function(event){
		// submit의 기본동작(posting)
		// 막아야한다.
		event.preventDefault();
		
		//validate form data
		$name = $("#input-name");
		if($name.val() == ""){
			messageBox($name,"글남기기","이름은 필수 입력 항목입니다.");
			return;
		}
		if($password.val() == ""){
			messageBox($password,"글남기기","비밀번호는 필수 입력 항목입니다.");
			return;
		}
		if($message.val() == ""){
			messageBox($message,"글남기기","글 내용은 필수 입력 항목입니다.");
			return;
		}
		
		insert();
	});
	
	//스크롤 이벤트
	$(window).scroll(function(){
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $(document).height();;
		
	// 	console.log(scrollTop+":"+windowHeight+":"+documentHeight);
		if(documentHeight < scrollTop + windowHeight + 10){
			fetchList();
		}
	});
	
	fetchList();
	
// 	$("#btn-next").click(function(){
// 		fetchList();
// 	});
	
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">
				</ul>
				<!-- <button id="btn-next">다음</button> -->
			</div>
			<input type="hidden" id="auth-no" value="${authuser.no }">
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p id="error" class="validateTips error" style="display:none; color:red;">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style="padding: 30px;"></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>