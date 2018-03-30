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
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.servletContext.contextPath }/board/search" method="post">
					<input type="hidden" name="page" value="${param.page }"> 
					<input type="text" id="kwd" name="kwd" value=""> 
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
					<c:set var="count" value="${fn:length(list) }" />
					<c:forEach items="${list }" step="1" var="vo" varStatus="status">
						<tr>
							<td>${maxNo - ( (param.page-1) * 5) - status.count + 1  }</td>
							<td style="text-align: left; padding-left: ${20*vo.depth}px">
								<c:choose>
									<c:when test="${vo.orderNo > 1}">
										<img src="${pageContext.servletContext.contextPath }/assets/images/reply.png" />
										<a href="${pageContext.servletContext.contextPath }/board/view?page=${param.page }&no=${vo.no }">${vo.title }</a>
									</c:when>
									<c:otherwise>
										<a href="${pageContext.servletContext.contextPath }/board/view?page=${param.page }&no=${vo.no }">${vo.title }</a>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${vo.user }</td>
							<td>${vo.hit }</td>
							<td>${vo.curDate }</td>
							<c:choose>
								<c:when test="${authUser.no == vo.userNo }">
									<td><a
										href="${pageContext.servletContext.contextPath }/board/delete?userNo=${authUser.no }&no=${vo.no}&page=${param.page}"
										class="del">삭제</a></td>
								</c:when>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div id="underpoint" class="pager">
					<ul>
						<c:set var="count" value="${fn:length(list) }" />						
						<fmt:parseNumber var="totalPage" value="${(((maxNo-1)/5)+1)}"  integerOnly="true" />
						<c:choose>
							<c:when test="${param.page > 5 }">
								<fmt:parseNumber var="startPage"
									value="${((param.page-1)/totalPage) * totalPage + 1} " integerOnly="true" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber var="startPage"
									value="${((1-1)/totalPage) * totalPage + 1} " integerOnly="true" />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${endPage > totalPage }">
								<fmt:parseNumber var="endPage" value="${((startPage + totalPage ) -1) } " integerOnly="true" />
							</c:when>
							<c:otherwise>
								<fmt:parseNumber var="endPage" value="${totalPage}" integerOnly="true" />
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${startPage <= param.page && param.page != 1}">
								<li><a
									href="${pageContext.servletContext.contextPath }/board/list?page=${(param.page-1) }">◀
								</a></li>
							</c:when>
							<c:otherwise>
								<li>◀</li>
							</c:otherwise>
						</c:choose>
						<c:forEach begin="${startPage }" end="${startPage+4}" step="1"
							var="i" varStatus="status">
							<c:choose>
								<c:when test="${totalPage >= i }">
									<li class="selected">
									<a href="${pageContext.servletContext.contextPath }/board/list?page=${i }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li>${i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${totalPage > param.page  }">
								<li><a
									href="${pageContext.servletContext.contextPath }/board/list?page=${(param.page+1) }">▶
								</a></li>
							</c:when>
							<c:when test="${totalPage <= param.page }">
								<li>▶</li>
							</c:when>
						</c:choose>
					</ul>
				</div>
				<c:choose>
					<c:when test="${not empty authUser }">
						<div class="bottom">
							<a href="${pageContext.servletContext.contextPath }/board/write" id="new-book">글쓰기</a>
						</div>
					</c:when>
				</c:choose>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>