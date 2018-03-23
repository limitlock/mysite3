package com.cafe24.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cafe24.mysite.dto.JSONResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws Exception {

		// 1.로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		request.setAttribute("errors", errors.toString());
		e.printStackTrace();

		String accept = request.getHeader("accept");
		System.out.println(accept);
		
		if (accept.matches(".*application/json.*")) {
			// 실패 json 응답
			JSONResult jsonResult = JSONResult.fail(errors.toString());
			String json = new ObjectMapper().writeValueAsString(jsonResult);
			System.out.println(json);
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().print(json);
		} else {
			// 2.사과
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
		}
	}

}
