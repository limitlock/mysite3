<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">
	$(function() {

		var dialogDelete = $("#dialog-delete-form").dialog({
			autoOpen : false,
			height : 200,
			width : 300,
			modal : true,
			buttons : {
				"삭제" : function() {
					$(this).dialog("close");
				},
				"취소" : function() {
					$(this).dialog("close");
				}
			},close : function() {
				
			}

		});

		$("#delete-btn").click(function(event) {
			event.preventDefault();
			dialogDelete.dialog("open");
		});
	});
</script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

div div div h1 {
	background:
		url('${pageContext.request.contextPath }/assets/images/guestbook.png')
		left 5px no-repeat;
	background-size: 35px;
	margin-bottom: 10px;
	font-size: 30px;
	padding-left: 45px;
}

#add-form input {
	width: 100%;
	margin-bottom: 5px;
	display: block;
	padding: 5px;
}

#add-form textarea {
	max-width: 100%;
	min-width: 100%;
	max-height: 100px;
	min-height: 100px;
	overflow: auto;
	padding: 5px;
}

#add-form input[type='submit'] {
	width: 100%;
	border: 1px solid #666;
	background-color: #ffffff;
}

#list-guestbook {
	border: 1px solid #666;
}

#list-guestbook li {
	background:
		url('${pageContext.request.contextPath }/assets/images/user.png') 8px
		15px no-repeat;
	float: left;
	display: block;
	padding-bottom: 5px;
	position: relative;
}

#list-guestbook li strong {
	color: #737373;
	margin-left: 50px;
}

#list-guestbook li p {
	border: 1px solid #666;
	border-radius: 5px;
	background-color: #f9fafb;
	margin: 5px 5px 0px 50px;
	padding: 5px;
	display: block;
}

#list-guestbook li a {
	display: block;
	position: absolute;
	left: 25px;
	top: 35px;
	background:
		url('${pageContext.request.contextPath }/assets/images/delete.png') 0
		0 no-repeat;
	width: 20px;
	height: 20px;
	font-size: 0;
}

#dialog-delete-form p {
	padding: 5px;
}

#password-delete {
	padding: 5px;
	border: 4px solid #666;
}
</style>

</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1 id="h1_image">방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">

					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>

					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook">

					<li data-no=''><strong>지나가다가</strong>
						<p>
							별루입니다.<br> 비번:1234 -,.-
						</p> <strong></strong> <a href='' id="delete-btn" data-no="">삭제</a></li>

					<li data-no=''><strong>둘리</strong>
						<p>
							안녕하세요<br> 홈페이지가 개 굿 입니다.
						</p> <strong></strong> <a href='' data-no=''>삭제</a></li>

					<li data-no=''>
						<strong>주인</strong>
						<p>아작스 방명록 입니다.<br> 테스트~</p> 
						<strong></strong> 
						<a href='' data-no=''>삭제</a>
					</li>
				</ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="hidden-no" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="" style="display: none">
				<p></p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>