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
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript">


// jQuery plugin
// 어디에서나 호출 될 수 있다. 어떤 엘러먼트든지
(function($){
	$.fn.hello = function(){
		var $element = $(this);
		console.log($element.attr("id")+":hello~");
	}
})(jQuery)

var ejsListItem = new EJS({
	url:"${pageContext.request.contextPath }/assets/js/ejs/template/listitem.ejs"
	
});


var isEnd = 0;
var render = function(mode, vo){
	var html = ejsListItem.render(vo);
	
	
		/*    "<li data-no='"+vo.no+"'>"+
		   "<strong>"+vo.name+"</strong>" +
		   "<p>"+vo.content.replace(/\n/gi,"<br>")+"</p>" +
		   "<strong></strong>" + 
		   "<a href='#' data-no='"+vo.no+"'>삭제</a>"+
		   "</li>"; */
	if(mode ==true){
		$("#list-guestbook").prepend(html);
	}else{
		$("#list-guestbook").append(html);
	}
	
}



var messageBox = function(title, message, callback){
	$("#dialog-message").attr("title", title);
	$("#dialog-message p").text(message);
	
	$("#dialog-message").dialog({
		modal : true,
		buttons : {
			"확인" : function() {
				$(this).dialog("close");
			}
		}
	});
}


var fetchList = function(){
	if (isEnd == true) {
		return;
	}
	var startNo = $("#list-guestbook li").last().data("no") || 0;
	console.log(startNo);

	$.ajax({
		url : "/mysite3/api/guestbook/list?no=" + startNo,
		type : "get",
		dataType : "json",
		success : function(response) {
			//성공 유무
			if (response.result != "success") { //실패할 경우, 에러메세지
				console.log(resoponse.message);
				return;
			}

			//끝 감지
			if (response.data.length < 5) {
				isEnd = true;
				$("#btn-fetch").prop("disabled", true);
			}

			//render
			$.each(response.data, function(index, vo) {
				render(false, vo);
			});

			/* 	//가져온 댓글의 마지막 숫자, why? 그래야지 다음 댓글을 알맞게 가져올 수 있으니까
				var length = response.data.length;
				if(length > 0){
					startNo = response.data[length-1].no;
				} */
		}
	});
}

$(function(){
	
	
	
	//삭제 시 비밀번호 입력 모델 다이알로그
	
	var deleteDialog = $("#dialog-delete-form").dialog({
			autoOpen : false,
			modal : true,
			buttons : {
				"삭제" : function(){
					console.log("삭제");
					var password = $("#password-delete").val();
					var no = $("#hidden-no").val();
					console.log(password+":"+no);
					
					//ajax 통신
					$.ajax({
						url:"/mysite3/api/guestbook/delete",
						type:"post",
						dataType:"json", //서버에서 반환되는 값의 타입
						data:"no="+no+"&password="+password,
						success: function(response){
							if(response.result == "fail"){
								console.log(response.message);
								return;
							}
							
							if(response.data == -1){
								$(".validateTips.normal").hide();
								$(".validateTips.error").show();
								$("#password-delete").val("");
								return;
							}
							
							
							$("#list-guestbook li[data-no="+response.data+"]").remove();
							deleteDialog.dialog("close");
							
						}
					});
					
				},
				"취소" : function() {
					deleteDialog.dialog("close");
				}
			},
			close : function() {
				console.log("closed");
				$(".validateTips.normal").show();
				$(".validateTips.error").hide();
				$("#password-delete").val("");
				$("#hidden-no").val("");
			}
		});

	
		//동적 이벤트 추가
		$(document).on("click", "#list-guestbook li a", function(event) {
			event.preventDefault();

			var no = $(this).data("no");
			$("#hidden-no").val(no);
			
			deleteDialog.dialog("open");
			
			
		});

		$("#add-form").submit(
				function(event) {
					event.preventDefault();

					/* var queryString = $(this).serialize(); */

					var data = {};
					$.each($(this).serializeArray(), function(index, o) {
						data[o.name] = o.value;
					});
					
					if (data["name"] == '') {
						messageBox("메세지 등록", "이름이 비어 있습니다.", $("#input-name")
								.focus());

						return;
					}
					if (data["password"] == '') {
						messageBox("비밀번호 등록", "비밀번호가 비어 있습니다.", $("#password")
								.focus());

						return;
					}
					if (data["content"] == '') {
						messageBox("내용 등록", "내용이 비어 있습니다.", $("#tx-content")
								.focus());

						return;
					}

					console.log(data);

					$.ajax({ //@modelAttribute를 빼줘야 함
						url : "/mysite3/api/guestbook/insert",
						type : "post",
						dataType : "json", //내가 받는 타입
						contentType : "application/json", //내가 보내는 타입
						data : JSON.stringify(data),
						success : function(response) {
							console.log(response);
							render(true, response.data);

							$("#add-form")[0].reset();
						}
					});

					/* $.ajax({  //@modelAttribute를 넣어야한다.
						url:"/mysite3/api/guestbook/insert",
						type:"post",
						dataType:"json",
						data:queryString,
						success:function(response){
							console.log(response);
							render(true, response.data);
						
							$("#add-form")[0].reset();
						}
					}); */
				});

		$("#btn-fetch").click(function() {
			fetchList();
		});
		
		
		$(window).scroll(function(){
			var $window = $(this);
			var scrollTop = $window.scrollTop();
			var windowHeight = $window.height();
			var documentHeight = $(document).height();
			
			//console.log(scrollTop + ":" + windowHeight + ":" +documentHeight);
			
			// scrollbar의 thumb가 바닥 전 30px까지 도달하면, fetchList()를 실행한다. 즉, 새로운 게시물을 갱신
			if(scrollTop + windowHeight + 30 > documentHeight){
				fetchList();
			}
			
		});
		
		
		
		// 최초 리스트 가져오기
		fetchList();
		
		$("#container").hello();
		
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
					<input type="text" name="name" id="input-name" placeholder="이름">
					<input type="password" name="password" id="input-password"
						placeholder="비밀번호">
					<textarea id="tx-content" name="content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all"> 
						<input type="hidden" id="hidden-no" value=""> 
						<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
				</form>
			</div>
			<button id="btn-fetch">가져오기</button>
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