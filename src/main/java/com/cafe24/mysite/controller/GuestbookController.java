package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {

		guestbookService.list(model);
		return "guestbook/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(GuestbookVo vo) {
		guestbookService.add(vo);

		return "redirect:/guestbook/list";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete() {
		return "guestbook/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute GuestbookVo vo) {
		guestbookService.delete(vo);
		
		return "redirect:/guestbook/list";
	}

}
