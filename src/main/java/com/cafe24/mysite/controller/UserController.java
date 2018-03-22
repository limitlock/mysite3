package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {

		return "user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess")
	public String joinsuccess() {

		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute UserVo vo, Model model) {

		UserVo authUser = userService.getUser(vo);

		if (authUser == null) {
			model.addAttribute("result", "fail");
			return "user/login";
		}

		// 인증 처리
		session.setAttribute("authUser", authUser);
		return "redirect:/main";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		return "redirect:/main";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(HttpSession session) {
		// 접근 제어(나중에 이러한 코드를 어노테이션으로 만들어 제거시킨다 -> @Auth 이런식으로...
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/main";
		}

		return "user/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute UserVo vo, Model model) {

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/main";
		}

		userService.modify(vo);

		return "user/modifysuccess";
	}

	// @ExceptionHandler(UserDaoException.class)
	// // 서비스영역에서 처리하지 않을 경우 컨트롤러 영역에서 처리한다.
	// // (원래는 서비스영역에서 처리해야함, 그렇기 때문에 해당 코드는 잘 사용하지 않는다.)
	// public String handleUserDaoException() {
	// /* 로그 남기기 */
	//
	// return "error/exception";
	// }

}
