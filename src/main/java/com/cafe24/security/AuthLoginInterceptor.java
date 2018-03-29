package com.cafe24.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
	//
	// @Autowired
	// private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		/**
		 * 이렇게 사용하면(new) 안된다. 이미 UserController부분에 service와 dao,vo가 객체가 생성,연결되어 있다. 아래와
		 * 같은 식으로 사용하게 된다면 이미 생성, 연결되어 있는 부분을 또 새롭게 생성하는 잘못을 하게 된다.
		 * 
		 * 그렇다면 어떻게 만들어져 있는 객체를 가져오는가?
		 * 
		 * UserService userService = new UserService(); UserVo vo = new UserVo();
		 * vo.setEmail(email); vo.setPassword(password);
		 * 
		 * UserVo authUser = userService.getUser(vo);
		 */

		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);

		// Web Application Servlet에 접근
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		UserService userService = ac.getBean(UserService.class);

		UserVo authUser = userService.getUser(vo);

		// request.getContextPath() => mysite3
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath() + "/main");

		return false;
	}

}
