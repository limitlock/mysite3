<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook" class="delete-form">
				<h3>정말로 삭제하시겠습니까?</h3>
				<form method="post" action="${pageContext.servletContext.contextPath }/board/delete?page=${param.page}">
					<input type="hidden" name="a" value="delete"> 
					<input type='hidden' name="userNo" value="${param.userNo}"> 
					<input type='hidden' name="no" value="${param.no}"> 
					<label>비밀번호</label><input type="password" name="password"> 
					<input type="submit" value="확인">
				</form>
				<a href="${pageContext.servletContext.contextPath }/board/list">되돌아가기</a>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>