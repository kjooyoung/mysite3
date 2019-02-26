<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>웁스 - ${uri }</h1>
	<p>
		<!-- 사용자 요청이 갑자기 많아져서 서비스에 일시적인 장애가 발생하였습니다.<br/>
		잠시 후 다시 시도해 주세요. -->
		<strong>예외 발생</strong>
	</p>
	<p>
		===================================================
	</p>
	<p style="color: red;">
		${exception }
	</p>
</body>
</html>