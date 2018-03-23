package com.cafe24.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.service.CommentService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.CommentVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private CommentService commentService;

	@RequestMapping("/list")
	public String list(Model model, @RequestParam("page") Long page) {
		boardService.list(model, page);
		
		return "/board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "/board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo) {
		boardService.write(vo);
		return "redirect:/board/list?page=1";
	}

	@RequestMapping("/view")
	public String view(Model model, @ModelAttribute BoardVo vo) {
	
		boardService.view(model, vo.getNo());
		boardService.hit(vo);
		commentService.commentList(model, vo.getNo());
		return "board/view";
	}
	
	@RequestMapping(value = "/commentinsert", method = RequestMethod.POST)
	public String insert(@ModelAttribute CommentVo vo, @RequestParam("page") Integer page) {
		System.out.println("boardcontroler ");
		commentService.write(vo);
		return "redirect:/board/view?page="+page+"&no="+vo.getNo();
	}
	@RequestMapping(value = "/commentdelete", method=RequestMethod.GET)
	public String commentDelete() {
		return "/board/commentdeleteform";
	}
	
	@RequestMapping(value = "/commentdelete", method=RequestMethod.POST)
	public String commentDelete(@ModelAttribute CommentVo vo, @RequestParam("page") Integer page) {
	System.out.println("controller: "+vo);
		commentService.delete(vo);
		return "redirect:/board/view?page="+page+"&no="+vo.getBoardNo();
	}
	
	
	

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete() {
		return "/board/deleteform";

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam("page") Integer page, @ModelAttribute BoardVo vo) {

		boardService.delete(vo);
		return "redirect:/board/list?page=" + page;
	}

	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String modify(Model model, @RequestParam("no") Long no) {
		boardService.view_modify(model, no);

		return "/board/modify";
	}

	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(@RequestParam("no") String no, @RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("page") Integer page, BoardVo vo) {

		vo.setNo(Long.parseLong(no));
		vo.setTitle(title);
		vo.setContent(content);

		boardService.modify(vo);

		return "redirect:/board/list?page=" + page;
	}

	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String search(Model model, @RequestParam("kwd") String inputTitle, @RequestParam("page") Long page) {

		System.out.println("controller: " + page);
		boardService.search(model, inputTitle, page);
		System.out.println("controller: " + page);
		return "/board/list";
	}

	@RequestMapping(value = "reply", method = RequestMethod.GET)
	public String reply() {

		return "board/reply";
	}

	@RequestMapping(value = "reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo vo, @RequestParam("page") Integer page) {
		System.out.println("controller : " + vo);
		boardService.reply(vo);

		return "redirect:/board/list?page=" + page;
	}

}
