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
<style type="text/css">
a:link {color: black; text-decoration: none;}
a:visited {color: black; text-decoration: none;}
a:hover {color: black; text-decoration: none;}
</style>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board?page=1" method="post">
					<input type="text" id="kwd" name="kwd" value="${map.kwd}">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:set var="count" value="${fn:length(list) }"/>
					<c:forEach items="${map.list }" var="vo" varStatus="status">
					<tr>
						<td>${(map.totalNum-status.index)-((map.page-1)*map.pageMaker.cri.perPageNum)}</td>
						<td id="title" style="padding-left:${20*vo.depth}px">
							<c:if test="${vo.orderNo ne 1 }">
								<img style="width : 15px"src="${pageContext.servletContext.contextPath }/assets/images/img.png"/>
							</c:if>
							<c:choose>
								<c:when test="${vo.replyCount ne 0}">
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}?page=${map.page }&kwd=${map.kwd}">${vo.title } 
										<span style="color: red;">[${vo.replyCount }]</span></a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no}?page=${map.page }&kwd=${map.kwd}">${vo.title }</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.writeDate }</td>
						<td>
							<c:if test="${authuser ne null and authuser.no eq vo.userNo}">
							<a href="${pageContext.servletContext.contextPath }/board/delete/${vo.no}" class="del">
							</a>
<%-- 							  <img alt="" style="width : 20px" src="${pageContext.servletContext.contextPath }/assets/images/recycle.png"> --%>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${map.pageMaker.prev }">
							<li><a href="${pageContext.servletContext.contextPath }/board?page=${map.pageMaker.startPage-1}&kwd=${map.kwd}">◀</a></li>
						</c:if>
						
						<c:forEach begin="${map.pageMaker.startPage }" end="${map.pageMaker.endPage }" var="index">
							<li><a href="${pageContext.servletContext.contextPath }/board?page=${index}&kwd=${map.kwd}">${index }</a></li>
						</c:forEach>
						
						<c:if test="${map.pageMaker.next eq true and map.pageMaker.endPage > 0}">
							<li><a href="${pageContext.servletContext.contextPath }/board?page=${map.pageMaker.endPage+1}&kwd=${map.kwd}">▶</a></li>
						</c:if>
					</ul>
				</div>			
				<!-- pager 추가 -->
				
				<!-- pager 추가 -->
				<!-- <div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>	 -->				
				<!-- pager 추가 -->
				
				<div class="bottom">
					<c:if test="${null ne authuser.no }">
						<a href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
					</c:if>
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