<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/boardbook.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<c:forEach items="${list }" var="vo" varStatus="status">
				<div id="board" class="board-form">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글보기</th>
						</tr>

						<tr>
							<td class="label">제목</td>
							<td>${vo.title }</td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<div class="view-content">${vo.content }</div>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/list?page=1">글목록</a>
						<a href="${pageContext.servletContext.contextPath }/comment?boardNo=${vo.no }">댓글</a>
						<c:choose>
							<c:when test="${authUser.no == vo.userNo }">
								<a
									href="${pageContext.servletContext.contextPath }/board/modify?no=${vo.no }&page=${param.page}">글수정</a>
							</c:when>
							<c:when test="${not empty authUser }">
								<a href="${pageContext.servletContext.contextPath }/board/reply?page=${param.page }&no=${param.no }&groupNo=${param.groupNo }&orderNo=${param.orderNo}&depth=${param.depth}">답글</a>
							</c:when>
						</c:choose>
					</div>
				</div>
			</c:forEach>
			<div id="boardbook">
				<form action="${pageContext.servletContext.contextPath }/board/commentinsert" method="post">	
					<input type="hidden" name="no" value="${param.no }">
					<input type="hidden" name="page" value="${param.page }">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li><c:set var="count" value="${fn:length(commentlist) }" /> 
					<c:forEach items="${commentlist }" var="vo" varStatus="status">
							<table>
								<tr>
									<td>[${status.count-index }]</td>
									<td>${vo.name }</td>
									<td>${vo.curDate }</td>
									<td><a href="${pageContext.servletContext.contextPath }/board/commentdelete?no=${vo.no }&page=${param.page}&boardNo=${boardNo}">삭제</a></td>
								</tr>
								<tr>
									<td colspan=4>${fn:replace(vo.content, newLine, "<br>") }</td>
								</tr>
							</table>
							<br>
						</c:forEach></li>
				</ul>
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />

	</div>

</body>
</html>