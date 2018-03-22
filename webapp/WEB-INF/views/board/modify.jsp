<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/board.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<c:forEach items="${list }" var="vo" varStatus="status">
				<div id="board">
					<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/modify?page=${param.page}">
					<input type='hidden' name="no" value="${vo.no}"> 
						<table class="tbl-ex">
							<tr>
								<th colspan="2">글수정</th>
							</tr>
							<tr>
								<td class="label">제목</td>
								<td><input type="text" name="title" value="${vo.title }"></td>
							</tr>
							<tr>
								<td class="label">내용</td>
								<td><textarea id="content" name="content">${vo.content }</textarea></td>
							</tr>
						</table>
						<div class="bottom">
							<a href="">취소</a> <input type="submit" value="수정">
						</div>
					</form>
				</div>
			</c:forEach>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>