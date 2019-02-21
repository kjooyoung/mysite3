<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
.delete{
	width: 16px;
	height: 16px;
	cursor:pointer;
}
.reply-cancle{
	float : right;
	padding:5px; 
	border:1px solid #aaa;
	background-color: #aaa;
	font-weight:bold;
	color:#fff;
	margin-left: 5px; 
}
</style>
<script type="text/javascript">
$(function(){
	$(".update").click(function(){
		var no = $(this).attr("data-no");
		$(".reply-wrap[data-no="+no+"]").hide();
		$(".reply-update-form[data-no="+no+"]").show();
	});
	
	$(".reply-cancle").click(function() {
		var no = $(this).attr("data-no");
		$(".reply-update-form[data-no="+no+"]").hide();
		$(".reply-wrap[data-no="+no+"]").show();
	});
	
	${"#reply-btn"}.click(funtion(){
		f
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<c:set value="\n" var="newline"/>
			<c:set value="${map.board }" var="board"/>
			<c:set value="${map.reply }" var="reply"/>
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${board.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(board.contents, newline, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board/${page}">글목록</a>
					<c:if test="${authuser.no eq board.userNo }">
						<a href="${pageContext.servletContext.contextPath }/board/modify/${page }/${board.no}">글수정</a>
					</c:if>
					<c:if test="${authuser.no ne null }">
					<a href="${pageContext.servletContext.contextPath }/board/write/${board.no}">답글쓰기</a>
					</c:if>
				</div>
				<div id="reply">
					<form method="post" action="${pageContext.servletContext.contextPath }/reply/write/${page}/${board.no}">
						<table class="tbl-ex">
							<tr>
								<th>댓글</th>
							</tr>
						</table>
						<c:if test="${authuser ne null }">
						<table class="tbl-ex">
							<tr>
								<td>
									<textarea id="cont" name="contents"></textarea>
								</td>
							</tr>
						</table>
						<div class="bottom">
							<input type="submit" value="등록">
						</div>
						</c:if>
					</form>
					<c:forEach items="${reply }" var="vo" varStatus="status">
						<c:choose>
							<c:when test="${param.replyNo ne null and param.replyNo eq vo.no and param.reReply eq 1}">
								<!-- 대댓글 폼 -->
								<div class="reply-wrap" data-no="${status.index }">
									<form method="post" action="${pageContext.servletContext.contextPath }/reply">
										<input type="hidden" name="a" value="write">
										<input type="hidden" name="reply" value="true">
										<input type="hidden" name="no" value="${vo.no }">
										<input type="hidden" name="boardNo" value="${board.no}">
										<input type="hidden" name="userNo" value="${authuser.no }">
										<table class="tbl-ex">
										<tr>
											<td>${vo.userName }
												<c:if test="${authuser.no eq vo.userNo }">
													<img data-no="${status.index }" class="delete update" src="${pageContext.servletContext.contextPath }/assets/images/update.png">
												<a href="${pageContext.servletContext.contextPath }/reply/delete/${page}/${board.no}/${vo.no}">
													<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/recycle.png">
												</a>
												</c:if>
												<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${board.no}&replyNo=${vo.no}&reReply=1">
													<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/img.png">
												</a>
											<div class="date">
												<div id="del-wrap">${vo.writeDate }</div>
											</div>
											</td>
										</tr>
										<tr>
											<td>${vo.contents }</td>
										</tr>
										<tr>
											<td style="font-weight: bold;">
												<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/img.png">
												${authuser.name }
											</td>
										</tr>
										<tr>
											<td>
												<textarea id="cont" name="contents"></textarea>
												<input id="reply-btn" type="submit" value="등록">
											</td>
										</tr>
										</table>
									</form>
								</div>
							</c:when>
							<c:otherwise>
								<!-- 기본 뷰 -->
								<div class="reply-wrap" data-no="${status.index }">
									<table class="tbl-ex">
										<tr>
											<c:if test="${vo.depth ne 0 }">
											<td rowspan="2" style='width:10px; padding-left:${30 * vo.depth}px; ' >
												<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/img.png">
											</td>
											</c:if>
											<td>${vo.userName }
												<c:if test="${authuser.no eq vo.userNo }">
													<img data-no="${status.index }" class="delete update" src="${pageContext.servletContext.contextPath }/assets/images/update.png">
												<a href="${pageContext.servletContext.contextPath }/reply/delete/${page}/${board.no}/${vo.no}">
													<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/recycle.png">
												</a>
												</c:if>
												<a href="${pageContext.servletContext.contextPath }/board?a=view&no=${board.no}&replyNo=${vo.no}&reReply=1">
													<img class="delete" src="${pageContext.servletContext.contextPath }/assets/images/img.png">
												</a>
											<div class="date">
												<div id="del-wrap">${vo.writeDate }</div>
											</div>
											</td>
										</tr>
										<tr>
											<td>${vo.contents }</td>
										</tr>
									</table>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="reply-update-form" style="display: none;" data-no="${status.index }">
							<form method="post" action="${pageContext.servletContext.contextPath }/reply/update/${page }/${board.no}/${vo.no}">
								<table class="tbl-ex">
								<tr>
									<td>${vo.userName }
										<div class="date">
											<div id="del-wrap">${vo.writeDate }</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<textarea id="cont" name="contents">${vo.contents }</textarea>
										<input class="reply-cancle" data-no="${status.index }" type="button" value="취소">
										<input id="reply-btn" type="submit" value="등록">
										
									</td>
								</tr>
								</table>
							</form>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>