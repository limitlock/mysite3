<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("#btn-checkemail").click(function(){
		
		var email = $("#email").val();
		if(email==""){
			return;
		}
		
		$.ajax({
			url:"/mysite3/api/user/checkemail?email="+email,
			type:"get",
			data:"",
			dataType:"json", 
			success: function(response){
				if(response.result != "success"){
					console.log(response.message);
					return;
				}
				
				if(response.data == "exist"){
					alert("이미 사용 중인 이메일 입니다.");
					$("#email").val("").focus();
					return;
				}
				
				$("#img-check").show();
				$("#btn-checkemail").hide();
				
			},
			error: function(xhr, status, e){
				console.error(status + ":" + e);
			}
		});
	});
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">
				<form:form
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.servletContext.contextPath }/user/join">
					
					<label class="block-label" for="name">이름</label>
					
					
					<!-- <input id="name" name="name" type="text" value=""> -->
					<form:input path="name"/>
					<p style="padding:0; font-weight:bold; text-align:left; color:#f00">
					<form:errors path="name"/>
					</p>
					
					<!--	<spring:hasBindErrors name="userVo">					
						   <c:if test="${errors.hasFieldErrors('name') }">
						   		<p style="padding:0; text-align:left; color:#f00;">
						        <strong>${errors.getFieldError( 'name' ).defaultMessage }</strong>
						        </p>
						   </c:if>			
						</spring:hasBindErrors>-->

					<label class="block-label" for="email">이메일</label>
					
					<form:input path="email"/>
					<p style="padding:0; font-weight:bold; text-align:left; color:#f00">
					<form:errors path="email"/>
					</p>
					
					
					
					<img id="img-check" style="display:none" src="${pageContext.servletContext.contextPath }/assets/images/check.png">
					<input type="button" value="id 중복체크" id="btn-checkemail">
					
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					<spring:hasBindErrors name="userVo">					
					   <c:if test="${errors.hasFieldErrors('password') }">
					   		<p style="padding:0; text-align:left; color:#f00;">
					   		<spring:message>
					   			code="${errors.getFieldError('password').codes[0] }"
					   			text="${errors.getFieldError('password').defaultMessage }"
					   		</spring:message>
					        <!--  <strong>${errors.getFieldError( 'password' ).defaultMessage }</strong>-->
					        </p>
					   </c:if>			
					</spring:hasBindErrors>
					
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <form:radiobutton path="gender" value="female"/>
						<label>남</label> <form:radiobutton path="gender" value="male"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form:form>
			</div>
		</div>
			<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value=""/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>