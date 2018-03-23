package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.mysite.service.CommentService;

@Controller
@RequestMapping("/view")
public class CommentController {
	
	@Autowired
	private CommentService commentService;



	@RequestMapping("/commentdelete")
	public String delete() {
		return "";
	}

}
